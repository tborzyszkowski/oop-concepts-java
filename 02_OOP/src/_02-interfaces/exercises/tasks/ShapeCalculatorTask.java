package exercises.tasks;

/**
 * ZADANIE 4 (⭐⭐⭐) — Sealed interface + record + switch (Java 21)
 *
 * Zaimplementuj kalkulator pól i obwodów figur geometrycznych.
 *
 * Wymagania:
 *   - sealed interface Shape permits Circle, Rectangle, Triangle
 *   - każdy kształt jako record
 *   - metody area(Shape) i perimeter(Shape) używają switch bez default
 *   - metoda describe(Shape) z guarded patterns (when)
 *
 * Oczekiwany output:
 *   Kolo r=5.0       | pole=78.54  | obwod=31.42
 *   Prostokat 4x6    | pole=24.00  | obwod=20.00
 *   Kwadrat 3x3      | pole=9.00   | obwod=12.00  [to kwadrat!]
 *   Trojkat 3,4,5    | pole=6.00   | obwod=12.00
 */
public class ShapeCalculatorTask {

    // TODO: sealed interface Shape permits Circle, Rectangle, Triangle
    // TODO: record Circle(double radius) implements Shape
    // TODO: record Rectangle(double width, double height) implements Shape
    // TODO: record Triangle(double a, double b, double c) implements Shape
    //        pole = wzór Herona: s=(a+b+c)/2, area=sqrt(s*(s-a)*(s-b)*(s-c))

    // TODO: static double area(Shape s) { return switch(s) { ... } }
    // TODO: static double perimeter(Shape s) { return switch(s) { ... } }
    // TODO: static String describe(Shape s) — guarded patterns:
    //        case Rectangle r when r.width() == r.height() -> "Kwadrat ..."

    public static void main(String[] args) {
        System.out.println("TODO: zaimplementuj zadanie");
    }
}

