package _06_wyjatki._02_hierarchia_klas.code;

/**
 * ExceptionHierarchyDemo — hierarchia klas Throwable / Error / Exception.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_02_hierarchia_klas/code/ExceptionHierarchyDemo.java
 *   java  -cp out _06_wyjatki._02_hierarchia_klas.code.ExceptionHierarchyDemo
 */
public class ExceptionHierarchyDemo {

    // -------------------------------------------------------
    // Część 1: RuntimeException — nieprzechwycone (unchecked)
    // -------------------------------------------------------
    static void part1_Unchecked() {
        System.out.println("=== Część 1: wyjątki unchecked (RuntimeException) ===");

        // NullPointerException
        try {
            String s = null;
            System.out.println(s.length());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e.getClass().getSimpleName());
        }

        // ArrayIndexOutOfBoundsException
        try {
            int[] arr = new int[3];
            int x = arr[10];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());
        }

        // ClassCastException
        try {
            Object obj = "text";
            Integer i = (Integer) obj;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage());
        }

        // NumberFormatException (podklasa IllegalArgumentException)
        try {
            int n = Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        }

        // StackOverflowError — symulacja płytkiej rekursji
        try {
            // Wywołujemy ją z ograniczeniem głębokości — nie zawiesimy JVM
            stackOverflowSimulation(0);
        } catch (StackOverflowError e) {
            System.out.println("StackOverflowError przechwycony (rzadko robione w produkcji!)");
        }
    }

    static void stackOverflowSimulation(int depth) {
        stackOverflowSimulation(depth + 1);   // nieskończona rekursja
    }

    // -------------------------------------------------------
    // Część 2: Error — błędy JVM (nie obsługiwać standardowo)
    // -------------------------------------------------------
    static void part2_Errors() {
        System.out.println("\n=== Część 2: Error ===");
        System.out.println("Błędy klasy Error (OutOfMemoryError, StackOverflowError, AssertionError)");
        System.out.println("reprezentują nieodwracalne problemy środowiska JVM.");
        System.out.println("Zazwyczaj NIE powinniśmy ich przechwytywać w kodzie aplikacji.");

        // Przykład AssertionError (włącz asercje flagą -ea)
        try {
            assert false : "To jest asercja testowa";
        } catch (AssertionError e) {
            System.out.println("AssertionError (widoczny tylko z flagą -ea): " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Część 3: Exception checked — wymagają deklaracji lub obsługi
    // -------------------------------------------------------
    static void part3_Checked() throws java.io.IOException {
        System.out.println("\n=== Część 3: checked exceptions (IOException) ===");
        // Symulacja — normalnie byłby to odczyt pliku / sieci
        throw new java.io.IOException("Symulowany błąd I/O");
    }

    // -------------------------------------------------------
    // Część 4: Sprawdzenie hierarchii w runtime
    // -------------------------------------------------------
    static void part4_InstanceofHierarchy() {
        System.out.println("\n=== Część 4: hierarchia instanceof ===");
        Exception e = new NumberFormatException("bad number");

        System.out.println("instanceof NumberFormatException : " + (e instanceof NumberFormatException));
        System.out.println("instanceof IllegalArgumentException: " + (e instanceof IllegalArgumentException));
        System.out.println("instanceof RuntimeException        : " + (e instanceof RuntimeException));
        System.out.println("instanceof Exception               : " + (e instanceof Exception));
        System.out.println("instanceof Throwable               : " + (e instanceof Throwable));
    }

    // -------------------------------------------------------
    // Część 5: multi-catch (Java 7+)
    // -------------------------------------------------------
    static void part5_MultiCatch(int choice) {
        try {
            switch (choice) {
                case 0 -> throw new NumberFormatException("zły format");
                case 1 -> throw new ArrayIndexOutOfBoundsException("poza zakresem");
                default -> System.out.println("Brak wyjątku.");
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Java 7+: jeden blok catch dla wielu typów
            System.out.println("multi-catch: " + e.getClass().getSimpleName() + " — " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) {
        part1_Unchecked();
        part2_Errors();

        try {
            part3_Checked();
        } catch (java.io.IOException e) {
            System.out.println("IOException obsłużony: " + e.getMessage());
        }

        part4_InstanceofHierarchy();

        System.out.println("\n=== Część 5: multi-catch ===");
        for (int i = 0; i <= 2; i++) {
            System.out.print("choice=" + i + " → ");
            part5_MultiCatch(i);
        }
    }
}

