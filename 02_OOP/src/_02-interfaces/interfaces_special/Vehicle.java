package interfaces_special;

/**
 * Interfejs z: stałymi, default method, static method, private method (Java 9+).
 * Stałe w interfejsie są implicitnie public static final.
 */
public interface Vehicle {
    // --- Stałe (public static final — niejawnie) ---
    int    MAX_SPEED_KMH = 300;
    String DEFAULT_FUEL  = "Benzyna";

    // --- Metody abstrakcyjne (kontrakt) ---
    String getBrand();
    int    getCurrentSpeed();

    // --- default method (Java 8+) ---
    default void startEngine() {
        System.out.println(getBrand() + ": Silnik uruchomiony (paliwo: " + DEFAULT_FUEL + ")");
        logEvent("START");
    }

    default void stopEngine() {
        System.out.println(getBrand() + ": Silnik zatrzymany");
        logEvent("STOP");
    }

    default String statusReport() {
        return getBrand() + " | prędkość: " + getCurrentSpeed() + " km/h"
             + " | max: " + MAX_SPEED_KMH + " km/h";
    }

    // --- static method (Java 8+) — fabryka/helper, NIE jest dziedziczona! ---
    static Vehicle create(String brand) {
        return new Vehicle() {
            @Override public String getBrand()        { return brand; }
            @Override public int    getCurrentSpeed() { return 0; }
        };
    }

    // --- private method (Java 9+) — wspólna logika dla default methods ---
    private void logEvent(String event) {
        System.out.println("  [LOG] " + event + " @ " + System.currentTimeMillis());
    }
}

