package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverages.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
     * 주문 (음료 추가) - 고도화
     *
     * <pre>
     * 주문시 음료의 수량도 받을 수 있도록 고도화
     * </pre>
     *
     * @param beverage 음료
     * @param count 수량
     *
     * @throws IllegalArgumentException count(수량)이 0 이하일 경우 예외
     */
    public void add(final Beverage beverage, final int count) throws IllegalArgumentException {
        if (count <= 0) throw new IllegalArgumentException("음료는 1잔 이상 부터 주문하실 수 있습니다.");

        IntStream.range(0, count)
                .forEach((i) -> this.beverages.add(beverage));
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
