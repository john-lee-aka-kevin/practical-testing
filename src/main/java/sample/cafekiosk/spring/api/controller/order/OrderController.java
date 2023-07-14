package sample.cafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;

/**
 * 주문 API
 */
@RequiredArgsConstructor
@RestController
public class OrderController {
    /**
     * 주문 Service
     */
    private final OrderService orderService;

    /**
     * 주문 생성 요청
     *
     * @param request 주문 생성 요청 DTO
     *
     * @return 주문 정보
     */
    @PostMapping("/api/v1/orders/new")
    public OrderResponse createOrder(@RequestBody final OrderCreateRequest request) {
        final LocalDateTime registeredDateTime = LocalDateTime.now();

        return this.orderService.createOrder(request, registeredDateTime);
    }
}
