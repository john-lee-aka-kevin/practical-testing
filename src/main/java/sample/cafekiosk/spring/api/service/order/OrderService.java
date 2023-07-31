package sample.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 주문 Service
 */
@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {
    /**
     * 상품 Repository
     */
    private final ProductRepository productRepository;

    /**
     * 주문 Repository
     */
    private final OrderRepository orderRepository;

    /**
     * 재고 Repository
     */
    private final StockRepository stockRepository;

    /**
     * 주문 생성 로직
     *
     * @param request 주문 생성 요청 DTO
     * @param registeredDateTime 주문 생성 요청 시간
     *
     * @return 주문 응답 DTO
     */
    public OrderResponse createOrder(final OrderCreateRequest request, final LocalDateTime registeredDateTime) {
        final List<String> productNumbers = request.getProductNumbers();

        // Product
        final List<Product> products = this.findProductsBy(productNumbers);

        // 재고 차감
        this.deductStockQuantities(products);

        // Order
        final Order order = Order.create(products, registeredDateTime);
        final Order savedOrder = this.orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    /**
     * 재고 차감 처리
     *
     * @param products 상품 정보 리스트
     */
    private void deductStockQuantities(final List<Product> products) {
        // 재고 차감 체크가 필요한 상품들 filter
        final List<String> stockProductNumbers = extractStockProductNumbers(products);

        // 재고 엔티티 조회
        final Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        // 상품별 counting
        final Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        // 재고 차감 시도
        for (final String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            final Stock stock = stockMap.get(stockProductNumber);
            final int quantity = productCountingMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }

            stock.deductQuantity(quantity);
        }
    }

    /**
     * 상품 정보 리스트로 부터 상품 번호 리스트 추출
     *
     * @param products 상품 정보 리스트
     *
     * @return 상품 번호 리스트
     */
    private static List<String> extractStockProductNumbers(final List<Product> products) {
        return products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .toList();
    }

    /**
     * 상품 번호 리스트로부터 재고 정보 조회
     *
     * @param stockProductNumbers 재고를 조회할 상품 번호 리스트
     *
     * @return 조회한 상품에 대한 재고 정보가 담긴 Map
     */
    private Map<String, Stock> createStockMapBy(final List<String> stockProductNumbers) {
        final List<Stock> stocks = this.stockRepository.findAllByProductNumberIn(stockProductNumbers);

        return stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }

    /**
     * 상품 번호에 대한 카운팅 (요청된 주문에 대한 상품별 개수 판별 용)
     *
     * @param stockProductNumbers 상품 번호 리스트
     *
     * @return 상품 번호별 카운팅 정보가 담긴 Map
     */
    private static Map<String, Long> createCountingMapBy(final List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    /**
     * 상품 번호 리스트로 상품 정보 조회 (중복 허용)
     *
     * @param productNumbers 상품 번호 리스트
     *
     * @return 상품 정보
     */
    private List<Product> findProductsBy(final List<String> productNumbers) {
        final List<Product> products = this.productRepository.findAllByProductNumberIn(productNumbers);
        final Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .toList();
    }
}
