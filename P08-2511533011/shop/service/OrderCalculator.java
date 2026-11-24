package shop.service;

import shop.discount.DiscountStrategy;
import shop.model.Order;
import shop.model.Shippable;
import shop.shipping.RegularRate;
import shop.shipping.ShippingRate;

public class OrderCalculator {

    private final ShippingRate shippingRate;

    public OrderCalculator(long ratePerKg) {
        this(new RegularRate(ratePerKg));
    }

    public OrderCalculator(ShippingRate shippingRate) {
        this.shippingRate = shippingRate;
    }

    public Bill calculate(Order order, DiscountStrategy discountStrategy) {
        // [R1] Perhitungan total order
        long subtotal = 0;
        for (Order.Line line : order.lines()) {
            subtotal += line.total();
        }

        // [R2] Perhitungan total diskon
        long discount = discountStrategy.discountFor(subtotal);

        // [R3] Perhitungan total berat barang yang dikirim
        int grams = 0;
        for (Order.Line line : order.lines()) {
            if (line.product() instanceof Shippable item) {
                grams += item.getWeightGram() * line.qty();
            }
        }

        // [R4] Perhitungan ongkir dan final total
        long shipping = shippingRate.costFor(grams);
        long total = subtotal - discount + shipping;

        return new Bill(subtotal, discount, shipping, total);
    }
}