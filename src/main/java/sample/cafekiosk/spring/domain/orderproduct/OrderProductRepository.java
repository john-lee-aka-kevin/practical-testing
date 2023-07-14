package sample.cafekiosk.spring.domain.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 주문 상품 매핑정보 Repository
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
