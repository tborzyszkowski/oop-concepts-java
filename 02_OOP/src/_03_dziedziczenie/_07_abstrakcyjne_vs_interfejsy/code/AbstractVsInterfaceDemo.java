package _03_dziedziczenie._07_abstrakcyjne_vs_interfejsy.code;

import java.util.Random;

abstract class AbstractNotifier {
    void notifyUser(String message) {
        System.out.println("[before]");
        send(message);
        System.out.println("[after]");
    }

    abstract void send(String message);
}

interface Retryable {
    int maxRetries();
}

interface TimeToSend {
    String timeToSend();
}

class EmailNotifier
        extends AbstractNotifier
        implements Retryable, TimeToSend {
    @Override
    void send(String message) {
        System.out.println("Time: " + timeToSend() + "\nEmail: " + message);
    }

    @Override
    public int maxRetries() {
        return 3;
    }

    @Override
    public String timeToSend() {
        return "Now!!!";
    }
}

class SmsNotifier
        extends AbstractNotifier
        implements Retryable, TimeToSend {
    @Override
    void send(String message) {
        System.out.println("Time: " + timeToSend() + "\nSMS: " + message);
    }

    @Override
    public int maxRetries() {
        return 5;
    }

    @Override
    public String timeToSend() {
        return "For a while !!!";
    }
}

public class AbstractVsInterfaceDemo {
    public static void main(String[] args) {
        EmailNotifier emailNotifier = new EmailNotifier();
        emailNotifier.notifyUser("Witaj");
        System.out.println("retries=" + emailNotifier.maxRetries());
        System.out.println("timeToSend=" + emailNotifier.timeToSend());

        System.out.println("====================================");
        SmsNotifier smsNotifier = new SmsNotifier();

        AbstractNotifier abstractNotifier;
        Random random = new Random();
        if (random.nextBoolean()) {
            abstractNotifier = emailNotifier;
        } else {
            abstractNotifier = smsNotifier;
        }
        System.out.println("abstractNotifier=" + abstractNotifier.getClass().getSimpleName());
        abstractNotifier.notifyUser("Hello, World!");
    }
}

