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

    /**
     * 재고 정보 생성
     *
     * @param productNumber 상품 번호
     * @param quantity 재고 수량
     *
     * @return 상품에 대한 재고 정보
     */
    public static Stock create(final String productNumber, final int quantity) {
        return Stock.builder()
                .productNumber(productNumber)
                .quantity(quantity)
                .build();
    }

    /**
     * 전달받은 수량보다 현재 재고가 적은지 체크
     *
     * @param quantity 전달받은 수량
     *
     * @return 전달받은 수량보다 작을 경우 true, 크거나 같을 경우 false
     */
    public boolean isQuantityLessThan(final int quantity) {
        return this.quantity < quantity;
    }

    /**
     * 전달받은 수량 만큼 재고를 차감
     *
     * @param quantity 전달받은 수량
     */
    public void deductQuantity(final int quantity) {
        if (isQuantityLessThan(quantity)) {
            throw new IllegalArgumentException("차감할 재고 수량이 없습니다.");
        }

        this.quantity -= quantity;
    }
}
