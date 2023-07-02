package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverages.Americano;
import sample.cafekiosk.unit.beverages.Latte;

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

    @DisplayName("음료 추가하기 기능 테스트 - 자동화 테스트")
    @Test
    void add() {
        final var cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());

        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(1);
        Assertions.assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("음료 삭제하기 기능 테스트 - 자동화 테스트")
    @Test
    void remove() {
        final var cafeKiosk = new CafeKiosk();
        final var americano = new Americano();

        cafeKiosk.add(americano);
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        Assertions.assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @DisplayName("음료 전체 삭제하기 기능 테스트 - 자동화 테스트")
    @Test
    void clear() {
        final var cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());
        cafeKiosk.add(new Latte());
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        Assertions.assertThat(cafeKiosk.getBeverages()).isEmpty();
    }
}