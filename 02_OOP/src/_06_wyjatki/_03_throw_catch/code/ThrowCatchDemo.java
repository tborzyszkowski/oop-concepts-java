package _06_wyjatki._03_throw_catch.code;

import java.util.List;

/**
 * ThrowCatchDemo — mechanizm throw i catch: wszystkie warianty składniowe.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_03_throw_catch/code/ThrowCatchDemo.java
 *   java  -cp out _06_wyjatki._03_throw_catch.code.ThrowCatchDemo
 */
public class ThrowCatchDemo {

    // -------------------------------------------------------
    // Część 1: Jeden typ wyjątku — catch dopasowanie
    // -------------------------------------------------------
    static void part1_SingleCatch() {
        System.out.println("=== Część 1: pojedynczy catch ===");
        String[] dane = {"42", "kot", null, "7"};
        for (String s : dane) {
            try {
                int v = Integer.parseInt(s);
                System.out.println("Sparsowano: " + v);
            } catch (NumberFormatException e) {
                System.out.println("Błąd formatu dla '" + s + "': " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Wartość null — nie można parsować.");
            }
        }
    }

    // -------------------------------------------------------
    // Część 2: Łańcuch catch — kolejność ma znaczenie
    // -------------------------------------------------------
    static void parse(String s) {
        // Wywoła jedną z trzech gałęzi catch
        if (s == null) throw new NullPointerException("s == null");
        if (s.isBlank()) throw new IllegalArgumentException("Pusty ciąg");
        int v = Integer.parseInt(s);   // może rzucić NumberFormatException
        System.out.println("OK: " + v);
    }

    static void part2_CatchChain() {
        System.out.println("\n=== Część 2: łańcuch catch ===");
        List<String> inputs = List.of("10", "", "abc");
        for (String s : inputs) {
            try {
                parse(s);
            } catch (NumberFormatException e) {
                // Bardziej szczegółowy → przed Exception
                System.out.println("Zły format: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                // IllegalArgumentException (nadklasa NumberFormatException!)
                System.out.println("Nieprawidłowy argument: " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Null: " + e.getMessage());
            }
        }
    }

    // -------------------------------------------------------
    // Część 3: multi-catch (Java 7+)
    // -------------------------------------------------------
    static void riskOperation(int mode) {
        switch (mode) {
            case 0 -> throw new ArithmeticException("dzielenie");
            case 1 -> throw new ArrayIndexOutOfBoundsException("indeks");
            case 2 -> throw new ClassCastException("rzutowanie");
        }
    }

    static void part3_MultiCatch() {
        System.out.println("\n=== Część 3: multi-catch ===");
        for (int i = 0; i < 4; i++) {
            try {
                riskOperation(i);
                System.out.println("mode=" + i + " OK");
            } catch (ArithmeticException | ArrayIndexOutOfBoundsException | ClassCastException e) {
                System.out.println("mode=" + i + " multi-catch: " + e.getClass().getSimpleName());
            }
        }
    }

    // -------------------------------------------------------
    // Część 4: catch (Exception e) — pułapka nadmiernie ogólna
    // -------------------------------------------------------
    static void part4_CatchAll() {
        System.out.println("\n=== Część 4: catch(Exception) — ogólny ===");
        try {
            String s = null;
            s.length();
        } catch (Exception e) {
            // Dobre do logowania; złe gdy ukrywa konkretny typ
            System.out.println("Ogólny catch: " + e.getClass().getSimpleName()
                    + " — " + e.getMessage());
            // Uwaga: poniżej NIE robimy: catch (Throwable e) {} bez powodu
        }
    }

    // -------------------------------------------------------
    // Część 5: Wyjątek a typ zwracany — brak "wartości domyślnej"
    // -------------------------------------------------------
    static int safeDivide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Dzielnik wynosi zero");
        }
        return a / b;
    }

    static void part5_ReturnVsException() {
        System.out.println("\n=== Część 5: wyjątek a return ===");
        int[] denominators = {4, 0, 2};
        for (int d : denominators) {
            try {
                int result = safeDivide(100, d);
                System.out.println("100 / " + d + " = " + result);
            } catch (ArithmeticException e) {
                System.out.println("100 / " + d + " → wyjątek: " + e.getMessage());
            }
        }
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) {
        part1_SingleCatch();
        part2_CatchChain();
        part3_MultiCatch();
        part4_CatchAll();
        part5_ReturnVsException();
    }
}

