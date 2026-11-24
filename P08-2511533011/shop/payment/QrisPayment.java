package shop.payment;

import shop.exception.PaymentFailedException;

public class QrisPayment implements PaymentMethod {
    private static final long MAKS = 10_000_000L;

    @Override 
    public String name() {
        return "QRIS";
    }

    @Override 
    public void pay(long amount) throws PaymentFailedException {
        if (amount > MAKS) {
            throw new PaymentFailedException ("transaksi QRIS maksimal Rp.10.000.000");
        }
    }
}
