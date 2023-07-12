package sample.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 판매상태 Enum
 */
@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {
    SELLING("판매중"),
    HOLD("판매보류"),
    STOP_SELLING("판매중지");

    /**
     * Enum의 한글명
     */
    private final String text;

    /**
     * 화면에 표시할 판매상태 목록
     *
     * @return 화면에 표시할 판매상태 enum 리스트
     */
    public static List<ProductSellingStatus> forDisplay() {
        return List.of(SELLING, HOLD);
    }
}
