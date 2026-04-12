package imports.complex.reporting;

public class ImportCollisionDemo {
    public static void main(String[] args) {
        imports.complex.billing.Order billingOrder =
            new imports.complex.billing.Order("B-01", 199.99);

        imports.complex.shipping.Order shippingOrder =
            new imports.complex.shipping.Order("S-01", "Warszawa");

        System.out.println("Billing: " + billingOrder);
        System.out.println("Shipping: " + shippingOrder);
    }
}

