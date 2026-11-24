package shop.tantangan;

import java.util.List;
import shop.model.Address;
import shop.model.Order;
import shop.model.PhysicalProduct;
import shop.notify.SmsChannel;
import shop.notify.WhatsAppChannel;
import shop.service.Notifier;

public class TantanganB {
    public static void main(String[] args) {
        Order order = new Order("TB-1", new Address("Penguji", "Lab"))
                .add(new PhysicalProduct("KOPI", "Kopi Gayo 250g", 85_000, 300), 1);

        Notifier dua = new Notifier(List.of(new SmsChannel(), new WhatsAppChannel()));
        dua.notifyPaid(order, 95_000);
    }
}