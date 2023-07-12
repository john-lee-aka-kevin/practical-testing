package sample.cafekiosk.spring.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 주문 Entity
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {
    /**
     * 주문 번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 주문 상태
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /**
     * 주문 총 금액
     */
    private int totalPrice;

    /**
     * 주문 시간
     */
    private LocalDateTime registeredDateTime;
}
