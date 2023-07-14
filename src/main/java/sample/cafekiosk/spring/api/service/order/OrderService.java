package sample.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 주문 Service
 */
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

        // Order
        final Order order = Order.create(products, registeredDateTime);
        final Order savedOrder = this.orderRepository.save(order);

        return OrderResponse.of(savedOrder);
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
