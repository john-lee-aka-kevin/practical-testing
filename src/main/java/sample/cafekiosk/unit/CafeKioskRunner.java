package sample.cafekiosk.unit;

/**
 * 카페 키오스크를 실행시키기 위한 Runner
 */
public class CafeKioskRunner {
    public static void main(final String[] args) {
        final var cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());
        System.out.println(">>>> 아메리카노 추가");

        cafeKiosk.add(new Latte());
        System.out.println(">>>> 라떼 추가");

        var totalPrice = cafeKiosk.calculateTotalPrice();
        System.out.println("총 주문가격 : " + totalPrice);
    }
}
