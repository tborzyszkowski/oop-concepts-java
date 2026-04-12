package _02_interfaces.interfaces_advanced;

import java.util.Iterator;

/**
 * Demonstruje implementację Iterable<T> i użycie for-each.
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_advanced/*.java
 *   java -cp out interfaces_advanced.IterableDemo
 */
public class IterableDemo {
    public static void main(String[] args) {

        // 1. for-each — kompilator tłumaczy na wywołanie iterator()
        System.out.println("=== for-each (1..10) ===");
        for (int n : new NumberRange(1, 10)) {
            System.out.print(n + " ");
        }

        System.out.println("\n\n=== Co-drugi (2..20, step=2) ===");
        for (int n : new NumberRange(2, 20, 2)) {
            System.out.print(n + " ");
        }

        // 2. Jawna iteracja przez Iterator
        System.out.println("\n\n=== Jawny Iterator ===");
        Iterator<Integer> it = new NumberRange(1, 5).iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }

        // 3. Suma pierwszych 100 liczb naturalnych (wzór Gaussa: 5050)
        int sum = 0;
        for (int n : new NumberRange(1, 100)) sum += n;
        System.out.println("\n\nSuma 1..100 = " + sum);
    }
}

