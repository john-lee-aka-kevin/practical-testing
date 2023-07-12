package sample.cafekiosk.spring.domain.orderproduct;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.product.Product;

import javax.persistence.*;

/**
 * 주문(Order)와 상품(Product)의 매핑 관계 Entity
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderProduct extends BaseEntity {
    /**
     * 상품 주문 번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 주문 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    /**
     * 상품 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public OrderProduct(final Order order, final Product product) {
        this.order = order;
        this.product = product;
    }
}
