package _06_wyjatki._01_wprowadzenie.code;

/**
 * ExceptionIntroDemo — wprowadzenie do wyjątków w Javie.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_01_wprowadzenie/code/ExceptionIntroDemo.java
 *   java  -cp out _06_wyjatki._01_wprowadzenie.code.ExceptionIntroDemo
 */
public class ExceptionIntroDemo {

    // -------------------------------------------------------
    // Część 1: Błąd bez obsługi — program ulega awarii
    // -------------------------------------------------------
    static void part1_NoHandling() {
        System.out.println("=== Część 1: brak obsługi (komentarz) ===");
        // Gdybyśmy odkomentowali poniższy kod, JVM rzuciłby ArithmeticException
        // i wypisał stack trace:
        //
        //   int d = 0;
        //   int a = 42 / d;   // java.lang.ArithmeticException: / by zero
        //
        System.out.println("Brak awarii — kod chroniony komentarzem.");
    }

    // -------------------------------------------------------
    // Część 2: Obsługa try-catch — program kontynuuje działanie
    // -------------------------------------------------------
    static void part2_BasicTryCatch() {
        System.out.println("\n=== Część 2: podstawowy try-catch ===");
        try {
            int d = 0;
            int a = 42 / d;          // rzuca ArithmeticException
            System.out.println("Tego nie zobaczymy: " + a);
        } catch (ArithmeticException e) {
            System.out.println("Przechwycono: " + e.getMessage());
        }
        System.out.println("Program kontynuuje po bloku try-catch.");
    }

    // -------------------------------------------------------
    // Część 3: Stack trace i informacje diagnostyczne
    // -------------------------------------------------------
    static void methodC() {
        String s = null;
        s.length();   // NullPointerException
    }

    static void methodB() { methodC(); }
    static void methodA() { methodB(); }

    static void part3_StackTrace() {
        System.out.println("\n=== Część 3: stack trace ===");
        try {
            methodA();
        } catch (NullPointerException e) {
            System.out.println("Typ wyjątku : " + e.getClass().getName());
            System.out.println("Komunikat  : " + e.getMessage());
            System.out.println("--- Stack trace (skrócony) ---");
            // Drukujemy tylko 3 pierwsze elementy stosu wywołań
            StackTraceElement[] st = e.getStackTrace();
            for (int i = 0; i < Math.min(3, st.length); i++) {
                System.out.println("  at " + st[i]);
            }
        }
    }

    // -------------------------------------------------------
    // Część 4: Wyjątki a kody błędów — porównanie stylu C vs Java
    // -------------------------------------------------------
    /**
     * Styl C — zwracanie kodu błędu (anty-wzorzec w Javie).
     * Wady: każde wywołanie musi sprawdzać wynik; łatwo pominąć błąd.
     */
    static int parseLengthC(String s) {
        if (s == null || s.isBlank()) return -1;   // kod błędu
        try { return Integer.parseInt(s.trim()); }
        catch (NumberFormatException e) { return -2; }
    }

    /**
     * Styl Java — wyjątek niesie pełną informację i wymusza obsługę.
     */
    static int parseLengthJava(String s) {
        if (s == null || s.isBlank())
            throw new IllegalArgumentException("Pusty ciąg znaków");
        return Integer.parseInt(s.trim());   // ewentualnie NumberFormatException
    }

    static void part4_ErrorCodesVsExceptions() {
        System.out.println("\n=== Część 4: kody błędów vs wyjątki ===");

        // Styl C — łatwo zapomnieć o sprawdzeniu
        int result = parseLengthC("abc");
        if (result < 0) {
            System.out.println("Błąd (styl C): kod = " + result);
        }

        // Styl Java — wyjątek wymusza obsługę
        try {
            int val = parseLengthJava("abc");
            System.out.println("Długość: " + val);
        } catch (NumberFormatException e) {
            System.out.println("Błąd formatu (Java): " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd argumentu (Java): " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Część 5: Wyjątek przerywa wykonanie bloku
    // -------------------------------------------------------
    static void part5_FlowInterruption() {
        System.out.println("\n=== Część 5: przerwanie przepływu ===");
        int[] tab = {10, 20, 30};
        try {
            System.out.println("tab[0] = " + tab[0]);
            System.out.println("tab[5] = " + tab[5]);   // ArrayIndexOutOfBoundsException
            System.out.println("To nie zostanie wypisane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Poza zakresem: indeks " + e.getMessage());
        }
        System.out.println("Wykonanie po bloku try-catch.");
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) {
        part1_NoHandling();
        part2_BasicTryCatch();
        part3_StackTrace();
        part4_ErrorCodesVsExceptions();
        part5_FlowInterruption();

        System.out.println("\nWszystkie części ukończone pomyślnie.");
    }
}

