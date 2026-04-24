package introduction.value_objects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

// ================================================================
// CZĘŚĆ 1: STARY STYL — DTO z boilerplatem (Java < 14)
// ================================================================

/**
 * DTO w starym stylu — wymaga ręcznego pisania wszystkich metod.
 * Około 60 linii kodu dla 3 pól.
 */
class UserDtoOld {
    private final long id;
    private final String email;
    private final String name;

    public UserDtoOld(long id, String email, String name) {
        this.id    = id;
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.name  = Objects.requireNonNull(name,  "name must not be null");
    }

    public long   getId()    { return id; }
    public String getEmail() { return email; }
    public String getName()  { return name; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDtoOld u)) return false;
        return id == u.id && email.equals(u.email) && name.equals(u.name);
    }

    @Override public int hashCode() { return Objects.hash(id, email, name); }

    @Override public String toString() {
        return "UserDtoOld[id=" + id + ", email=" + email + ", name=" + name + "]";
    }
}

// ================================================================
// CZĘŚĆ 2: RECORD (Java 14+) — kilka linii zamiast 60
// ================================================================

/**
 * Ten sam DTO jako record.
 * Kompilator generuje: konstruktor kanoniczny, gettery (id(), email(), name()),
 * equals, hashCode, toString — automatycznie!
 */
record UserDto(long id, String email, String name) {
    // Kompaktowy konstruktor — walidacja bez powtarzania przypisań
    UserDto {
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(name,  "name must not be null");
        if (id <= 0) throw new IllegalArgumentException("id must be positive");
        email = email.trim().toLowerCase(); // można transformować przed przypisaniem
    }
}

// ================================================================
// CZĘŚĆ 3: VALUE OBJECT — niemutowalny obiekt z logiką dziedziny
// ================================================================

/**
 * Money jako Value Object (DDD).
 *
 * Właściwości Value Object:
 * - Niemutowalny (final fields, brak setterów)
 * - Tożsamość przez wartość (equals po amount+currency)
 * - Operacje zawsze zwracają nowy obiekt
 */
record Money(BigDecimal amount, String currency) {

    Money {
        Objects.requireNonNull(amount,   "amount must not be null");
        Objects.requireNonNull(currency, "currency must not be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("amount cannot be negative");
        currency = currency.toUpperCase();
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    // Fabryczna metoda — czytelniejsze tworzenie
    public static Money of(String amount, String currency) {
        return new Money(new BigDecimal(amount), currency);
    }

    // Operacje zwracają NOWY obiekt — niezmienialność zachowana
    public Money add(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException("Cannot add different currencies");
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money multiply(BigDecimal factor) {
        return new Money(this.amount.multiply(factor), this.currency);
    }

    public boolean isPositive() { return amount.compareTo(BigDecimal.ZERO) > 0; }

    @Override public String toString() {
        return amount.toPlainString() + " " + currency;
    }
}

// ================================================================
// CZĘŚĆ 4: NIEMUTOWALNA KLASA RĘCZNIE (klasyczny styl, Java < 14)
// ================================================================

/**
 * Klasyczna niemutowalna klasa:
 * - wszystkie pola final
 * - brak setterów
 * - ochrona mutowalnych referencji (defensive copy)
 */
final class ImmutablePoint {
    private final double x;
    private final double y;

    public ImmutablePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public ImmutablePoint translate(double dx, double dy) {
        return new ImmutablePoint(x + dx, y + dy);  // nowy obiekt!
    }

    public double distanceTo(ImmutablePoint other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof ImmutablePoint p)) return false;
        return Double.compare(x, p.x) == 0 && Double.compare(y, p.y) == 0;
    }

    @Override public int hashCode() { return Objects.hash(x, y); }

    @Override public String toString() { return "(%s, %s)".formatted(x, y); }
}

// ================================================================
// CZĘŚĆ 5: STAŁE i READ-ONLY
// ================================================================

class Constants {
    // static final = stała klasy (konwencja: UPPER_SNAKE_CASE)
    public static final double PI       = Math.PI;
    public static final int    MAX_SIZE = 100;
    public static final String APP_NAME = "OOP Demo";

    // final w metodzie = read-only lokalna zmienna
    static void finalLocalDemo() {
        final int limit = 42;
        // limit = 99; // błąd kompilacji!
        System.out.println("Limit (read-only lokalnie): " + limit);
    }

    // final parametr = nie wolno go ponownie przypisać
    static void process(final String input) {
        // input = "inne"; // błąd kompilacji!
        System.out.println("Przetwarzam: " + input);
    }
}

// ================================================================
// Typy pomocnicze dla demonstracji pattern matching (Java 21)
// Sealed interface i records muszą być na poziomie pliku (nie lokalne)
// ================================================================

sealed interface Shape permits Circle, Rect {}
record Circle(double radius) implements Shape {}
record Rect(double w, double h) implements Shape {}

// ================================================================
// DEMO
// ================================================================

public class RecordsAndValuesDemo {

