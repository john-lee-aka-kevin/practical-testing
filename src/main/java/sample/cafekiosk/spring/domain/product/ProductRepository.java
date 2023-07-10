package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 상품 Repository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * 인자로 받은 판매상태에 해당하는 상품 리스트 조회
     *
     * @param sellingStatuses 조회에 사용할 판매상태 조회조건
     *
     * @return 상품 리스트
     */
    List<Product> findAllBySellingStatusIn(final List<ProductSellingStatus> sellingStatuses);
}
