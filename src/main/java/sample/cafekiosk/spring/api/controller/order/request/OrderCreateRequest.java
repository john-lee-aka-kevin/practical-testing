package sample.cafekiosk.spring.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 주문 생성 요청 DTO
 */
@Getter
public class OrderCreateRequest {
    /**
     * 상품 번호 리스트
     */
    private List<String> productNumbers;

    @Builder
    private OrderCreateRequest(final List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
