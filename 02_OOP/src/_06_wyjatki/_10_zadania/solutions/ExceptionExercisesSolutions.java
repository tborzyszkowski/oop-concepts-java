package _06_wyjatki._10_zadania.solutions;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ExceptionExercisesSolutions — rozwiązania referencyjne zadań z modułu 10.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_10_zadania/solutions/ExceptionExercisesSolutions.java
 *   java  -cp out _06_wyjatki._10_zadania.solutions.ExceptionExercisesSolutions
 */
public class ExceptionExercisesSolutions {

    // =====================================================
    // Zadanie 1: Bezpieczne parsowanie liczb
    // =====================================================
    public static OptionalInt safeParseInt(String s) {
        try {
            return OptionalInt.of(Integer.parseInt(s.trim()));
        } catch (NumberFormatException | NullPointerException e) {
            return OptionalInt.empty();
        }
    }

    static void task1() {
        System.out.println("=== Zadanie 1: safeParseInt ===");
        String[] inputs = {"42", " -7 ", "abc", null, "3.14", "2147483647"};
        for (String s : inputs) {
            OptionalInt result = safeParseInt(s);
            System.out.printf("  %-15s → %s%n",
                    "'" + s + "'",
                    result.isPresent() ? result.getAsInt() : "EMPTY");
        }
    }

    // =====================================================
    // Zadanie 2: Sprawdzenie stosu z wyjątkami
    // =====================================================
    static class BoundedStack<T> {
        private final int capacity;
        private final Deque<T> deque = new ArrayDeque<>();

        BoundedStack(int capacity) {
            if (capacity <= 0)
                throw new IllegalArgumentException("Pojemność musi być > 0, było: " + capacity);
            this.capacity = capacity;
        }

        public void push(T item) {
            Objects.requireNonNull(item, "Null nie jest dozwolony na stosie");
            if (deque.size() >= capacity)
                throw new IllegalStateException("Stos pełny (pojemność=" + capacity + ")");
            deque.push(item);
        }

        public T pop() {
            if (deque.isEmpty())
                throw new NoSuchElementException("Stos jest pusty");
            return deque.pop();
        }

        public T peek() {
            if (deque.isEmpty())
                throw new NoSuchElementException("Stos jest pusty");
            return deque.peek();
        }

        public int size() { return deque.size(); }
        public boolean isEmpty() { return deque.isEmpty(); }
    }

    static void task2() {
        System.out.println("\n=== Zadanie 2: BoundedStack ===");
        BoundedStack<Integer> stack = new BoundedStack<>(3);
        stack.push(10); stack.push(20); stack.push(30);
        System.out.println("  size: " + stack.size());

        // Próba push na pełny stos
        try { stack.push(40); }
        catch (IllegalStateException e) { System.out.println("  push pełny: " + e.getMessage()); }

        // Pop wszystkich
        while (!stack.isEmpty()) System.out.println("  pop: " + stack.pop());

        // Pop z pustego
        try { stack.pop(); }
        catch (NoSuchElementException e) { System.out.println("  pop pusty: " + e.getMessage()); }

        // Null
        try { stack.push(null); }
        catch (NullPointerException e) { System.out.println("  push null: " + e.getMessage()); }

        // Zła pojemność
        try { new BoundedStack<>(0); }
        catch (IllegalArgumentException e) { System.out.println("  capacity=0: " + e.getMessage()); }
    }

    // =====================================================
    // Zadanie 3: Własny wyjątek z kontekstem walidacji
    // =====================================================
    static class PasswordException extends RuntimeException {
        private final List<String> violations;

        PasswordException(List<String> violations) {
            super("Hasło nie spełnia wymagań: " + violations);
            this.violations = List.copyOf(violations);
        }
        public List<String> getViolations() { return violations; }
    }

    static void validatePassword(String password) {
        List<String> errors = new ArrayList<>();
        if (password == null || password.length() < 8) errors.add("Minimum 8 znaków");
        if (password != null && !password.matches(".*[A-Z].*")) errors.add("Wymagana duża litera");
        if (password != null && !password.matches(".*[0-9].*")) errors.add("Wymagana cyfra");
        if (password != null && !password.matches(".*[!@#$%].*")) errors.add("Wymagany znak specjalny (!@#$%)");
        if (!errors.isEmpty()) throw new PasswordException(errors);
    }

    static void task3() {
        System.out.println("\n=== Zadanie 3: PasswordException ===");
        String[] passwords = {"Secret1!", "abc", "password123", "Valid1!"};
        for (String p : passwords) {
            try {
                validatePassword(p);
                System.out.println("  '" + p + "' → OK");
            } catch (PasswordException e) {
                System.out.println("  '" + p + "' → BŁĘDY:");
                e.getViolations().forEach(v -> System.out.println("      - " + v));
            }
        }
    }

    // =====================================================
    // Zadanie 4: Finally i zwalnianie zasobów
    // =====================================================
    static class FakeConnection implements AutoCloseable {
        private final String url;
        private boolean open = false;

        FakeConnection(String url) throws IOException {
            if (url == null || url.isBlank())
                throw new IOException("Pusty URL");
            this.url = url;
            this.open = true;
            System.out.println("  Połączono: " + url);
        }

        String query(String sql) throws IOException {
            if (!open) throw new IOException("Połączenie zamknięte");
            if (sql.contains("ERROR")) throw new IOException("Błąd zapytania: " + sql);
            return "wynik(" + sql + ")";
        }

        @Override
        public void close() {
            open = false;
            System.out.println("  Rozłączono: " + url);
        }
    }

    static void task4() throws IOException {
        System.out.println("\n=== Zadanie 4: try-with-resources ===");
        String[] queries = {"SELECT 1", "SELECT ERROR", "SELECT 2"};
        for (String sql : queries) {
            System.out.println("  Zapytanie: " + sql);
            try (FakeConnection conn = new FakeConnection("jdbc:fake://localhost")) {
                System.out.println("    " + conn.query(sql));
            } catch (IOException e) {
                System.out.println("    IOException: " + e.getMessage());
            }
        }
    }

    // =====================================================
    // Zadanie 5: Propagacja przez warstwy
    // =====================================================
    static class DataProcessingException extends Exception {
        DataProcessingException(String msg, Throwable cause) { super(msg, cause); }
    }

    static int parseAndDouble(String s) throws DataProcessingException {
        try {
            return Integer.parseInt(s) * 2;
        } catch (NumberFormatException e) {
            throw new DataProcessingException("Nie można przetworzyć: '" + s + "'", e);
        }
    }

    static List<Integer> processAll(List<String> items) throws DataProcessingException {
        List<Integer> results = new ArrayList<>();
        for (String item : items) {
            results.add(parseAndDouble(item));
        }
        return results;
    }

    static void task5() {
        System.out.println("\n=== Zadanie 5: propagacja przez warstwy ===");
        var good = List.of("1", "2", "3");
        var bad  = List.of("1", "two", "3");

        for (var list : List.of(good, bad)) {
            try {
                System.out.println("  => " + processAll(list));
            } catch (DataProcessingException e) {
                System.out.println("  DataProcessingException: " + e.getMessage());
                System.out.println("  Przyczyna: " + e.getCause().getClass().getSimpleName());
            }
        }
    }

    // =====================================================
    // main
    // =====================================================
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        System.out.println("\nWszystkie zadania ukończone.");
    }
}

