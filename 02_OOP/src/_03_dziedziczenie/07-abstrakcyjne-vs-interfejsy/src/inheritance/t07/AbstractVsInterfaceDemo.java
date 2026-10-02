package inheritance.t07;

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

class EmailNotifier extends AbstractNotifier implements Retryable {
    @Override
    void send(String message) {
        System.out.println("Email: " + message);
    }

    @Override
    public int maxRetries() {
        return 3;
    }
}

public class AbstractVsInterfaceDemo {
    public static void main(String[] args) {
        EmailNotifier notifier = new EmailNotifier();
        notifier.notifyUser("Witaj");
        System.out.println("retries=" + notifier.maxRetries());
    }
}

