package fields_and_methods.after;

/**
 * PO refaktoryzacji — enkapsulacja z polami prywatnymi.
 *
 * Demonstracja:
 *  - Pola prywatne (private) — ukryty stan
 *  - Metody publiczne (public) — kontrolowany dostęp
 *  - Pole statyczne (static) — wspólne dla wszystkich instancji
 *  - Metoda statyczna (static) — bez dostępu do this
 *  - Gettery i settery z walidacją
 */
public class Counter {

    // ===== POLA STATYCZNE (static fields) =====
    // Jedna kopia dla CAŁEJ klasy (wszystkich instancji razem)
    private static int instanceCount = 0;  // ile Counter-ów istnieje
    public static final int DEFAULT_MAX = 100; // stała klasy

    // ===== POLA INSTANCYJNE (instance fields) =====
    // Każdy obiekt ma WŁASNĄ kopię tych pól
    private String name;
    private int count;
    private int minValue;
    private int maxValue;

    // ===== KONSTRUKTOR =====
    public Counter(String name, int minValue, int maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException(
                "minValue (" + minValue + ") musi być <= maxValue (" + maxValue + ")"
            );
        }
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.count = minValue;  // startujemy od min
        instanceCount++;        // pole statyczne — wspólne
        System.out.println("Utworzono licznik: '" + name
                + "' [" + minValue + ".." + maxValue + "]"
                + " | Łącznie liczników: " + instanceCount);
    }

    // ===== METODY PUBLICZNE =====

    public boolean increment() {
        if (count >= maxValue) {
            System.out.println(name + ": osiągnięto maksimum (" + maxValue + ")");
            return false;
        }
        count++;
        return true;
    }

    public boolean decrement() {
        if (count <= minValue) {
            System.out.println(name + ": osiągnięto minimum (" + minValue + ")");
            return false;
        }
        count--;
        return true;
    }

    public void reset() {
        count = minValue;
        System.out.println(name + ": reset do " + minValue);
    }

    // ===== METODA STATYCZNA =====
    // Nie ma dostępu do pól instancyjnych (brak this)!
    // Można wywołać bez obiektu: Counter.getTotalInstances()
    public static int getTotalInstances() {
        return instanceCount;
        // return count; // BŁĄD: count jest polem instancyjnym!
    }

    public static String getDefaultMaxDescription() {
        return "Domyślne maksimum: " + DEFAULT_MAX;
    }

    // ===== GETTERY (kontrolowany dostęp do odczytu) =====
    public int getCount() { return count; }
    public String getName() { return name; }
    public int getMinValue() { return minValue; }
    public int getMaxValue() { return maxValue; }

    // ===== SETTER z walidacją =====
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nazwa nie może być pusta");
        }
        this.name = name;
    }

    // ===== METODA PRYWATNA (pomocnicza) =====
    private String statusBar() {
        int range = maxValue - minValue;
        int progress = count - minValue;
        int bars = range > 0 ? (progress * 10) / range : 0;
        return "[" + "=".repeat(bars) + " ".repeat(10 - bars) + "]";
    }

    @Override
    public String toString() {
        return "Counter{'" + name + "': "
                + count + " " + statusBar()
                + " [" + minValue + ".." + maxValue + "]}";
    }
}

