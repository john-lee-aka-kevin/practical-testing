package sample.cafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;

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
     */
    @PostMapping("/api/v1/orders/new")
    public void createOrder(@RequestBody final OrderCreateRequest request) {
        final LocalDateTime registeredDateTime = LocalDateTime.now();

        this.orderService.createOrder(request, registeredDateTime);
    }
}
