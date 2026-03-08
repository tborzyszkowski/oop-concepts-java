package interfaces_implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstruje wielokrotne implements i polimorfizm przez różne interfejsy.
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_implementation/*.java
 *   java -cp out interfaces_implementation.MultiInterfaceDemo
 */
public class MultiInterfaceDemo {
    public static void main(String[] args) {

        Duck donald = new Duck("Donald");
        Airplane boeing = new Airplane("SP-LRA");

        // Ten sam obiekt duck dostępny przez oba "widoki"
        Flyable   asFlyable   = donald;   // widok jako Flyable
        Swimmable asSwimmable = donald;   // widok jako Swimmable

        System.out.println("=== Polimorfizm przez Flyable ===");
        List<Flyable> flyers = new ArrayList<>();
        flyers.add(donald);
        flyers.add(boeing);
        for (Flyable f : flyers) {
            f.fly();
            System.out.println("  Status: " + f.status());
        }

        System.out.println("\n=== Polimorfizm przez Swimmable ===");
        List<Swimmable> swimmers = new ArrayList<>();
        swimmers.add(donald);
        // boeing nie jest Swimmable — kompilator nie pozwoli dodać!
        for (Swimmable s : swimmers) {
            s.swim();
        }

        System.out.println("\n=== Duck jako oba interfejsy ===");
        System.out.println("flyStatus:  " + donald.flyStatus());
        System.out.println("swimStatus: " + donald.swimStatus());
        System.out.println("status:     " + donald.status());

        System.out.println("\n=== instanceof ===");
        System.out.println("donald instanceof Flyable:   " + (donald instanceof Flyable));
        System.out.println("donald instanceof Swimmable: " + (donald instanceof Swimmable));
        System.out.println("boeing instanceof Swimmable: " + (boeing instanceof Swimmable));
    }
}

