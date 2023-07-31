package sample.cafekiosk.spring.domain.stock;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 재고 Entity
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock extends BaseEntity {
    /**
     * 고유 키 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 상품 번호
     */
    private String productNumber;

    /**
     * 재고 수량
     */
    private int quantity;

    @Builder
    private Stock(final String productNumber, final int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }
}
