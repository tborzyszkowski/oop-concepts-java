package _10_wzorce._14_zadania.code;

import java.util.*;

/**
 * Zadanie 1 — Singleton: Rejestr konfiguracji (Holder Idiom)
 * Zadanie 2 — Factory Method: Parser formatów
 * Zadanie 3 — Builder: Formularz HTML
 * Zadanie 4 — Decorator: Walidator pól
 * Zadanie 5 — Observer: EventBus dla sklepu
 * Zadanie 6 — Strategy: Kalkulator rabatów
 */
public class TaskSolutions {

    // ═══════════════════════════════════════════════════════
    // ZADANIE 1 — Singleton: AppRegistry (Holder Idiom)
    // ═══════════════════════════════════════════════════════

    static class AppRegistry {
        private final Map<String, String> properties = new LinkedHashMap<>();

        private AppRegistry() {
            // Domyślne wartości
            properties.put("app.name",    "MyApplication");
            properties.put("app.version", "1.0.0");
            properties.put("db.host",     "localhost");
            properties.put("db.port",     "5432");
            properties.put("log.level",   "INFO");
        }

        // Holder Idiom — thread-safe, leniwe ładowanie
        private static final class Holder {
            static final AppRegistry INSTANCE = new AppRegistry();
        }

        public static AppRegistry getInstance() {
            return Holder.INSTANCE;
        }

        public String get(String key) {
            return properties.get(key);
        }

        public void set(String key, String value) {
            properties.put(key, value);
        }

        public String getOrDefault(String key, String defaultVal) {
            return properties.getOrDefault(key, defaultVal);
        }

        public void print() {
            properties.forEach((k, v) -> System.out.printf("  %-20s = %s%n", k, v));
        }
    }

    // ═══════════════════════════════════════════════════════
    // ZADANIE 2 — Factory Method: Parser formatów
    // ═══════════════════════════════════════════════════════

    interface DataParser {
        List<Map<String, String>> parse(String input);
    }

    static class CsvParser implements DataParser {
        @Override
        public List<Map<String, String>> parse(String input) {
            return parseDelimited(input, ",");
        }
    }

    static class TsvParser implements DataParser {
        @Override
        public List<Map<String, String>> parse(String input) {
            return parseDelimited(input, "\t");
        }
    }

