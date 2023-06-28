package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverages.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 카페 키오스크
 */
@Getter
public class CafeKiosk {
    /**
     * 주문한 음료 목록
     */
    private final List<Beverage> beverages = new ArrayList<>();

    /**
     * 주문 (음료 추가)
     *
     * @param beverage 음료
     */
    public void add(final Beverage beverage) {
        this.beverages.add(beverage);
    }

    /**
     * 주문 취소 (음료 제거)
     *
     * @param beverage 음료
     */
    public void remove(final Beverage beverage) {
        this.beverages.remove(beverage);
    }

    /**
     * 주문 전체 취소 (음료 전체 제거)
     */
    public void clear() {
        this.beverages.clear();
    }

    /**
     * 주문된 음료의 총 가격정보
     *
     * @return 카페 키오스크를 통해 주문한 음료들의 가격 총합
     */
    public int calculateTotalPrice() {
        return this.beverages.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }

    /**
     * 주문하기
     *
     * @return 주문 정보
     */
    public Order createOrder() {
        return new Order(LocalDateTime.now(), this.beverages);
    }
}
