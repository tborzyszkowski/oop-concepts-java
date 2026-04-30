package _03_dziedziczenie._07_abstrakcyjne_vs_interfejsy.code;

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

public class AbstractVsInterfaceDemo {
    public static void main(String[] args) {
        EmailNotifier notifier = new EmailNotifier();
        notifier.notifyUser("Witaj");
        System.out.println("retries=" + notifier.maxRetries());
        System.out.println("timeToSend=" + notifier.timeToSend());
    }
}

