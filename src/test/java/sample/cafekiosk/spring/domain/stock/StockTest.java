package sample.cafekiosk.spring.domain.stock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("재고 Entity Test")
class StockTest {
    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인 한다.")
    @Test
    void isQuantityLessThan() {
        // Given
        final var stock = Stock.create("001", 1);
        final int quantity = 2;

        // When
        final var result = stock.isQuantityLessThan(2);

        // Then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity() {
        // Given
        final var stock = Stock.create("001", 1);
        int quantity = 1;

        // When
        stock.deductQuantity(quantity);

        // Then
        Assertions.assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    void deductQuantityWithException() {
        // Given
        final var stock = Stock.create("001", 1);
        int quantity = 2;

        // When & Then
        Assertions.assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");
    }
}