package _03_dziedziczenie._06_polimorfizm_i_wzorce.code;

import java.util.Random;

interface PaymentStrategy {
    String pay(double amount);
}

class CardPayment implements PaymentStrategy {
    public String pay(double amount) { return "Card: " + amount; }
}

class BlikPayment implements PaymentStrategy {
    public String pay(double amount) { return "Blik: " + amount; }
}

class CashPayment implements PaymentStrategy {
    public String pay(double amount) { return "Cash: " + amount; }
}

class Checkout {
    private final PaymentStrategy paymentStrategy;

    Checkout(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    String finalizeOrder(double amount) {
        return paymentStrategy.pay(amount);
    }
}

public class PolymorphismPatternsDemo {
    public static void main(String[] args) {
        Random random = new Random();
        int choice = random.nextInt(3);
        Checkout checkout;
        switch (choice) {
            case 0:
                checkout = new Checkout(new BlikPayment());
                break;
            case 1:
                checkout = new Checkout(new CardPayment());
                break;
            default:
                checkout = new Checkout(new CashPayment());
        }
        System.out.println(choice + ": " + checkout.finalizeOrder(199.99));
    }
}

