package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverages.Americano;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("카페 키오스크 테스트")
class CafeKioskTest {
    @DisplayName("음료 추가하기 기능 테스트")
    @Test
    void add() {
        final var cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());

        System.out.println(">>>> 담긴 음료의 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
    }
}