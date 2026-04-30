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
        boolean choice = random.nextBoolean();
        Checkout checkout;
        if  (choice) {
            checkout = new Checkout(new BlikPayment());
        } else {
            checkout = new Checkout(new CardPayment());
        }
        System.out.println(checkout.finalizeOrder(199.99));
    }
}

