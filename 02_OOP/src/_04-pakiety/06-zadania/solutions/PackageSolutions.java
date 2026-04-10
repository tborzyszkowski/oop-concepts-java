package exercises.solutions;

public class PackageSolutions {
    public String buildQualifiedMessage() {
        Teacher teacher = new Teacher("dr Nowak");
        return "Prowadzacy: " + teacher.name();
    }

    public String mergeOrders() {
        BillingOrder billing = new BillingOrder("B-10", 150.0);
        ShippingOrder shipping = new ShippingOrder("S-10", "Krakow");
        return billing.id() + " + " + shipping.id();
    }

    public static void main(String[] args) {
        PackageSolutions s = new PackageSolutions();
        System.out.println(s.buildQualifiedMessage());
        System.out.println(s.mergeOrders());
    }
}


