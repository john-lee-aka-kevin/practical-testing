package sample.cafekiosk.spring.api.service.order;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.List;

@ActiveProfiles("test")
@DataJpaTest
@Import(OrderService.class)
@DisplayName("주문 Business Layer 테스트")
class OrderServiceTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

//    @Autowired
//    private OrderRepository orderRepository;

//    @Autowired
//    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

//    /**
//     * DB Cleansing 처리
//     */
//    @AfterEach
//    void tearDown() {
//        this.orderProductRepository.deleteAllInBatch();
//        this.productRepository.deleteAllInBatch();
//        this.orderRepository.deleteAllInBatch();
//    }

    @DisplayName("주문 번호 리스트를 받아 주문을 생성 한다.")
    @Test
    void createOrder() {
        // Given
        final var product1 = this.createProduct(ProductType.HANDMADE, "001", 1_000);
        final var product2 = this.createProduct(ProductType.HANDMADE, "002", 3_000);
        final var product3 = this.createProduct(ProductType.HANDMADE, "003", 5_000);

        this.productRepository.saveAll(List.of(product1, product2, product3));

        final var registeredDateTime = LocalDateTime.now();
        final var request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        // When
        final var orderResponse = this.orderService.createOrder(request, registeredDateTime);

        // Then
        Assertions.assertThat(orderResponse.getId()).isNotNull();
        Assertions.assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 4_000);
        Assertions.assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 1_000),
                        Tuple.tuple("002", 3_000)
                );
    }


    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문 번호 리스트를 받아 주문을 생성 한다.")
    @Test
    void createOrderWithStock() {
        // Given
        final var product1 = this.createProduct(ProductType.BOTTLE, "001", 1_000);
        final var product2 = this.createProduct(ProductType.BAKERY, "002", 3_000);
        final var product3 = this.createProduct(ProductType.HANDMADE, "003", 5_000);

        this.productRepository.saveAll(List.of(product1, product2, product3));

        final var stock1 = Stock.create("001", 2);
        final var stock2 = Stock.create("002", 2);

        this.stockRepository.saveAll(List.of(stock1, stock2));

        final var registeredDateTime = LocalDateTime.now();
        final var request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        // When
        final var orderResponse = this.orderService.createOrder(request, registeredDateTime);

        // Then
        Assertions.assertThat(orderResponse.getId()).isNotNull();
        Assertions.assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 10_000);
        Assertions.assertThat(orderResponse.getProducts()).hasSize(4)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 1_000),
                        Tuple.tuple("001", 1_000),
                        Tuple.tuple("002", 3_000),
                        Tuple.tuple("003", 5_000)
                );

        final var stocks = this.stockRepository.findAll();

        Assertions.assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 0),
                        Tuple.tuple("002", 1)
                );
    }

    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    @Test
    void createOrderWithNoStock() {
        // Given
        final var product1 = this.createProduct(ProductType.BOTTLE, "001", 1_000);
        final var product2 = this.createProduct(ProductType.BAKERY, "002", 3_000);
        final var product3 = this.createProduct(ProductType.HANDMADE, "003", 5_000);

        this.productRepository.saveAll(List.of(product1, product2, product3));

        final var stock1 = Stock.create("001", 2);
        final var stock2 = Stock.create("002", 2);

        stock1.deductQuantity(1); // TODO: 이렇게 작성하면 안된다?

        this.stockRepository.saveAll(List.of(stock1, stock2));

        final var registeredDateTime = LocalDateTime.now();
        final var request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        // When & Then
        Assertions.assertThatThrownBy(() -> this.orderService.createOrder(request, registeredDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족한 상품이 있습니다.");
    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProductNumbers() {
        // Given
        final var product1 = this.createProduct(ProductType.HANDMADE, "001", 1_000);
        final var product2 = this.createProduct(ProductType.HANDMADE, "002", 3_000);
        final var product3 = this.createProduct(ProductType.HANDMADE, "003", 5_000);

        this.productRepository.saveAll(List.of(product1, product2, product3));

        final var registeredDateTime = LocalDateTime.now();
        final var request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001"))
                .build();

        // When
        final var orderResponse = this.orderService.createOrder(request, registeredDateTime);

        // Then
        Assertions.assertThat(orderResponse.getId()).isNotNull();
        Assertions.assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 2_000);
        Assertions.assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 1_000),
                        Tuple.tuple("001", 1_000)
                );
    }

    private Product createProduct(final ProductType type, final String productNumber, final int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("메뉴 이름")
                .build();
    }
}