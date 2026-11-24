package shop.tantangan;

import shop.discount.NoDiscount;
import shop.model.Address;
import shop.model.Order;
import shop.model.PhysicalProduct;
import shop.service.Bill;
import shop.service.OrderCalculator;
import shop.shipping.RegularRate;
import shop.shipping.SameDayRate;

public class TantanganA {
    public static void main(String[] args) {
        Order order = new Order("TA-1", new Address("Penguji", "Lab"))
                .add(new PhysicalProduct("TAS", "Tas ransel", 150_000, 4_500), 1);

        Bill reguler = new OrderCalculator(new RegularRate(10_000)).calculate(order, new NoDiscount());
        Bill sameDay = new OrderCalculator(new SameDayRate()).calculate(order, new NoDiscount());

        System.out.println("Ongkir reguler : " + reguler.shipping());
        System.out.println("Ongkir same day: " + sameDay.shipping());
    }
}