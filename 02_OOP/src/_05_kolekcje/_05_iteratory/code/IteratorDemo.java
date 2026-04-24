package _05_kolekcje._05_iteratory.code;

import java.util.*;

/**
 * IteratorDemo — iteratory jawne i niejawne, wzorzec Iterator, ListIterator.
 */
public class IteratorDemo {

    // -------------------------------------------------------
    // 1. Iterator jawny
    // -------------------------------------------------------
    static void explicitIterator() {
        System.out.println("=== 1. Iterator jawny ===");

        List<String> names = new ArrayList<>(List.of("Alicja", "Bob", "Cezary", "Diana"));

        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String name = it.next();
            System.out.println("  " + name);
            if (name.startsWith("B")) {
                it.remove();  // bezpieczne usunięcie podczas iteracji
            }
        }
        System.out.println("Po usunięciu 'B*': " + names);
    }

    // -------------------------------------------------------
    // 2. Pętla for-each (niejawny iterator)
    // -------------------------------------------------------
    static void forEachLoop() {
        System.out.println("\n=== 2. Pętla for-each ===");

        Set<Integer> numbers = new LinkedHashSet<>(List.of(1, 2, 3, 4, 5));

        // Niejawny iterator — kompilator generuje kod z Iterator<>
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();

        // Equivalent Iterator jawny:
        Iterator<Integer> it = numbers.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println("(iterator jawny)");
    }

    // -------------------------------------------------------
    // 3. ListIterator — dwukierunkowy
    // -------------------------------------------------------
    static void listIteratorDemo() {
        System.out.println("\n=== 3. ListIterator (dwukierunkowy) ===");

        List<String> words = new ArrayList<>(List.of("raz", "dwa", "trzy", "cztery"));

        ListIterator<String> lit = words.listIterator(words.size()); // od końca
        System.out.print("Wstecz: ");
        while (lit.hasPrevious()) {
            System.out.print(lit.previous() + " ");
        }
        System.out.println();

        // Modyfikacja podczas iteracji
        ListIterator<String> lit2 = words.listIterator();
        while (lit2.hasNext()) {
            String w = lit2.next();
            lit2.set(w.toUpperCase());  // zastępuje bieżący element
        }
        System.out.println("Po set() toUpperCase: " + words);
    }

    // -------------------------------------------------------
    // 4. Własna klasa implementująca Iterable
    // -------------------------------------------------------
    static class Range implements Iterable<Integer> {
        private final int start;
        private final int end;   // exclusive

        Range(int start, int end) { this.start = start; this.end = end; }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                int current = start;

                @Override
                public boolean hasNext() { return current < end; }

                @Override
                public Integer next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    return current++;
                }
            };
        }
    }

    static void customIterableDemo() {
        System.out.println("\n=== 4. Własna klasa Iterable<Integer> (Range) ===");

        Range r = new Range(1, 6);
        System.out.print("Range(1, 6): ");
        for (int i : r) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Wielokrotna iteracja — każde wywołanie iterator() tworzy nowy iterator
        long sum = 0;
        for (int i : r) sum += i;
        System.out.println("Suma 1..5 = " + sum);
    }

    // -------------------------------------------------------
    // 5. ConcurrentModificationException
    // -------------------------------------------------------
    static void concurrentModDemo() {
        System.out.println("\n=== 5. ConcurrentModificationException ===");

        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        // BŁĄD — modyfikacja listy podczas for-each
        try {
            for (int n : list) {
                if (n % 2 == 0) list.remove(Integer.valueOf(n));  // ConcurrentModificationException!
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException złapany! (" + e.getClass().getSimpleName() + ")");
        }

        // POPRAW 1 — Iterator.remove()
        list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            if (it.next() % 2 == 0) it.remove();
        }
        System.out.println("Po Iterator.remove() (nieparzyste): " + list);

        // POPRAW 2 — removeIf (Java 8+)
        list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        list.removeIf(n -> n % 2 == 0);
        System.out.println("Po removeIf (nieparzyste):          " + list);
    }

    // -------------------------------------------------------
    // 6. forEach z lambdą (Iterable.forEach)
    // -------------------------------------------------------
    static void forEachLambda() {
        System.out.println("\n=== 6. forEach z lambdą ===");

        List<String> cities = List.of("Kraków", "Warszawa", "Gdańsk", "Wrocław");

        cities.forEach(city -> System.out.println("  Miasto: " + city));
        cities.forEach(System.out::println);  // referencja do metody
    }

    public static void main(String[] args) {
        explicitIterator();
        forEachLoop();
        listIteratorDemo();
        customIterableDemo();
        concurrentModDemo();
        forEachLambda();
    }
}