    private static List<Map<String, String>> parseDelimited(String input, String sep) {
        String[] lines = input.split("\n");
        if (lines.length < 2) return Collections.emptyList();
        String[] headers = lines[0].split(sep);
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String[] vals = lines[i].split(sep, -1);
            Map<String, String> row = new LinkedHashMap<>();
            for (int j = 0; j < headers.length; j++) {
                row.put(headers[j].trim(), j < vals.length ? vals[j].trim() : "");
            }
            result.add(row);
        }
        return result;
    }

    static class ParserFactory {
        public static DataParser create(String format) {
            return switch (format.toLowerCase()) {
                case "csv"  -> new CsvParser();
                case "tsv"  -> new TsvParser();
                default -> throw new IllegalArgumentException("Nieznany format: " + format);
            };
        }
    }

    // ═══════════════════════════════════════════════════════
    // ZADANIE 3 — Builder: HtmlFormBuilder
    // ═══════════════════════════════════════════════════════

    static class HtmlFormBuilder {
        private String action = "#";
        private String method = "GET";
        private final List<String> fields = new ArrayList<>();

        public HtmlFormBuilder action(String action) {
            this.action = action; return this;
        }

        public HtmlFormBuilder method(String method) {
            this.method = method; return this;
        }

        public HtmlFormBuilder addTextField(String name, String label, boolean required) {
            fields.add(String.format(
                "  <div><label>%s</label><input type=\"text\" name=\"%s\"%s></div>",
                label, name, required ? " required" : ""));
            return this;
        }

        public HtmlFormBuilder addPasswordField(String name, String label, boolean required) {
            fields.add(String.format(
                "  <div><label>%s</label><input type=\"password\" name=\"%s\"%s></div>",
                label, name, required ? " required" : ""));
            return this;
        }

        public HtmlFormBuilder addCheckbox(String name, String label, boolean checked) {
            fields.add(String.format(
                "  <div><input type=\"checkbox\" name=\"%s\"%s> %s</div>",
                name, checked ? " checked" : "", label));
            return this;
        }

        public HtmlFormBuilder addSubmitButton(String text) {
            fields.add("  <button type=\"submit\">" + text + "</button>");
            return this;
        }

        public String build() {
            StringBuilder sb = new StringBuilder();
            sb.append("<form action=\"").append(action).append("\" method=\"").append(method).append("\">\n");
            fields.forEach(f -> sb.append(f).append("\n"));
            sb.append("</form>");
            return sb.toString();
        }
    }

    // ═══════════════════════════════════════════════════════
    // ZADANIE 4 — Decorator: Walidator pól
    // ═══════════════════════════════════════════════════════

    record ValidationResult(boolean valid, List<String> errors) {
        static ValidationResult ok() { return new ValidationResult(true, List.of()); }
        static ValidationResult fail(String msg) { return new ValidationResult(false, List.of(msg)); }

        ValidationResult merge(ValidationResult other) {
            List<String> all = new ArrayList<>(this.errors);
            all.addAll(other.errors);
            return new ValidationResult(this.valid && other.valid, all);
        }
    }

    interface Validator<T> {
        ValidationResult validate(T value);
    }

    static class BaseValidator<T> implements Validator<T> {
        @Override public ValidationResult validate(T value) { return ValidationResult.ok(); }
    }

    static class NotEmptyValidator<T extends CharSequence> implements Validator<T> {
        private final Validator<T> inner;
        NotEmptyValidator(Validator<T> inner) { this.inner = inner; }

        @Override public ValidationResult validate(T value) {
            if (value == null || value.length() == 0)
                return ValidationResult.ok().merge(ValidationResult.fail("Pole nie może być puste"));
            return inner.validate(value);
        }
    }

    static class MaxLengthValidator<T extends CharSequence> implements Validator<T> {
        private final Validator<T> inner;
        private final int max;
        MaxLengthValidator(Validator<T> inner, int max) { this.inner = inner; this.max = max; }

        @Override public ValidationResult validate(T value) {
            ValidationResult r = inner.validate(value);
            if (value != null && value.length() > max)
                return r.merge(ValidationResult.fail("Zbyt długie (max " + max + ")"));
            return r;
        }
    }

    static class RegexValidator<T extends CharSequence> implements Validator<T> {
        private final Validator<T> inner;
        private final String pattern;
        private final String message;
        RegexValidator(Validator<T> inner, String pattern, String message) {
            this.inner = inner; this.pattern = pattern; this.message = message;
        }

        @Override public ValidationResult validate(T value) {
            ValidationResult r = inner.validate(value);
            if (value != null && !value.toString().matches(pattern))
                return r.merge(ValidationResult.fail(message));
            return r;
        }
    }

    // ═══════════════════════════════════════════════════════
    // ZADANIE 5 — Observer: EventBus
    // ═══════════════════════════════════════════════════════

    interface EventHandler<E> {
        void handle(E event);
    }

    static class EventBus {
        private final Map<Class<?>, List<EventHandler<Object>>> handlers = new HashMap<>();

        @SuppressWarnings("unchecked")
        public <E> void subscribe(Class<E> eventType, EventHandler<E> handler) {
            handlers.computeIfAbsent(eventType, k -> new ArrayList<>())
                    .add((EventHandler<Object>) handler);
        }

        @SuppressWarnings("unchecked")
        public <E> void publish(E event) {
            List<EventHandler<Object>> list = handlers.get(event.getClass());
            if (list != null) {
                for (EventHandler<Object> h : list) h.handle(event);
            }
        }
    }

    record OrderPlacedEvent(String orderId, String email, double total) {}
    record OrderShippedEvent(String orderId, String trackingNumber) {}

    // ═══════════════════════════════════════════════════════
    // ZADANIE 6 — Strategy: Kalkulator rabatów
    // ═══════════════════════════════════════════════════════

    interface DiscountStrategy {
        double apply(double unitPrice, int quantity);
        String describe();
    }

    static class NoDiscount implements DiscountStrategy {
        @Override public double apply(double price, int qty) { return price * qty; }
        @Override public String describe() { return "Brak rabatu"; }
    }

    static class PercentDiscount implements DiscountStrategy {
        private final double percent;
        PercentDiscount(double percent) { this.percent = percent; }
        @Override public double apply(double price, int qty) { return price * qty * (1 - percent / 100); }
        @Override public String describe() { return percent + "% rabatu"; }
    }

    static class BulkDiscount implements DiscountStrategy {
        private final int minQty;
        private final double percent;
        BulkDiscount(int minQty, double percent) { this.minQty = minQty; this.percent = percent; }
        @Override public double apply(double price, int qty) {
            double factor = qty >= minQty ? (1 - percent / 100) : 1.0;
            return price * qty * factor;
        }
        @Override public String describe() { return "Bulk: " + percent + "% przy >= " + minQty + " szt."; }
    }

    static class PricingEngine {
        private DiscountStrategy strategy;

        PricingEngine(DiscountStrategy strategy) { this.strategy = strategy; }
        public void setStrategy(DiscountStrategy s) { this.strategy = s; }

        public double calculatePrice(double unitPrice, int qty) {
            double total = strategy.apply(unitPrice, qty);
            System.out.printf("  Strategia: %-30s  %.2f × %d → %.2f PLN%n",
                    strategy.describe(), unitPrice, qty, total);
            return total;
        }
    }

    // ═══════════════════════════════════════════════════════
    // MAIN — demonstracja wszystkich zadań
    // ═══════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("=== Rozwiązania zadań — Wzorce projektowe ===\n");

        // Zadanie 1
        System.out.println("--- Zadanie 1: Singleton AppRegistry ---");
        AppRegistry reg = AppRegistry.getInstance();
        reg.print();
        reg.set("db.host", "prod-server.example.com");
        System.out.println("  Ten sam obiekt: " + (AppRegistry.getInstance() == reg));
        System.out.println();

        // Zadanie 2
        System.out.println("--- Zadanie 2: Factory Method — Parser ---");
        DataParser csv = ParserFactory.create("csv");
        List<Map<String,String>> rows = csv.parse("name,age,city\nAlice,30,Warsaw\nBob,25,Krakow");
        rows.forEach(r -> System.out.println("  " + r));
        System.out.println();

        // Zadanie 3
        System.out.println("--- Zadanie 3: Builder — HtmlFormBuilder ---");
        String html = new HtmlFormBuilder()
            .action("/login").method("POST")
            .addTextField("username", "Nazwa użytkownika", true)
            .addPasswordField("password", "Hasło", true)
            .addCheckbox("remember", "Zapamiętaj mnie", false)
            .addSubmitButton("Zaloguj się")
            .build();
        System.out.println(html);
        System.out.println();

        // Zadanie 4
        System.out.println("--- Zadanie 4: Decorator — Walidator e-mail ---");
        Validator<String> emailValidator =
            new NotEmptyValidator<>(
                new MaxLengthValidator<>(
                    new RegexValidator<>(
                        new BaseValidator<>(),
                        "^[^@]+@[^@]+\\.[^@]+$",
                        "Nieprawidłowy format e-mail"),
                    100));

        String[] emails = {"alice@example.com", "", "not-an-email", "a".repeat(101) + "@x.pl"};
        for (String e : emails) {
            ValidationResult r = emailValidator.validate(e);
            System.out.printf("  %-30s → %s%n",
                "\"" + (e.length() > 20 ? e.substring(0, 17) + "..." : e) + "\"",
                r.valid() ? "OK" : "BŁĘDY: " + r.errors());
        }
        System.out.println();

        // Zadanie 5
        System.out.println("--- Zadanie 5: Observer — EventBus ---");
        EventBus bus = new EventBus();
        bus.subscribe(OrderPlacedEvent.class, e ->
            System.out.println("  📧 Email do: " + e.email() + " (zamówienie " + e.orderId() + ")"));
        bus.subscribe(OrderPlacedEvent.class, e ->
            System.out.println("  📦 Magazyn: zaktualizuj stany dla " + e.orderId()));
        bus.subscribe(OrderShippedEvent.class, e ->
            System.out.println("  📱 SMS: zamówienie " + e.orderId() + " wysłane, nr " + e.trackingNumber()));

        bus.publish(new OrderPlacedEvent("ORD-001", "alice@example.com", 299.99));
        bus.publish(new OrderShippedEvent("ORD-001", "PL123456789"));
        System.out.println();

        // Zadanie 6
        System.out.println("--- Zadanie 6: Strategy — Kalkulator rabatów ---");
        PricingEngine engine = new PricingEngine(new NoDiscount());
        engine.calculatePrice(200.0, 3);

        engine.setStrategy(new PercentDiscount(10));
        engine.calculatePrice(200.0, 3);

        engine.setStrategy(new BulkDiscount(5, 15));
        engine.calculatePrice(200.0, 3);   // poniżej progu
        engine.calculatePrice(200.0, 10);  // powyżej progu
    }
}

