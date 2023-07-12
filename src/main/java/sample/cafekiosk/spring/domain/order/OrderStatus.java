package sample.cafekiosk.spring.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 상태 Enum
 */
@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    INIT("주문생성"),
    CANCELED("주문취소"),
    PAYMENT_COMPLETE("결제완료"),
    PAYMENT_FAILED("결제실패"),
    RECEIVED("주문접수"),
    COMPLETED("처리완료");

    /**
     * Enum의 한글명
     */
    private final String text;
}
