package _02_interfaces.interfaces_special;

/**
 * Sealed interface (Java 17+) + record (Java 16+) + switch pattern matching (Java 21).
 * Kompilator wymusza kompletność switch — nie trzeba default!
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_special/*.java
 *   java -cp out interfaces_special.SealedDemo
 */
public class SealedDemo {

    /** sealed — tylko wymienione klasy mogą implementować Shape */
    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle   (double radius)               implements Shape {}
    record Rectangle(double width, double height) implements Shape {}
    record Triangle (double base,  double height) implements Shape {}

    /** Switch bez default — kompilator sprawdza kompletność dzięki sealed! */
    static double area(Shape shape) {
        return switch (shape) {
            case Circle    c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            case Triangle  t -> 0.5 * t.base() * t.height();
        };
    }

    static String describe(Shape shape) {
        return switch (shape) {
            case Circle    c when c.radius() > 100 -> "Gigantyczne kolo r=" + c.radius();
            case Circle    c                       -> String.format("Kolo r=%.1f", c.radius());
            case Rectangle r when r.width() == r.height() -> "Kwadrat " + r.width() + "x" + r.height();
            case Rectangle r -> String.format("Prostokat %.1fx%.1f", r.width(), r.height());
            case Triangle  t -> String.format("Trojkat b=%.1f h=%.1f", t.base(), t.height());
        };
    }

    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5),
            new Circle(150),
            new Rectangle(4, 6),
            new Rectangle(3, 3),
            new Triangle(8, 5)
        };

        System.out.println("=== Sealed interface + record + pattern matching ===");
        System.out.printf("%-35s  %10s%n", "Ksztalt", "Pole");
        System.out.println("-".repeat(50));
        for (Shape s : shapes) {
            System.out.printf("%-35s  %10.2f%n", describe(s), area(s));
        }
    }
}

