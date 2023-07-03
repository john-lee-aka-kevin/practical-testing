package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverages.Americano;
import sample.cafekiosk.unit.beverages.Latte;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("카페 키오스크 테스트")
class CafeKioskTest {
    @DisplayName("음료 추가하기 기능 테스트 - 수동 테스트")
    @Test
    void add_manual_test() {
        final var cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());

        System.out.println(">>>> 담긴 음료의 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @DisplayName("음료 추가하기 기능 테스트")
    @Test
    void add() {
        final var cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());

        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(1);
        Assertions.assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("음료 다건 추가하기 기능 테스트")
    @Test
    void add_several_beverages() {
        final var cafeKiosk = new CafeKiosk();
        final var americano = new Americano();

        cafeKiosk.add(americano, 2);
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(2);
        Assertions.assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        Assertions.assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @DisplayName("음료 0잔 추가하기시 예외 테스트")
    @Test
    void add_zero_beverages() {
        final var cafeKiosk = new CafeKiosk();

        Assertions.assertThatThrownBy(() -> cafeKiosk.add(new Americano(), 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 부터 주문하실 수 있습니다.");
    }

    @DisplayName("음료 삭제하기 기능 테스트")
    @Test
    void remove() {
        final var cafeKiosk = new CafeKiosk();
        final var americano = new Americano();

        cafeKiosk.add(americano);
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        Assertions.assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @DisplayName("음료 전체 삭제하기 기능 테스트")
    @Test
    void clear() {
        final var cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());
        cafeKiosk.add(new Latte());
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        Assertions.assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @DisplayName("주문하기 기능 테스트")
    @Test
    void createOrder() {
        final var cafeKiosk = new CafeKiosk();
        final var americano = new Americano();

        cafeKiosk.add(americano);

        final var nowDate = LocalDateTime.of(2023, 7, 3, 10, 0);
        final var order = cafeKiosk.createOrder(nowDate);

        Assertions.assertThat(order.getBeverages()).hasSize(1);
        Assertions.assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }
    
    @DisplayName("주문하기 기능 - 예외 테스트(주문 불가 시간 주문)")
    @Test
    void createOrderUnavailableOrderTime() {
        final var cafeKiosk = new CafeKiosk();
        final var americano = new Americano();

        cafeKiosk.add(americano);

        final var unavailableOrderDateTime = LocalDateTime.of(2023, 7, 3, 9, 59);

        Assertions.assertThatThrownBy(() -> cafeKiosk.createOrder(unavailableOrderDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 가능 시간이 아닙니다. 관리자에게 문의하세요.");
    }
}