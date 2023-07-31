package sample.cafekiosk.spring.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 재고 Repository
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    /**
     * 상품번호에 해당하는 재고 정보 리스트 조회
     *
     * @param productNumbers 조회에 사용할 상품번호 리스트
     *
     * @return 재고 정보 리스트
     */
    List<Stock> findAllByProductNumberIn(final List<String> productNumbers);
}
