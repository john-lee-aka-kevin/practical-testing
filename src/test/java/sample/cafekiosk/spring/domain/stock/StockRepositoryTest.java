package sample.cafekiosk.spring.domain.stock;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("재고 Repository 테스트")
@DataJpaTest
class StockRepositoryTest {
    @Autowired
    private StockRepository stockRepository;

    @DisplayName("상품번호 리스트로 재고 정보 리스트를 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // Given
        final var stock1 = Stock.create("001", 1);
        final var stock2 = Stock.create("002", 2);
        final var stock3 = Stock.create("003", 3);

        this.stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // When
        final var stocks = this.stockRepository.findAllByProductNumberIn(List.of("001", "002"));

        // Then
        Assertions.assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", 1),
                        Tuple.tuple("002", 2)
                );
    }
}