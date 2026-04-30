package _06_wyjatki._08_wlasne_wyjatki.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// =============================================================
// Własne wyjątki — przykład 1: prosty wyjątek domenowy
// =============================================================

/** Wyjątek sygnalizujący wykroczenie poza dozwolony zakres. */
class RangeException extends Exception {
    private final double value;
    private final double min;
    private final double max;

    public RangeException(double value, double min, double max) {
        super(String.format("Wartość %.2f wykracza poza zakres [%.2f, %.2f]", value, min, max));
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public double getValue() { return value; }
    public double getMin()   { return min; }
    public double getMax()   { return max; }

    @Override
    public String toString() {
        return "RangeException{value=" + value + ", min=" + min + ", max=" + max + "}";
    }
}

// =============================================================
// Własne wyjątki — przykład 2: hierarchia wyjątków dla domeny
// =============================================================

/** Bazowy wyjątek aplikacji — umożliwia catch dla wszystkich błędów domeny. */
class AppException extends RuntimeException {
    private final String errorCode;

    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = Objects.requireNonNull(errorCode);
    }

    public AppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() { return errorCode; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + errorCode + "]: " + getMessage();
    }
}

/** Błąd walidacji wejściowych danych. */
class ValidationException extends AppException {
    private final List<String> errors;

    public ValidationException(List<String> errors) {
        super("VALIDATION_ERROR", "Dane wejściowe są nieprawidłowe: " + errors);
        this.errors = List.copyOf(errors);
    }

    public List<String> getErrors() { return errors; }
}

/** Błąd dostępu do zasobu (np. plik, baza danych). */
class ResourceException extends AppException {
    private final String resourceId;

    public ResourceException(String resourceId, String message, Throwable cause) {
        super("RESOURCE_ERROR", message, cause);
        this.resourceId = resourceId;
    }

    public String getResourceId() { return resourceId; }
}

// =============================================================
// Przykład 3: Wyjątek z kodem błędu (wzorzec stosowany w API)
// =============================================================

/** Błąd HTTP — numery statusu jako enum. */
class HttpException extends RuntimeException {
    public enum Status { BAD_REQUEST, UNAUTHORIZED, NOT_FOUND, INTERNAL_ERROR }

    private final Status status;

    public HttpException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public Status getStatus() { return status; }
    public int getStatusCode() {
        return switch (status) {
            case BAD_REQUEST     -> 400;
            case UNAUTHORIZED    -> 401;
            case NOT_FOUND       -> 404;
            case INTERNAL_ERROR  -> 500;
        };
    }
}

// =============================================================
// Demo main
// =============================================================

/**
 * CustomExceptionsDemo — ilustruje tworzenie i użycie własnych wyjątków.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_08_wlasne_wyjatki/code/CustomExceptionsDemo.java
 *   java  -cp out _06_wyjatki._08_wlasne_wyjatki.code.CustomExceptionsDemo
 */
public class CustomExceptionsDemo {

    // -------------------------------------------------------
    // Część 1: Prosty wyjątek domenowy z polami
    // -------------------------------------------------------
    static double clampedValue(double value, double min, double max) throws RangeException {
        if (value < min || value > max) {
            throw new RangeException(value, min, max);
        }
        return value;
    }

    static void part1_SimpleCustomException() {
        System.out.println("=== Część 1: prosty wyjątek domenowy ===");
        double[] values = {5.0, -3.0, 15.0, 8.5};
        for (double v : values) {
            try {
                double result = clampedValue(v, 0.0, 10.0);
                System.out.printf("  OK: %.1f%n", result);
            } catch (RangeException e) {
                System.out.printf("  RangeException: wartość=%.1f, zakres=[%.1f, %.1f]%n",
                        e.getValue(), e.getMin(), e.getMax());
            }
        }
    }

    // -------------------------------------------------------
    // Część 2: Hierarchia wyjątków domenowych
    // -------------------------------------------------------
    record UserDto(String name, int age) {}

    static UserDto validateUser(String name, int age) {
        List<String> errors = new ArrayList<>();
        if (name == null || name.isBlank())   errors.add("Imię jest wymagane");
        if (name != null && name.length() > 50) errors.add("Imię zbyt długie (max 50)");
        if (age < 0 || age > 150)              errors.add("Wiek w zakresie [0, 150]: " + age);
        if (!errors.isEmpty()) throw new ValidationException(errors);
        return new UserDto(name, age);
    }

    static void part2_ExceptionHierarchy() {
        System.out.println("\n=== Część 2: hierarchia wyjątków domenowych ===");
        record TestCase(String name, int age) {}
        TestCase[] cases = {
            new TestCase("Jan Kowalski", 30),
            new TestCase("", -1),
            new TestCase(null, 200)
        };
        for (TestCase tc : cases) {
            try {
                UserDto user = validateUser(tc.name(), tc.age());
                System.out.println("  OK: " + user);
            } catch (ValidationException e) {
                System.out.println("  ValidationException [" + e.getErrorCode() + "]:");
                e.getErrors().forEach(err -> System.out.println("    - " + err));
            } catch (AppException e) {
                System.out.println("  AppException: " + e);
            }
        }
    }

    // -------------------------------------------------------
    // Część 3: Wyjątek HTTP-like z kodem statusu
    // -------------------------------------------------------
    static String getResource(String path) {
        if (path == null)       throw new HttpException(HttpException.Status.BAD_REQUEST,  "Null path");
        if (path.equals("/secret")) throw new HttpException(HttpException.Status.UNAUTHORIZED, "Brak dostępu");
        if (path.equals("/data")) return "{ \"data\": 42 }";
        throw new HttpException(HttpException.Status.NOT_FOUND, "Zasób nie znaleziony: " + path);
    }

    static void part3_HttpException() {
        System.out.println("\n=== Część 3: HttpException z kodem statusu ===");
        String[] paths = {"/data", "/secret", "/unknown", null};
        for (String p : paths) {
            try {
                System.out.println("  GET " + p + " → " + getResource(p));
            } catch (HttpException e) {
                System.out.printf("  HTTP %d (%s): %s%n",
                        e.getStatusCode(), e.getStatus(), e.getMessage());
            }
        }
    }

    // -------------------------------------------------------
    // Część 4: Chaining — zawijanie checked w domenowy unchecked
    // -------------------------------------------------------
    static String loadConfigFile(String filename) {
        try {
            // Symulujemy IOException bez prawdziwego pliku
            if (!filename.endsWith(".conf")) {
                throw new java.io.IOException("Nieobsługiwany format: " + filename);
            }
            return "host=localhost\nport=8080";
        } catch (java.io.IOException e) {
            throw new ResourceException(filename, "Nie można wczytać konfiguracji", e);
        }
    }

    static void part4_ExceptionWrapping() {
        System.out.println("\n=== Część 4: zawijanie wyjątków ===");
        String[] files = {"app.conf", "database.xml"};
        for (String f : files) {
            try {
                String config = loadConfigFile(f);
                System.out.println("  " + f + " wczytany:\n    " + config.replace("\n", "\n    "));
            } catch (ResourceException e) {
                System.out.println("  ResourceException [" + e.getErrorCode() + "] "
                        + e.getResourceId() + ": " + e.getMessage());
                System.out.println("    Przyczyna: " + e.getCause().getMessage());
            }
        }
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) {
        part1_SimpleCustomException();
        part2_ExceptionHierarchy();
        part3_HttpException();
        part4_ExceptionWrapping();
    }
}

