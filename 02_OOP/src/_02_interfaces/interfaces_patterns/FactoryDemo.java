package _02_interfaces.interfaces_patterns;

/** Factory Method — interfejs produktu */
interface Notification {
    void send(String message);
    String getType();
}

class EmailNotification implements Notification {
    private final String address;
    EmailNotification(String address) { this.address = address; }
    @Override public void send(String msg) {
        System.out.println("[EMAIL -> " + address + "] " + msg);
    }
    @Override public String getType() { return "EMAIL"; }
}

class SmsNotification implements Notification {
    private final String phone;
    SmsNotification(String phone) { this.phone = phone; }
    @Override public void send(String msg) {
        System.out.println("[SMS -> " + phone + "] " + msg.substring(0, Math.min(160, msg.length())));
    }
    @Override public String getType() { return "SMS"; }
}

class PushNotification implements Notification {
    private final String deviceToken;
    PushNotification(String token) { this.deviceToken = token; }
    @Override public void send(String msg) {
        System.out.println("[PUSH -> " + deviceToken.substring(0, 8) + "...] " + msg);
    }
    @Override public String getType() { return "PUSH"; }
}

/** Factory Method — interfejs fabryki (jest @FunctionalInterface!) */
@FunctionalInterface
interface NotificationFactory {
    Notification create(String recipient);

    /** static factory — wybiera implementację wg kanału */
    static NotificationFactory forChannel(String channel) {
        return switch (channel.toUpperCase()) {
            case "EMAIL" -> recipient -> new EmailNotification(recipient);
            case "SMS"   -> recipient -> new SmsNotification(recipient);
            case "PUSH"  -> recipient -> new PushNotification(recipient);
            default      -> throw new IllegalArgumentException("Nieznany kanal: " + channel);
        };
    }
}

/**
 * WZORZEC FACTORY METHOD
 * Tworzenie obiektów bez wiązania się z konkretnymi klasami.
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_patterns/FactoryDemo.java
 *   java -cp out interfaces_patterns.FactoryDemo
 */
public class FactoryDemo {
    public static void main(String[] args) {
        String[][] users = {
            {"EMAIL", "jan@example.com"},
            {"SMS",   "+48600100200"},
            {"PUSH",  "a1b2c3d4e5f6g7h8"},
            {"EMAIL", "anna@example.com"}
        };

        System.out.println("=== Wysylanie powiadomien (Factory Method) ===");
        for (String[] u : users) {
            NotificationFactory factory = NotificationFactory.forChannel(u[0]);
            Notification n = factory.create(u[1]);
            n.send("Twoje zamowienie zostalo wyslane!");
        }

        // Lambda jako Factory Method
        System.out.println("\n=== Lambda jako fabryka ===");
        NotificationFactory logFactory = recipient ->
            new Notification() {
                @Override public void send(String msg) {
                    System.out.println("[LOG -> " + recipient + "] " + msg);
                }
                @Override public String getType() { return "LOG"; }
            };

        logFactory.create("system").send("Test logu");
    }
}

