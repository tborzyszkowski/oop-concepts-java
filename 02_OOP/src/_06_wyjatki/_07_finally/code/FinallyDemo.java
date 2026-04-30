package _06_wyjatki._07_finally.code;

import java.io.*;

/**
 * FinallyDemo — sekcja finally i jej interakcja z return, throw, System.exit().
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_07_finally/code/FinallyDemo.java
 *   java  -cp out _06_wyjatki._07_finally.code.FinallyDemo
 */
public class FinallyDemo {

    // -------------------------------------------------------
    // Część 1: finally zawsze się wykonuje
    // -------------------------------------------------------
    static void part1_AlwaysExecutes(boolean throwEx) {
        System.out.println("part1_AlwaysExecutes(throw=" + throwEx + ")");
        try {
            System.out.println("  try: start");
            if (throwEx) throw new RuntimeException("Test");
            System.out.println("  try: koniec (brak wyjątku)");
        } catch (RuntimeException e) {
            System.out.println("  catch: " + e.getMessage());
        } finally {
            System.out.println("  finally: ZAWSZE");
        }
    }

    // -------------------------------------------------------
    // Część 2: finally + return — zaskakujące zachowanie
    // -------------------------------------------------------
    static int methodWithReturn() {
        try {
            System.out.println("  try: return 1");
            return 1;
        } finally {
            System.out.println("  finally: działa PRZED powrotem z return");
            // Uwaga: return tutaj NADPISAŁBY wartość 1 !
            // return 99;
        }
    }

    static int methodWithReturnInFinally() {
        try {
            System.out.println("  try: return 1");
            return 1;
        } finally {
            System.out.println("  finally: return 99 — NADPISUJE!");
            return 99;   // Anty-wzorzec! Ukrywa wartość z try
        }
    }

    static void part2_ReturnInFinally() {
        System.out.println("\npart2_ReturnInFinally");
        System.out.println("  Wynik normalny: " + methodWithReturn());
        System.out.println("  Wynik z return w finally: " + methodWithReturnInFinally());
    }

    // -------------------------------------------------------
    // Część 3: finally pochłania wyjątek przez return (anty-wzorzec)
    // -------------------------------------------------------
    static String dangerousMethod() {
        try {
            throw new RuntimeException("wyjątek z try");
        } finally {
            // return w finally POCHŁANIA wyjątek z try — BARDZO NIEBEZPIECZNE
            return "finally-return ukrywa wyjątek!";
        }
    }

    static void part3_FinallySwallowsException() {
        System.out.println("\npart3_FinallySwallowsException");
        // Wyjątek jest cicho pochłonięty — nie zobaczymy go!
        String result = dangerousMethod();
        System.out.println("Wynik: " + result);
        System.out.println("Wyjątek z try zniknął bez śladu!");
    }

    // -------------------------------------------------------
    // Część 4: finally i zwalnianie zasobów — klasyczny wzorzec
    // -------------------------------------------------------
    static void part4_ResourceRelease() throws IOException {
        System.out.println("\npart4_ResourceRelease");
        // Klasyczny wzorzec sprzed Java 7 — finally gwarantuje zamknięcie
        StringReader reader = null;
        try {
            reader = new StringReader("Linia 1\nLinia 2\nLinia 3");
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("  " + line);
            }
        } finally {
            if (reader != null) {
                reader.close();
                System.out.println("  finally: czytnik zamknięty");
            }
        }
    }

    // -------------------------------------------------------
    // Część 5: try-with-resources (Java 7+) — lepsze rozwiązanie
    // -------------------------------------------------------
    static void part5_TryWithResources() throws IOException {
        System.out.println("\npart5_TryWithResources");
        // AutoCloseable — close() wywołane automatycznie
        try (StringReader reader = new StringReader("X\nY\nZ");
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("  " + line);
            }
        }   // close() wywołane tu, nawet przy wyjątku
        System.out.println("  Zasób zamknięty automatycznie.");
    }

    // -------------------------------------------------------
    // Część 6: Suppressed exceptions (try-with-resources)
    // -------------------------------------------------------
    static class FaultyResource implements AutoCloseable {
        private final String name;
        FaultyResource(String name) {
            this.name = name;
            System.out.println("  Otwieram: " + name);
        }

        void use() {
            throw new RuntimeException("Błąd użycia: " + name);
        }

        @Override
        public void close() {
            throw new RuntimeException("Błąd zamknięcia: " + name);
        }
    }

    static void part6_SuppressedExceptions() {
        System.out.println("\npart6_SuppressedExceptions");
        try (FaultyResource r = new FaultyResource("R1")) {
            r.use();   // rzuca RuntimeException
        } catch (RuntimeException e) {
            System.out.println("  Główny wyjątek: " + e.getMessage());
            Throwable[] suppressed = e.getSuppressed();
            System.out.println("  Suppressed: " + suppressed.length);
            for (Throwable s : suppressed) {
                System.out.println("    → " + s.getMessage());
            }
        }
    }

    // -------------------------------------------------------
    // Część 7: finally a switch expression — informacja
    // -------------------------------------------------------
    static void part7_FinallyNotes() {
        System.out.println("\npart7_FinallyNotes");
        System.out.println("  System.exit() omija finally (JVM kończy działanie).");
        System.out.println("  Nieskończona pętla w try — finally nigdy nie wykona się.");
        System.out.println("  Zalecenie: NIE umieszczaj return/throw w finally.");
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) throws IOException {
        part1_AlwaysExecutes(false);
        part1_AlwaysExecutes(true);
        part2_ReturnInFinally();
        part3_FinallySwallowsException();
        part4_ResourceRelease();
        part5_TryWithResources();
        part6_SuppressedExceptions();
        part7_FinallyNotes();
    }
}

