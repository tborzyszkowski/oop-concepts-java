package exercises.solutions;

import java.util.*;

/**
 * Wzorcowe rozwiązania zadań 1–4.
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out exercises/solutions/Solutions.java
 *   java -cp out exercises.solutions.Solutions
 */
public class Solutions {

    // =========================================================
    // ZADANIE 1 — Payable
    // =========================================================
    interface Payable {
        void pay(double amount);
        String getPaymentInfo();
        default String receipt(double amount) {
            return String.format("Paragon: %s — %.2f PLN", getPaymentInfo(), amount);
        }
    }

    static class CreditCard implements Payable {
        private final String masked;
        CreditCard(String masked) { this.masked = masked; }
        @Override public void pay(double amount) {
            System.out.printf("  [KARTA] %s: %.2f PLN%n", masked, amount);
        }
        @Override public String getPaymentInfo() { return "Karta " + masked; }
    }

    static class PayPalPayment implements Payable {
        private final String email;
        PayPalPayment(String email) { this.email = email; }
        @Override public void pay(double amount) {
            System.out.printf("  [PAYPAL] %s: %.2f PLN%n", email, amount);
        }
        @Override public String getPaymentInfo() { return "PayPal " + email; }
    }

    static class BankTransfer implements Payable {
        private final String iban;
        BankTransfer(String iban) { this.iban = iban; }
        @Override public void pay(double amount) {
            System.out.printf("  [PRZELEW] %s: %.2f PLN%n",
                iban.substring(0, Math.min(14, iban.length())), amount);
        }
        @Override public String getPaymentInfo() { return "Przelew " + iban; }
    }

    static void task1() {
        System.out.println("=== Zadanie 1: Payable ===");
        record Payment(Payable method, double amount) {}
        List<Payment> payments = List.of(
            new Payment(new CreditCard("4111-xxxx-xxxx-1111"), 150.00),
            new Payment(new PayPalPayment("jan@example.com"),  299.99),
            new Payment(new BankTransfer("PL61109010140000071219812874"), 1200.00)
        );
        double total = 0;
        for (Payment p : payments) { p.method().pay(p.amount()); total += p.amount(); }
        System.out.printf("  Lacznie: %.2f PLN%n", total);
    }

    // =========================================================
    // ZADANIE 2 — Comparable/Comparator
    // =========================================================
    static class Student implements Comparable<Student> {
        final String firstName, lastName;
        final double gpa;
        Student(String f, String l, double g) { firstName = f; lastName = l; gpa = g; }

        @Override public int compareTo(Student o) {
            int c = Double.compare(o.gpa, gpa);   // malejąco po gpa
            return c != 0 ? c : lastName.compareTo(o.lastName);
        }
        @Override public String toString() {
            return String.format("%-12s %-10s %.1f", lastName, firstName, gpa);
        }
    }

    static void task2() {
        System.out.println("\n=== Zadanie 2: Comparable ===");
        List<Student> students = new ArrayList<>(List.of(
            new Student("Anna",    "Nowak",     4.8),
            new Student("Jan",     "Kowalski",  4.5),
            new Student("Maria",   "Wiśniewska",4.5),
            new Student("Tomasz",  "Zielinski", 3.9),
            new Student("Barbara", "Adamska",   4.2)
        ));

        Collections.sort(students);
        System.out.println("  Naturalne (srednia malejaco):");
        students.forEach(s -> System.out.println("    " + s));

        students.sort(Comparator.comparing((Student s) -> s.lastName)
                                .thenComparing(s -> s.firstName));
        System.out.println("  Alfabetycznie:");
        students.forEach(s -> System.out.println("    " + s));
    }

    // =========================================================
    // ZADANIE 3 — Strategy: TextTransformer
    // =========================================================
    @FunctionalInterface
    interface TextTransformer {
        String transform(String input);
        default TextTransformer andThen(TextTransformer next) {
            return input -> next.transform(this.transform(input));
        }
    }

    static void task3() {
        System.out.println("\n=== Zadanie 3: TextTransformer ===");
        TextTransformer upper   = String::toUpperCase;
        TextTransformer trim    = String::trim;
        TextTransformer reverse = s -> new StringBuilder(s).reverse().toString();

        System.out.println("  UpperCase: " + upper.transform("hello world"));
        System.out.println("  Trim:      " + trim.transform("  hello world  "));
        System.out.println("  Reverse:   " + reverse.transform("hello world"));

        TextTransformer pipeline = trim.andThen(upper).andThen(reverse);
        System.out.println("  Pipeline:  " + pipeline.transform("  hello world  "));
    }

    // =========================================================
    // ZADANIE 4 — Sealed interface + record + switch
    // =========================================================
    sealed interface Shape permits CircleShape, RectangleShape, TriangleShape {}
    record CircleShape   (double radius)               implements Shape {}
    record RectangleShape(double width, double height) implements Shape {}
    record TriangleShape (double a, double b, double c) implements Shape {}

    static double area(Shape s) {
        return switch (s) {
            case CircleShape    c -> Math.PI * c.radius() * c.radius();
            case RectangleShape r -> r.width() * r.height();
            case TriangleShape  t -> {
                double p = (t.a() + t.b() + t.c()) / 2;
                yield Math.sqrt(p * (p - t.a()) * (p - t.b()) * (p - t.c()));
            }
        };
    }

    static double perimeter(Shape s) {
        return switch (s) {
            case CircleShape    c -> 2 * Math.PI * c.radius();
            case RectangleShape r -> 2 * (r.width() + r.height());
            case TriangleShape  t -> t.a() + t.b() + t.c();
        };
    }

    static String describe(Shape s) {
        return switch (s) {
            case CircleShape    c -> String.format("Kolo r=%.1f", c.radius());
            case RectangleShape r when r.width() == r.height() ->
                String.format("Kwadrat %.0fx%.0f [to kwadrat!]", r.width(), r.height());
            case RectangleShape r -> String.format("Prostokat %.0fx%.0f", r.width(), r.height());
            case TriangleShape  t -> String.format("Trojkat %.0f,%.0f,%.0f", t.a(), t.b(), t.c());
        };
    }

    static void task4() {
        System.out.println("\n=== Zadanie 4: Sealed interface ===");
        List<Shape> shapes = List.of(
            new CircleShape(5), new RectangleShape(4, 6),
            new RectangleShape(3, 3), new TriangleShape(3, 4, 5)
        );
        System.out.printf("  %-30s  %8s  %8s%n", "Ksztalt", "Pole", "Obwod");
        for (Shape sh : shapes) {
            System.out.printf("  %-30s  %8.2f  %8.2f%n",
                describe(sh), area(sh), perimeter(sh));
        }
    }

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
    }
}

