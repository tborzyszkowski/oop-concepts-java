package _04_pakiety._05_importy_i_kolizje.code.complex.reporting;

import _04_pakiety._05_importy_i_kolizje.code.complex.shipping.Order;

public class ImportCollisionDemo {
    public static void main(String[] args) {
        _04_pakiety._05_importy_i_kolizje.code.complex.billing.Order billingOrder =
            new _04_pakiety._05_importy_i_kolizje.code.complex.billing.Order("B-01", 199.99);

        Order shippingOrder =
            new Order("S-01", "Warszawa");

        System.out.println("Billing: " + billingOrder);
        System.out.println("Shipping: " + shippingOrder);
    }
}

