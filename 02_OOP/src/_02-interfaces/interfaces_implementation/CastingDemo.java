package interfaces_implementation;

/**
 * Demonstruje bezpieczne i niebezpieczne rzutowanie na interfejsy.
 * Java 16+ pattern matching instanceof eliminuje potrzebę jawnego rzutowania.
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_implementation/*.java
 *   java -cp out interfaces_implementation.CastingDemo
 */
public class CastingDemo {
    public static void main(String[] args) {

        Object[] objects = {
            new Duck("Daisy"),
            new Airplane("SP-ABC"),
            "Zwykły String",
            42
        };

        System.out.println("=== Bezpieczne rzutowanie (pattern matching, Java 16+) ===");
        for (Object obj : objects) {
            // Stary styl (przed Java 16):
            // if (obj instanceof Flyable) { Flyable f = (Flyable) obj; f.fly(); }

            // Nowy styl — pattern matching instanceof:
            if (obj instanceof Flyable f) {
                System.out.print("[Flyable]  ");
                f.fly();
            }
            if (obj instanceof Swimmable s) {
                System.out.print("[Swimmable] ");
                s.swim();
            }
            if (obj instanceof String str) {
                System.out.println("[String]   wartość: " + str);
            }
            if (obj instanceof Integer i) {
                System.out.println("[Integer]  wartość: " + i);
            }
        }

        System.out.println("\n=== ClassCastException przy złym rzutowaniu ===");
        Object airplane = new Airplane("SP-XYZ");
        try {
            Swimmable s = (Swimmable) airplane;  // Airplane nie implementuje Swimmable!
            s.swim();
        } catch (ClassCastException e) {
            System.out.println("BŁĄD: " + e.getMessage());
            System.out.println("Zawsze sprawdzaj instanceof przed rzutowaniem!");
        }
    }
}

