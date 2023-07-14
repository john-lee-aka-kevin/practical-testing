package sample.cafekiosk.spring.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.orderproduct.OrderProduct;
import sample.cafekiosk.spring.domain.product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 주문에 대한 주문 상품 정보 리스트
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // 연관관계의 주인 = OrderProduct에 order = 상대쪽 필드?
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public Order(final List<Product> products, final LocalDateTime now) {
        this.orderStatus = OrderStatus.INIT;
        this.totalPrice = this.calculateTotalPrice(products);
        this.registeredDateTime = now;
        this.orderProducts = products.stream()
                .map(product -> new OrderProduct(this, product))
                .toList();
    }

    /**
     * 상품 리스트 정보로 주문 정보 생성
     *
     * @param products 상품 리스트
     * @param registeredDateTime 주문 시간
     *
     * @return 주문 정보
     */
    public static Order create(final List<Product> products, final LocalDateTime registeredDateTime) {
        return new Order(products, registeredDateTime);
    }

    /**
     * 주문에 대한 상품 총 금액 계산
     *
     * @param products 상품 리스트
     *
     * @return 상품에 대한 총 금액
     */
    private int calculateTotalPrice(final List<Product> products) {
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }
}
