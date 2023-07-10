package sample.cafekiosk.spring.api.service.product.respose;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

/**
 * 상품 응답 DTO
 */
@Getter
public class ProductResponse {
    /**
     * 고유 키 값
     */
    private Long id;

    /**
     * 상품 번호
     */
    private String productNumber;

    /**
     * 상품 타입
     */
    private ProductType type;

    /**
     * 판매 상태
     */
    private ProductSellingStatus sellingStatus;

    /**
     * 상품명
     */
    private String name;

    /**
     * 상품 가격
     */
    private int price;

    /**
     * 빌더를 만들기 위한 모든 파라미터가 있는 생성자
     *
     * @param id 고유 키 값
     * @param productNumber 상품 번호
     * @param type 상품 타입
     * @param sellingStatus 판매 상태
     * @param name 상품명
     * @param price 상품 가격
     */
    @Builder
    private ProductResponse(final Long id, final String productNumber, final ProductType type, final ProductSellingStatus sellingStatus, final String name, final int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    /**
     * Product Entity를 ProductResponse DTO로 변환
     *
     * @param product 상품 Entity
     *
     * @return API 응답을 위한 DTO
     */
    public static ProductResponse of(final Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .type(product.getType())
                .sellingStatus(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
