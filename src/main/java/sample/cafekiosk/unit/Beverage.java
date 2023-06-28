package sample.cafekiosk.unit;

/**
 * 음료 인터페이스
 */
public interface Beverage {
    /**
     * 음료 이름 조회
     *
     * @return 음료의 이름
     */
    String getName();

    /**
     * 음료 가격 조회
     *
     * @return 음료의 가격
     */
    int getPrice();
}
