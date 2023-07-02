package sample.cafekiosk.unit.beverages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("아메키라노 클래스 테스트")
class AmericanoTest {
    @DisplayName("아메키라노 객체의 이름 검증")
    @Test
    void getName() {
        final var americano = new Americano();

        assertEquals(americano.getName(), "아메리카노");
    }
}