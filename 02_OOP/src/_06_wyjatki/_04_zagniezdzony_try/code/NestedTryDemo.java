package _06_wyjatki._04_zagniezdzony_try.code;

/**
 * NestedTryDemo — zagnieżdżone bloki try-catch.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_04_zagniezdzony_try/code/NestedTryDemo.java
 *   java  -cp out _06_wyjatki._04_zagniezdzony_try.code.NestedTryDemo
 */
public class NestedTryDemo {

    // -------------------------------------------------------
    // Część 1: Wewnętrzny catch obsługuje swój wyjątek
    // -------------------------------------------------------
    static void part1_InnerHandled(int a) {
        System.out.println("--- part1 a=" + a + " ---");
        try {
            System.out.println("Zewnętrzny try, a=" + a);
            int b = 42 / a;                 // może rzucić ArithmeticException

            try {
                int[] arr = {1, 2, 3};
                System.out.println("arr[b]=" + arr[b]);   // może rzucić AIOOBE
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("  Wewnętrzny catch AIOOBE: " + e.getMessage());
                // Obsłużone — zewnętrzny try NIE widzi tego wyjątku
            }

            System.out.println("  Po wewnętrznym try-catch, b=" + b);

        } catch (ArithmeticException e) {
            System.out.println("Zewnętrzny catch AE: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Część 2: Wewnętrzny catch re-rzuca — trafia do zewnętrznego
    // -------------------------------------------------------
    static void part2_InnerRethrows(int a) {
        System.out.println("--- part2 a=" + a + " ---");
        try {
            try {
                if (a == 0) throw new ArithmeticException("inner zero");
                System.out.println("  inner OK, a=" + a);
            } catch (ArithmeticException e) {
                System.out.println("  inner catch: " + e.getMessage() + " → rethrow");
                throw e;   // propagacja do zewnętrznego
            }
        } catch (ArithmeticException e) {
            System.out.println("Zewnętrzny catch: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Część 3: Refaktoracja — wydzielenie metody zamiast zagnieżdżania
    // -------------------------------------------------------
    static int safeArrayGet(int[] arr, int index) {
        try {
            return arr[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;   // wartość sentinel
        }
    }

    static void part3_Refactored(int divisor) {
        System.out.println("--- part3 divisor=" + divisor + " ---");
        try {
            int idx = 100 / divisor;
            int[] data = {10, 20, 30};
            int val = safeArrayGet(data, idx);   // brak zagnieżdżenia!
            System.out.println("  val=" + val + (val == -1 ? " (poza zakresem)" : ""));
        } catch (ArithmeticException e) {
            System.out.println("  Dzielenie przez zero: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Część 4: Trzy poziomy zagnieżdżenia — śledzenie przepływu
    // -------------------------------------------------------
    static void deepNested() {
        System.out.println("--- part4: trzy poziomy ---");
        try {                                       // L1
            System.out.println("L1: start");
            try {                                   // L2
                System.out.println("L2: start");
                try {                               // L3
                    System.out.println("L3: rzucam NullPointerException");
                    throw new NullPointerException("NPE w L3");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("L3 catch AIOOBE — nigdy tu nie dotrzemy");
                }
                // NPE nie obsłużona w L3 → propaguje do L2
            } catch (NullPointerException e) {
                System.out.println("L2 catch NPE: " + e.getMessage());
                throw new IllegalStateException("Wrap NPE → ISE", e);
            }
            // ISE nie obsłużona w L2 → propaguje do L1
        } catch (IllegalStateException e) {
            System.out.println("L1 catch ISE: " + e.getMessage());
            System.out.println("  Przyczyna: " + e.getCause().getClass().getSimpleName());
        }
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("=== Zagnieżdżone try-catch ===\n");

        // Część 1
        part1_InnerHandled(3);   // b=14, arr[14] → AIOOBE w środku
        part1_InnerHandled(0);   // ArithmeticException w zewnętrznym

        System.out.println();

        // Część 2
        part2_InnerRethrows(1);
        part2_InnerRethrows(0);

        System.out.println();

        // Część 3
        part3_Refactored(10);   // idx=10, poza zakresem
        part3_Refactored(50);   // idx=2, arr[2]=30
        part3_Refactored(0);    // dzielenie przez zero

        System.out.println();

        // Część 4
        deepNested();
    }
}

