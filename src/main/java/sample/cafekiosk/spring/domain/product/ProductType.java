package sample.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 상품의 타입 Enum
 */
@Getter
@RequiredArgsConstructor
public enum ProductType {
    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");

    /**
     * Enum의 한글명
     */
    private final String text;

    /**
     * 전달 받은 상품 타입이 재고 처리가 필요한 상품 타입에 포함되는지 체크
     *
     * @param type 상품 타입 enum
     *
     * @return 재고 처리가 필요한 상품 타입일 경우 true, 그렇지 않을 경우 false
     */
    public static boolean containsStockType(final ProductType type) {
        return List.of(BOTTLE, BAKERY).contains(type);
    }
}
