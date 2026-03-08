package fields_and_methods.before;

/**
 * Demonstracja problemów z publicznymi polami.
 * Pokazuje dlaczego enkapsulacja jest ważna.
 */
public class CounterBeforeDemo {

    public static void main(String[] args) {
        System.out.println("=== PRZED refaktoryzacją (anty-wzorzec) ===");
        System.out.println();

        Counter counter = new Counter();
        counter.name = "Licznik zamówień";
        counter.maxValue = 5;

        System.out.println("Normalny scenariusz:");
        counter.increment();
        counter.increment();
        counter.increment();
        System.out.println("count = " + counter.count); // 3 — OK

        System.out.println();
        System.out.println("Nieprawidłowe użycie (możliwe dzięki public fields):");

        // PROBLEM 1: Można ustawić wartość ujemną
        counter.count = -999;
        System.out.println("counter.count = -999 — możliwe! count = " + counter.count);

        // PROBLEM 2: Można przekroczyć maxValue
        counter.count = 999;
        System.out.println("counter.count = 999 — możliwe! count = " + counter.count);

        // PROBLEM 3: Można zmienić maxValue
        counter.maxValue = -50;
        System.out.println("counter.maxValue = -50 — możliwe! Brak walidacji.");

        // PROBLEM 4: increment() nie sprawdza maxValue
        counter.count = 4;
        counter.increment();
        counter.increment(); // przekracza maxValue = -50...
        System.out.println("Po 2x increment (maxValue=-50): count = " + counter.count);

        System.out.println();
        System.out.println("Wniosek: publiczne pola = brak kontroli nad stanem obiektu!");
    }
}

