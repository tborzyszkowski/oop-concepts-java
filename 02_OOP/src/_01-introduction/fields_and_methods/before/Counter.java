package fields_and_methods.before;

/**
 * PRZED refaktoryzacją — anty-wzorzec: wszystkie pola publiczne.
 *
 * Problem: zewnętrzny kod może ustawić dowolną (nieprawidłową) wartość.
 * Brak kontroli nad stanem obiektu.
 */
public class Counter {

    // PROBLEM: publiczne pola — można ustawić count = -999
    public int count = 0;
    public String name;
    public int maxValue = 100;

    public void increment() {
        count++;  // brak sprawdzenia czy count < maxValue!
    }

    public void decrement() {
        count--;  // brak sprawdzenia czy count > 0!
    }

    public void reset() {
        count = 0;
    }
}

