package _10_wzorce._03_factory.code;

import java.util.List;

/**
 * Factory Method + Abstract Factory.
 */
public class FactoryDemo {

    // ─── FACTORY METHOD ───────────────────────────────────────────────────────
    interface Notification {
        void send(String message);
        String getType();
    }

    static class EmailNotification implements Notification {
        private final String recipient;
        EmailNotification(String recipient) { this.recipient = recipient; }
        @Override public void send(String message) {
            System.out.println("  [EMAIL → " + recipient + "] " + message);
        }
        @Override public String getType() { return "EMAIL"; }
    }

    static class SmsNotification implements Notification {
        private final String phone;
        SmsNotification(String phone) { this.phone = phone; }
        @Override public void send(String message) {
            System.out.println("  [SMS → " + phone + "] " + message.substring(0, Math.min(message.length(), 40)));
        }
        @Override public String getType() { return "SMS"; }
    }

    static class PushNotification implements Notification {
        private final String deviceId;
        PushNotification(String deviceId) { this.deviceId = deviceId; }
        @Override public void send(String message) {
            System.out.println("  [PUSH → " + deviceId + "] " + message);
        }
        @Override public String getType() { return "PUSH"; }
    }

    // Factory Method — decyzja w podklasie
    abstract static class NotificationService {
        protected abstract Notification createNotification(String target);

        public void alertUsers(List<String> targets, String message) {
            System.out.println("  Alerting " + targets.size() + " users via " + getChannel() + "...");
            for (String t : targets) {
                Notification n = createNotification(t);
                n.send(message);
            }
        }

        abstract String getChannel();
    }

    static class EmailService extends NotificationService {
        @Override protected Notification createNotification(String email) {
            return new EmailNotification(email);
        }
        @Override String getChannel() { return "Email"; }
    }

    static class SmsService extends NotificationService {
        @Override protected Notification createNotification(String phone) {
            return new SmsNotification(phone);
        }
        @Override String getChannel() { return "SMS"; }
    }

    // Statyczna metoda fabrykująca (Static Factory)
    static Notification createNotification(String type, String target) {
        return switch (type.toUpperCase()) {
            case "EMAIL" -> new EmailNotification(target);
            case "SMS"   -> new SmsNotification(target);
            case "PUSH"  -> new PushNotification(target);
            default      -> throw new IllegalArgumentException("Nieznany typ: " + type);
        };
    }

    // ─── ABSTRACT FACTORY ────────────────────────────────────────────────────
    interface Button { void render(); void onClick(); }
    interface Checkbox { void render(); void check(boolean value); }

    // Windows UI
    static class WindowsButton implements Button {
        @Override public void render() { System.out.println("  [Windows Button] ╔═══╗ ║ OK ║ ╚═══╝"); }
        @Override public void onClick() { System.out.println("  [Windows] Click!"); }
    }
    static class WindowsCheckbox implements Checkbox {
        @Override public void render() { System.out.println("  [Windows Checkbox] [✓] Option"); }
        @Override public void check(boolean v) { System.out.println("  [Windows] Checked: " + v); }
    }

    // Mac UI
    static class MacButton implements Button {
        @Override public void render() { System.out.println("  [Mac Button] ( OK )"); }
        @Override public void onClick() { System.out.println("  [Mac] Click!"); }
    }
    static class MacCheckbox implements Checkbox {
        @Override public void render() { System.out.println("  [Mac Checkbox] ◉ Option"); }
        @Override public void check(boolean v) { System.out.println("  [Mac] Checked: " + v); }
    }

    // Abstract Factory — rodzina spójnych komponentów
    interface GUIFactory {
        Button createButton();
        Checkbox createCheckbox();
    }

    static class WindowsFactory implements GUIFactory {
        @Override public Button createButton()     { return new WindowsButton(); }
        @Override public Checkbox createCheckbox() { return new WindowsCheckbox(); }
    }

    static class MacFactory implements GUIFactory {
        @Override public Button createButton()     { return new MacButton(); }
        @Override public Checkbox createCheckbox() { return new MacCheckbox(); }
    }

    static GUIFactory detectPlatform() {
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.contains("mac")) return new MacFactory();
        return new WindowsFactory(); // default
    }

    static void buildUI(GUIFactory factory) {
        Button btn = factory.createButton();
        Checkbox chk = factory.createCheckbox();
        btn.render();
        chk.render();
        btn.onClick();
        chk.check(true);
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Factory Method ===\n");

        System.out.println("A) Static Factory Method:");
        Notification n1 = createNotification("EMAIL", "alice@example.com");
        Notification n2 = createNotification("SMS", "+48123456789");
        Notification n3 = createNotification("PUSH", "device-abc-123");
        n1.send("Twoje zamowienie zostalo zrealizowane!");
        n2.send("Zamowienie gotowe");
        n3.send("Nowa wiadomosc dla Ciebie");

        System.out.println("\nB) Factory Method (podklasa decyduje):");
        NotificationService emailSvc = new EmailService();
        emailSvc.alertUsers(List.of("alice@example.com", "bob@example.com"),
                "Serwis bedzie niedostepny od 2:00 do 4:00.");

        NotificationService smsSvc = new SmsService();
        smsSvc.alertUsers(List.of("+48111222333", "+48444555666"),
                "Alert: serwis niedostepny");

        System.out.println("\n=== Abstract Factory ===\n");
        System.out.println("Wykryta platforma: " + System.getProperty("os.name"));

        System.out.println("\nUI na WindowsFactory:");
        buildUI(new WindowsFactory());

        System.out.println("\nUI na MacFactory:");
        buildUI(new MacFactory());

        System.out.println("\nUI wykryte automatycznie:");
        buildUI(detectPlatform());
    }
}

