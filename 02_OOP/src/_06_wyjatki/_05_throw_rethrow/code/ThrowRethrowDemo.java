package _06_wyjatki._05_throw_rethrow.code;

/**
 * ThrowRethrowDemo — instrukcja throw i propagacja wyjątków.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_05_throw_rethrow/code/ThrowRethrowDemo.java
 *   java  -cp out _06_wyjatki._05_throw_rethrow.code.ThrowRethrowDemo
 */
public class ThrowRethrowDemo {

    // -------------------------------------------------------
    // Część 1: Jawne rzucanie wyjątku (throw new …)
    // -------------------------------------------------------
    static double squareRoot(double x) {
        if (x < 0) {
            // Tworzenie obiektu wyjątku i rzucanie — dwa osobne kroki
            IllegalArgumentException ex =
                    new IllegalArgumentException("Pierwiastek z liczby ujemnej: " + x);
            throw ex;
        }
        return Math.sqrt(x);
    }

    static void part1_ExplicitThrow() {
        System.out.println("=== Część 1: jawne throw ===");
        double[] values = {4.0, -1.0, 9.0};
        for (double v : values) {
            try {
                System.out.printf("sqrt(%.1f) = %.4f%n", v, squareRoot(v));
            } catch (IllegalArgumentException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    // -------------------------------------------------------
    // Część 2: Re-rzucanie tego samego wyjątku
    // -------------------------------------------------------
    static void process(String s) {
        System.out.println("process(\"" + s + "\") — wchodzę");
        try {
            int v = Integer.parseInt(s);
            System.out.println("  Wynik = " + v);
        } catch (NumberFormatException e) {
            System.out.println("  process: złapałem NumberFormatException, re-rzucam");
            throw e;   // re-throw — stack trace NIEZMIENIONY (Java 7+)
        }
        System.out.println("process(\"" + s + "\") — wychodzę normalnie");
    }

    static void part2_Rethrow() {
        System.out.println("\n=== Część 2: re-throw ===");
        try {
            process("123");
            process("abc");
        } catch (NumberFormatException e) {
            System.out.println("main catch: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Część 3: Zawinięcie w inny wyjątek (exception chaining)
    // -------------------------------------------------------
    static int loadConfig(String key) {
        try {
            // Symulacja odczytu zewnętrznej konfiguracji
            String raw = fetchRaw(key);
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            // Zachowujemy przyczynę (cause)
            throw new IllegalStateException(
                    "Błędna konfiguracja klucza '" + key + "'", e);
        }
    }

    static String fetchRaw(String key) {
        if (key.equals("timeout")) return "not-a-number";
        if (key.equals("retries")) return "3";
        throw new IllegalArgumentException("Nieznany klucz: " + key);
    }

    static void part3_Chaining() {
        System.out.println("\n=== Część 3: exception chaining ===");
        String[] keys = {"retries", "timeout", "unknown"};
        for (String k : keys) {
            try {
                System.out.println(k + " = " + loadConfig(k));
            } catch (IllegalStateException e) {
                System.out.println("IllegalStateException: " + e.getMessage());
                System.out.println("  Przyczyna: " + e.getCause().getClass().getSimpleName()
                        + " — " + e.getCause().getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("IllegalArgumentException: " + e.getMessage());
            }
        }
    }

    // -------------------------------------------------------
    // Część 4: Niezależne miejsce powstania a miejsce zgłoszenia
    // -------------------------------------------------------
    static IllegalArgumentException buildException(String param) {
        // Obiekt wyjątku tworzony TUTAJ (w tej metodzie), ale rzucony dalej
        return new IllegalArgumentException("Nieprawidłowy parametr: " + param);
    }

    static void validate(String param) {
        if (param == null || param.isBlank()) {
            IllegalArgumentException ex = buildException(param);
            // ex.getStackTrace()[0] wskazuje na buildException — miejsce TWORZENIA
            // throw niżej wskazuje na validate — miejsca RZUCENIA
            throw ex;
        }
    }

    static void part4_CreationVsThrow() {
        System.out.println("\n=== Część 4: miejsce tworzenia vs rzucenia ===");
        try {
            validate("");
        } catch (IllegalArgumentException e) {
            System.out.println("Komunikat: " + e.getMessage());
            StackTraceElement[] st = e.getStackTrace();
            System.out.println("Pierwsze elementy stack trace:");
            for (int i = 0; i < Math.min(4, st.length); i++) {
                System.out.println("  [" + i + "] " + st[i]);
            }
        }
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) {
        part1_ExplicitThrow();
        part2_Rethrow();
        part3_Chaining();
        part4_CreationVsThrow();
    }
}

