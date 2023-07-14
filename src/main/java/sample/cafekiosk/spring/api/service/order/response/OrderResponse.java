package sample.cafekiosk.spring.api.service.order.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.api.service.product.respose.ProductResponse;
import sample.cafekiosk.spring.domain.order.Order;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 주문 응답 결과 DTO
 */
@Getter
public class OrderResponse {
    /**
     * 주문 번호
     */
    private Long id;

    /**
     * 주문 총 금액
     */
    private int totalPrice;

    /**
     * 주문 시간
     */
    private LocalDateTime registeredDateTime;

    /**
     * 주문에 포함된 상품 리스트 (상품 응답 DTO 형태)
     */
    private List<ProductResponse> products;

    @Builder
    private OrderResponse(final Long id, final int totalPrice, final LocalDateTime registeredDateTime, final List<ProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(final Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDateTime())
                .products(
                        order.getOrderProducts().stream()
                                .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                                .toList()
                )
                .build();
    }
}
