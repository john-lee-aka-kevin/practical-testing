package sample.cafekiosk.spring.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.*;

/**
 * 상품 Entity
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {
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
     * 상품 타입
     */
    @Enumerated(EnumType.STRING)
    private ProductType type;

    /**
     * 판매 상태
     */
    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;

    /**
     * 상품명
     */
    private String name;

    /**
     * 상품 가격
     */
    private int price;
}
