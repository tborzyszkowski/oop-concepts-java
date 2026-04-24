package introduction.exercises.tasks;

/**
 * Zadanie 5: Value Object — Temperature i GeoPoint
 *
 * Zaimplementuj dwa typy jako record w Javie.
 *
 * ZADANIE 5a: Temperature
 * - record Temperature(double value, String scale)
 * - Walidacja: scale musi być "C", "F" lub "K"
 * - Walidacja: wartość w K nie może być ujemna
 * - toCelsius() : Temperature
 * - toFahrenheit() : Temperature
 *
 * ZADANIE 5b: GeoPoint
 * - record GeoPoint(double latitude, double longitude)
 * - Walidacja: latitude ∈ [-90, 90], longitude ∈ [-180, 180]
 * - distanceTo(GeoPoint other) : double (w km, wzór uproszczony)
 * - isNorthOf(GeoPoint other) : boolean
 */
public class ValueObjectTask {

    // TODO: zaimplementuj record Temperature(double value, String scale)
    // TODO: zaimplementuj record GeoPoint(double latitude, double longitude)

    public static void main(String[] args) {
        // Odkomentuj i uzupełnij po implementacji:

        // Temperature boiling = new Temperature(100.0, "C");
        // System.out.println("100°C to F: " + boiling.toFahrenheit().value());  // 212.0

        // Temperature absZero = new Temperature(0.0, "K");
        // System.out.println("0K to C: " + absZero.toCelsius().value());         // -273.15

        // GeoPoint warsaw = new GeoPoint(52.23, 21.01);
        // GeoPoint krakow = new GeoPoint(50.06, 19.94);
        // System.out.println("Warszawa na północ od Krakowa: " + warsaw.isNorthOf(krakow)); // true
        // System.out.println("Odległość: " + warsaw.distanceTo(krakow) + " km");
    }
}

