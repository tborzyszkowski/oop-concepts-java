package introduction.control_flow.examples;

/**
 * Pattern Matching w switch — nowość w Java 21 (finalne).
 *
 * Java 21 wprowadza:
 *  - Pattern matching w switch (JEP 441)
 *  - Guarded patterns (when)
 *  - Sealed classes z switch (kompletne sprawdzenie przez kompilator)
 */
public class SwitchPatternDemo {

    // Sealed interface — kompilator sprawdza czy switch jest kompletny
    sealed interface Shape permits Circle, Rectangle, Triangle {}
    record Circle(double radius) implements Shape {}
    record Rectangle(double width, double height) implements Shape {}
    record Triangle(double base, double height) implements Shape {}

    /**
     * Pattern matching w switch (Java 21) — obliczanie pola figury
     */
    static double area(Shape shape) {
        return switch (shape) {
            case Circle c       -> Math.PI * c.radius() * c.radius();
            case Rectangle r    -> r.width() * r.height();
            case Triangle t     -> 0.5 * t.base() * t.height();
            // Nie potrzeba default! Sealed interface — kompilator wie, że to wszystko.
        };
    }

    /**
     * Guarded patterns — dodatkowy warunek po 'when'
     */
    static String classify(Object obj) {
        return switch (obj) {
            case Integer i when i < 0  -> "ujemna liczba całkowita: " + i;
            case Integer i when i == 0 -> "zero";
            case Integer i             -> "dodatnia liczba całkowita: " + i;
            case Double d              -> "liczba rzeczywista: " + d;
            case String s when s.isEmpty() -> "pusty String";
            case String s              -> "String: \"" + s + "\"";
            case null                  -> "null";
            default                    -> "inny typ: " + obj.getClass().getSimpleName();
        };
    }

    /**
     * Ewolucja switch — porównanie stylów
     */
    static void compareStyles(int value) {
        // === STYL JAVA 8 ===
        String result8;
        switch (value) {
            case 1:  result8 = "jeden"; break;
            case 2:  result8 = "dwa";   break;
            default: result8 = "inne";
        }

        // === STYL JAVA 14+ (switch expression) ===
        String result14 = switch (value) {
            case 1  -> "jeden";
            case 2  -> "dwa";
            default -> "inne";
        };

        System.out.println("Java 8 styl:  " + result8);
        System.out.println("Java 14 styl: " + result14);
    }

    public static void main(String[] args) {

        System.out.println("=== Pattern Matching w switch (Java 21) ===");
        System.out.println();

        System.out.println("--- 1. Pattern matching z sealed interface ---");
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Triangle(3.0, 8.0)
        };
        for (Shape s : shapes) {
            System.out.println(s + " -> pole = " + String.format("%.2f", area(s)));
        }

        System.out.println();
        System.out.println("--- 2. Guarded patterns (when) ---");
        Object[] objects = {-5, 0, 42, 3.14, "", "Hello", null, 'A'};
        for (Object o : objects) {
            System.out.println("  " + classify(o));
        }

        System.out.println();
        System.out.println("--- 3. Ewolucja switch (style) ---");
        compareStyles(1);
        compareStyles(2);
        compareStyles(99);
    }
}

