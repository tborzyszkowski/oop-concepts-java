package _06_wyjatki._06_throws.code;

import java.io.*;
import java.nio.file.*;
import java.sql.SQLException;

/**
 * ThrowsDeclarationDemo — deklaracja throws i checked vs unchecked exceptions.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_06_throws/code/ThrowsDeclarationDemo.java
 *   java  -cp out _06_wyjatki._06_throws.code.ThrowsDeclarationDemo
 */
public class ThrowsDeclarationDemo {

    // -------------------------------------------------------
    // Część 1: Dlaczego throws jest wymagane dla checked exceptions
    // -------------------------------------------------------

    // Checked — MUSI być zadeklarowane lub obsłużone
    static String readFirstLine(String path) throws IOException {
        return Files.readString(Path.of(path)).lines().findFirst().orElse("");
    }

    static void part1_CheckedRequired() {
        System.out.println("=== Część 1: checked — throws wymagane ===");
        try {
            String line = readFirstLine("nieistniejacy.txt");
            System.out.println("Linia: " + line);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Część 2: Propagacja checked przez kilka warstw
    // -------------------------------------------------------

    // Warstwa niskopoziomowa symuluje DB
    static String dbQuery(String sql) throws SQLException {
        if (sql.contains("DROP")) {
            throw new SQLException("Niedozwolona operacja DDL: " + sql, "42501", 4001);
        }
        return "result_of: " + sql;
    }

    // Warstwa repozytorium propaguje wyjątek
    static String findUser(int id) throws SQLException {
        return dbQuery("SELECT * FROM users WHERE id = " + id);
    }

    // Warstwa serwisu zamienia checked w unchecked (często stosowane)
    static String getUserName(int id) {
        try {
            return findUser(id);
        } catch (SQLException e) {
            throw new RuntimeException("Błąd bazy danych dla id=" + id, e);
        }
    }

    static void part2_LayeredPropagation() {
        System.out.println("\n=== Część 2: propagacja przez warstwy ===");

        // Bezpieczne zapytanie
        try {
            System.out.println("findUser(1): " + findUser(1));
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        // Niebezpieczne — wywoła SQLException → RuntimeException
        try {
            String name = getUserName(-999);
            System.out.println("userName: " + name);
        } catch (RuntimeException e) {
            System.out.println("RuntimeException: " + e.getMessage());
            System.out.println("  Cause: " + e.getCause().getClass().getSimpleName());
        }
    }

    // -------------------------------------------------------
    // Część 3: throws z wieloma typami
    // -------------------------------------------------------
    static void riskyOperation(int mode) throws IOException, SQLException, InterruptedException {
        switch (mode) {
            case 0 -> throw new IOException("I/O failure");
            case 1 -> throw new SQLException("DB failure");
            case 2 -> throw new InterruptedException("Thread interrupted");
            default -> System.out.println("  OK, mode=" + mode);
        }
    }

    static void part3_MultipleThrows() {
        System.out.println("\n=== Część 3: throws z wieloma typami ===");
        for (int mode = 0; mode <= 3; mode++) {
            try {
                riskyOperation(mode);
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("InterruptedException: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    // -------------------------------------------------------
    // Część 4: Kiedy MUSIMY użyć throws — interfejsy i nadklasy
    // -------------------------------------------------------
    interface DataLoader {
        String load(String source) throws IOException;  // kontrakt interfejsu
    }

    static class FileLoader implements DataLoader {
        @Override
        public String load(String source) throws IOException {
            // Implementacja może zawęzić (nie rzucać) ale nie rozszerzyć
            if (!Files.exists(Path.of(source))) {
                throw new IOException("Plik nie istnieje: " + source);
            }
            return Files.readString(Path.of(source));
        }
    }

    static class InMemoryLoader implements DataLoader {
        private final String data;
        InMemoryLoader(String data) { this.data = data; }

        @Override
        public String load(String source) {
            // Implementacja może POMINĄĆ throws jeżeli nie rzuca
            return data;
        }
    }

    static void part4_InterfaceThrows() {
        System.out.println("\n=== Część 4: throws w interfejsie ===");
        DataLoader[] loaders = {
            new FileLoader(),
            new InMemoryLoader("dane z pamięci")
        };
        String[] sources = {"nieistniejacy.dat", "ignored"};

        for (int i = 0; i < loaders.length; i++) {
            try {
                String result = loaders[i].load(sources[i]);
                System.out.println(loaders[i].getClass().getSimpleName() + ": " + result);
            } catch (IOException e) {
                System.out.println(loaders[i].getClass().getSimpleName()
                        + " IOException: " + e.getMessage());
            }
        }
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) {
        part1_CheckedRequired();
        part2_LayeredPropagation();
        part3_MultipleThrows();
        part4_InterfaceThrows();
    }
}

