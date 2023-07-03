package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverages.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 카페 키오스크
 */
@Getter
public class CafeKiosk {
    /**
     * 가게 오픈 시간 (10:00)
     */
    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);

    /**
     * 가게 마감 시간 (22:00)
     */
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);
    
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
        final LocalDateTime currentDateTime = LocalDateTime.now();
        final LocalTime currentTime = currentDateTime.toLocalTime();
        final boolean unavailableOrderTime = currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME); // 현재 시간이 오픈시간(10:00) 보다 이르거나, 마감시간(22:00) 보다 늦으면 주문 불가 시간
        
        if (unavailableOrderTime) throw new IllegalArgumentException("주문 가능 시간이 아닙니다. 관리자에게 문의하세요.");
        
        return new Order(currentDateTime, this.beverages);
    }
}
