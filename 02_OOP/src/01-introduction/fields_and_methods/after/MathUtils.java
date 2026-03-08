package introduction.fields_and_methods.after;

/**
 * Klasa z wyłącznie statycznymi metodami użytkowymi (utility class).
 * Wzorzec: Math, Arrays, Collections w bibliotece standardowej Java.
 *
 * Charakterystyka:
 *  - Konstruktor prywatny — nie można tworzyć instancji
 *  - Wszystkie metody i pola statyczne
 *  - Używamy przez: MathUtils.square(5)
 */
public class MathUtils {

    // Prywatny konstruktor — blokuje tworzenie obiektów
    private MathUtils() {
        throw new UnsupportedOperationException("Klasa użytkowa — brak instancji");
    }

    // ===== STAŁE STATYCZNE =====
    public static final double PI = Math.PI;
    public static final double E  = Math.E;

    // ===== METODY STATYCZNE =====

    public static int square(int n) {
        return n * n;
    }

    public static double square(double n) {
        return n * n;
    }

    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n musi być >= 0");
        if (n <= 1) return 1;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static double circleArea(double radius) {
        return PI * radius * radius;
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}

