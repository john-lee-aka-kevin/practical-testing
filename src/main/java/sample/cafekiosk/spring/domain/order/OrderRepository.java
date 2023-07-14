package sample.cafekiosk.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 주문 Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
