package sample.cafekiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("주문 도메인 테스트")
class OrderTest {
    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // Given
        final var products = List.of(
                this.createProduct("001", 1_000),
                this.createProduct("002", 2_000)
        );

        // When
        final var order = Order.create(products, LocalDateTime.now());

        // Then
        Assertions.assertThat(order.getTotalPrice()).isEqualTo(3_000);
    }

    @DisplayName("주문 생성 시 주문의 상태는 INIT이다.")
    @Test
    void init() {
        // Given
        final var products = List.of(
                this.createProduct("001", 1_000),
                this.createProduct("002", 2_000)
        );

        // When
        final var order = Order.create(products, LocalDateTime.now());

        // Then
        Assertions.assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시 등록 시간을 기록한다.")
    @Test
    void registeredDateTime() {
        // Given
        final var registeredDateTime = LocalDateTime.now();
        final var products = List.of(
                this.createProduct("001", 1_000),
                this.createProduct("002", 2_000)
        );

        // When
        final var order = Order.create(products, registeredDateTime);

        // Then
        Assertions.assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(final String productNumber, final int price) {
        return Product.builder()
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("메뉴 이름")
                .build();
    }
}