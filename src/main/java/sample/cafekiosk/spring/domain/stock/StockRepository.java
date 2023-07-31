package sample.cafekiosk.spring.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 재고 Repository
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
