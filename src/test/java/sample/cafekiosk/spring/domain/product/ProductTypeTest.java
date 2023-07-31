package sample.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("상품 타입 enum 테스트")
class ProductTypeTest {
    @DisplayName("상품 타입이 재고 관련 타입이 아닌지를 체크한다.")
    @Test
    void containsStockTypeisFalse() {
        // Given
        var givenType = ProductType.HANDMADE;

        // When
        var result = ProductType.containsStockType(givenType);

        // Then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockTypeisTrue() {
        // Given
        var givenType = ProductType.BAKERY;

        // When
        var result = ProductType.containsStockType(givenType);

        // Then
        Assertions.assertThat(result).isTrue();
    }
}