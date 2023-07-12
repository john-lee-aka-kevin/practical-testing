package sample.cafekiosk.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.service.ProductService;
import sample.cafekiosk.spring.api.service.product.respose.ProductResponse;

import java.util.List;

/**
 * 상품 API
 */
@RequiredArgsConstructor
@RestController
public class ProductController {
    /**
     * 상품 Service
     */
    private final ProductService productService;

    /**
     * 판매하는 상품 리스트 조회 (판매중, 판매보류)
     *
     * @return 상품 리스트
     */
    @GetMapping("/api/v1/products/selling")
    public List<ProductResponse> getSellingProducts() {
        return this.productService.getSellingProducts();
    }
}