    static void part1_OldDto() {
        System.out.println("=== 1. Stary DTO (boilerplate) ===");
        UserDtoOld u1 = new UserDtoOld(1L, "anna@example.com", "Anna");
        UserDtoOld u2 = new UserDtoOld(1L, "anna@example.com", "Anna");
        System.out.println(u1);
        System.out.println("u1.equals(u2): " + u1.equals(u2));        // działa bo ręcznie napisane
        System.out.println("u1 == u2:      " + (u1 == u2));           // false — różne obiekty
    }

    static void part2_Record() {
        System.out.println("\n=== 2. record (Java 14+) ===");

        UserDto r1 = new UserDto(1L, "  Anna@Example.COM  ", "Anna");
        UserDto r2 = new UserDto(1L, "anna@example.com", "Anna");

        System.out.println(r1);
        System.out.println("id():    " + r1.id());       // getter bez get-prefix
        System.out.println("email(): " + r1.email());    // email po transformacji w konstruktorze

        System.out.println("r1.equals(r2): " + r1.equals(r2)); // true — generowane equals
        System.out.println("r1 == r2:      " + (r1 == r2));    // false

        // Zapis a la Builder — "withery" (Java 21: record patterns)
        UserDto updated = new UserDto(r1.id(), r1.email(), "Ania");
        System.out.println("Zaktualizowany: " + updated);

        try {
            new UserDto(-1L, "test@test.com", "Test"); // walidacja w kompaktowym konstruktorze
        } catch (IllegalArgumentException e) {
            System.out.println("Walidacja: " + e.getMessage());
        }
    }

    static void part3_ValueObject() {
        System.out.println("\n=== 3. Value Object — Money ===");

        Money price   = Money.of("19.99", "PLN");
        Money tax     = Money.of("4.60", "PLN");
        Money total   = price.add(tax);
        Money doubled = total.multiply(new BigDecimal("2"));

        System.out.println("Cena:      " + price);
        System.out.println("Podatek:   " + tax);
        System.out.println("Razem:     " + total);
        System.out.println("×2:        " + doubled);

        // Tożsamość przez wartość
        Money a = Money.of("10.00", "EUR");
        Money b = Money.of("10.00", "EUR");
        System.out.println("a.equals(b): " + a.equals(b));  // true (record equals)
        System.out.println("a == b:      " + (a == b));      // false

        // Odporność na błędne mieszanie walut
        try {
            price.add(a);
        } catch (IllegalArgumentException e) {
            System.out.println("Różne waluty: " + e.getMessage());
        }
    }

    static void part4_ImmutableClassic() {
        System.out.println("\n=== 4. Klasyczna niemutowalna klasa — ImmutablePoint ===");

        ImmutablePoint p1 = new ImmutablePoint(0, 0);
        ImmutablePoint p2 = p1.translate(3, 4);    // nowy obiekt, p1 niezmieniony

        System.out.println("p1 = " + p1);          // (0.0, 0.0)
        System.out.println("p2 = " + p2);          // (3.0, 4.0)
        System.out.println("dist(p1,p2) = " + p1.distanceTo(p2));  // 5.0

        // Można bezpiecznie współdzielić w wątkach — brak warunków wyścigu
        System.out.println("p1 po translate: nadal " + p1 + " (niezmieniony)");
    }

    static void part5_Constants() {
        System.out.println("\n=== 5. Stałe i read-only ===");
        System.out.println("PI       = " + Constants.PI);
        System.out.println("MAX_SIZE = " + Constants.MAX_SIZE);
        System.out.println("APP_NAME = " + Constants.APP_NAME);
        Constants.finalLocalDemo();
        Constants.process("hello");
    }

    static void part6_RecordPatternMatching() {
        System.out.println("\n=== 6. Pattern matching z record (Java 21) ===");

        Shape[] shapes = { new Circle(5), new Rect(3, 4) };

        for (Shape s : shapes) {
            // Dekonstrukcja rekordu w switch (Java 21)
            String desc = switch (s) {
                case Circle(double r)        -> "Koło r=%.2f, pole=%.2f".formatted(r, Math.PI * r * r);
                case Rect(double w, double h)-> "Prostokąt %gx%g, pole=%.2f".formatted(w, h, w * h);
            };
            System.out.println(desc);
        }
    }

    public static void main(String[] args) {
        part1_OldDto();
        part2_Record();
        part3_ValueObject();
        part4_ImmutableClassic();
        part5_Constants();
        part6_RecordPatternMatching();
    }
}



