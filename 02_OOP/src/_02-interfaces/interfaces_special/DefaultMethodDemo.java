package interfaces_special;

/**
 * Demonstruje:
 *  - stałe w interfejsach (public static final — niejawnie)
 *  - default methods i ich nadpisywanie
 *  - static methods (nie dziedziczone!)
 *  - private methods (Java 9+)
 *  - dziedziczenie interfejsów (extends)
 *  - Diamond Problem z default methods
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_special/*.java
 *   java -cp out interfaces_special.DefaultMethodDemo
 */
public class DefaultMethodDemo {
    public static void main(String[] args) {

        // 1. Stałe — public static final
        System.out.println("=== Stałe w interfejsach ===");
        System.out.println("Vehicle.MAX_SPEED_KMH       = " + Vehicle.MAX_SPEED_KMH);
        System.out.println("Vehicle.DEFAULT_FUEL        = " + Vehicle.DEFAULT_FUEL);
        System.out.println("ElectricVehicle.DEFAULT_FUEL= " + ElectricVehicle.DEFAULT_FUEL); // ukryta!

        // 2. Default methods + dziedziczenie interfejsów
        System.out.println("\n=== Tesla (implements ElectricVehicle extends Vehicle) ===");
        Tesla model3 = new Tesla("Model 3");
        model3.startEngine();           // z ElectricVehicle (nadpisane)
        model3.accelerate(80);
        System.out.println(model3.statusReport());   // z Vehicle (odziedziczone)
        System.out.println(model3.batteryStatus());  // z ElectricVehicle
        model3.charge(150);
        model3.stopEngine();            // z Vehicle (odziedziczone przez EV)

        // 3. Static method w interfejsie — tylko przez nazwę interfejsu
        System.out.println("\n=== static method interfejsu ===");
        Vehicle generic = Vehicle.create("GenericBrand");
        System.out.println(generic.statusReport());
        // ElectricVehicle.create(...) — BŁĄD: static NIE jest dziedziczona!

        // 4. Diamond Problem
        System.out.println("\n=== Diamond Problem ===");
        System.out.println(new D().hello());
    }

    // Diamond Problem: dwa interfejsy mają default z tą samą nazwą
    interface A { default String hello() { return "Hello z A"; } }
    interface B extends A { @Override default String hello() { return "Hello z B"; } }
    interface C extends A { @Override default String hello() { return "Hello z C"; } }

    // Klasa D musi jawnie rozwiązać konflikt
    static class D implements B, C {
        @Override
        public String hello() {
            return B.super.hello() + " + " + C.super.hello();
        }
    }
}

