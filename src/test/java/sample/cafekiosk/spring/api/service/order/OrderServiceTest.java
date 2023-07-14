package sample.cafekiosk.spring.api.service.order;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

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
    private OrderService orderService;

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