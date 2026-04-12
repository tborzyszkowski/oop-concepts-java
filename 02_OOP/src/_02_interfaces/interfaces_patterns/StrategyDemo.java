package _02_interfaces.interfaces_patterns;

import java.util.Arrays;

/** Strategy — interfejs strategii sortowania */
interface SortStrategy {
    void sort(int[] array);
    default String name() { return getClass().getSimpleName(); }
}

class BubbleSort implements SortStrategy {
    @Override public void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++)
            for (int j = 0; j < a.length - i - 1; j++)
                if (a[j] > a[j+1]) { int t = a[j]; a[j] = a[j+1]; a[j+1] = t; }
    }
}

class SelectionSort implements SortStrategy {
    @Override public void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) if (a[j] < a[min]) min = j;
            int t = a[min]; a[min] = a[i]; a[i] = t;
        }
    }
}

class JavaBuiltinSort implements SortStrategy {
    @Override public void sort(int[] a) { Arrays.sort(a); }
    @Override public String name()      { return "Arrays.sort (TimSort)"; }
}

/** Kontekst — zna algorytm przez interfejs, nie konkretną klasę */
class Sorter {
    private SortStrategy strategy;

    public Sorter(SortStrategy strategy) { this.strategy = strategy; }

    /** Podmiana strategii w runtime */
    public void setStrategy(SortStrategy strategy) { this.strategy = strategy; }

    public int[] sortCopy(int[] data) {
        int[] copy = Arrays.copyOf(data, data.length);
        strategy.sort(copy);
        return copy;
    }

    public String currentStrategy() { return strategy.name(); }
}

/**
 * WZORZEC STRATEGY
 * Interfejs definiuje "co" (sortuj), implementacje definiują "jak".
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_patterns/StrategyDemo.java
 *   java -cp out interfaces_patterns.StrategyDemo
 */
public class StrategyDemo {
    public static void main(String[] args) {
        int[] data = {64, 34, 25, 12, 22, 11, 90, 1, 55, 3};
        System.out.println("Dane:  " + Arrays.toString(data));

        Sorter sorter = new Sorter(new BubbleSort());

        System.out.println("\n--- " + sorter.currentStrategy() + " ---");
        System.out.println("Wynik: " + Arrays.toString(sorter.sortCopy(data)));

        sorter.setStrategy(new SelectionSort());
        System.out.println("\n--- " + sorter.currentStrategy() + " ---");
        System.out.println("Wynik: " + Arrays.toString(sorter.sortCopy(data)));

        sorter.setStrategy(new JavaBuiltinSort());
        System.out.println("\n--- " + sorter.currentStrategy() + " ---");
        System.out.println("Wynik: " + Arrays.toString(sorter.sortCopy(data)));

        // Lambda jako strategia — SortStrategy jest interfejsem funkcyjnym!
        sorter.setStrategy(a -> Arrays.sort(a));
        System.out.println("\n--- Lambda (Arrays.sort) ---");
        System.out.println("Wynik: " + Arrays.toString(sorter.sortCopy(data)));
    }
}

