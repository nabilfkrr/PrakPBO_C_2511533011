package shop.shipping;

/** Same Day: Rp25.000 sampai 3 kg, lalu Rp10.000 per kg berikutnya. */
public class SameDayRate implements ShippingRate {
    private static final long BASE = 25_000;
    private static final int BASE_GRAMS = 3_000;
    private static final long PER_KG = 10_000;

    @Override
    public long costFor(int grams) {
        if (grams == 0) return 0;
        if (grams <= BASE_GRAMS) return BASE;
        int extraGrams = grams - BASE_GRAMS;
        return BASE + ((extraGrams + 999) / 1000) * PER_KG;
    }
}