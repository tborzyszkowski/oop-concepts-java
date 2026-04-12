package _01_introduction.exercises.tasks;

/**
 * ZADANIE 1: Klasa Rectangle z walidacją i metodami geometrycznymi
 * ================================================================
 * Poziom: Podstawowy — oparty na module "classes"
 *
 * POLECENIE:
 *   Zaimplementuj klasę Rectangle reprezentującą prostokąt.
 *   Stosuj enkapsulację (pola prywatne, gettery, settery z walidacją).
 *
 * WYMAGANIA:
 *   1. Pola prywatne: width (double), height (double)
 *   2. Konstruktor z walidacją (width > 0 oraz height > 0)
 *   3. Gettery dla width i height
 *   4. Settery z walidacją (IllegalArgumentException dla <= 0)
 *   5. Metody:
 *       - double area()      → pole powierzchni
 *       - double perimeter() → obwód
 *       - boolean isSquare() → czy prostokąt jest kwadratem (width == height)
 *       - Rectangle scale(double factor) → zwraca NOWY Rectangle przeskalowany
 *   6. Poprawna metoda toString()
 *   7. Pole statyczne: int instanceCount (ile prostokątów utworzono)
 *
 * TESTY (w RectangleTest.java):
 *   - Weryfikują wszystkie wymagania powyżej
 *   - Sprawdzają walidację (wyjątki)
 *   - Sprawdzają scale() — czy tworzy nowy obiekt
 *
 * WSKAZÓWKI:
 *   - Patrz: Counter.java (after/) jako wzorzec enkapsulacji i static
 *   - Patrz: BankAccount.java jako wzorzec walidacji
 */
public class RectangleTask {

    // TODO: Zaimplementuj klasę Rectangle poniżej

    public static class Rectangle {
        // TODO: pola prywatne
        // TODO: pole statyczne instanceCount

        // TODO: Konstruktor(double width, double height)

        // TODO: gettery

        // TODO: settery z walidacją

        // TODO: area(), perimeter(), isSquare()

        // TODO: scale(double factor) — zwraca nowy Rectangle

        // TODO: toString()
    }

    // Prosta weryfikacja ręczna — usuń gdy napiszesz testy JUnit
    public static void main(String[] args) {
//        Rectangle r1 = new Rectangle(4.0, 3.0);
//        System.out.println("Rectangle: " + r1);
//        System.out.println("Area: " + r1.area());           // 12.0
//        System.out.println("Perimeter: " + r1.perimeter()); // 14.0
//        System.out.println("isSquare: " + r1.isSquare());   // false
//
//        Rectangle r2 = new Rectangle(5.0, 5.0);
//        System.out.println("isSquare: " + r2.isSquare());   // true
//
//        Rectangle r3 = r1.scale(2.0);
//        System.out.println("Scaled: " + r3);                // 8.0 x 6.0
//
//        System.out.println("Instances: " + Rectangle.instanceCount); // 3
    }
}

