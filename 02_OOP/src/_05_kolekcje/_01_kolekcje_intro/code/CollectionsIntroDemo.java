package _05_kolekcje._01_kolekcje_intro.code;

import java.util.*;

/**
 * CollectionsIntroDemo — demonstracja podstaw Java Collections Framework (JCF).
 *
 * <p>Pokazuje:
 * <ul>
 *   <li>hierarchię interfejsów: Collection → List / Set / Queue</li>
 *   <li>podstawowe operacje wspólne dla wszystkich kolekcji</li>
 *   <li>różnicę między starymi klasami (Vector, Hashtable) a nowoczesnymi (ArrayList, HashMap)</li>
 * </ul>
 *
 * Uruchomienie:
 * <pre>
 *   javac --release 21 -d out code/CollectionsIntroDemo.java
 *   java -cp out _05_kolekcje._01_kolekcje_intro.code.CollectionsIntroDemo
 * </pre>
 */
public class CollectionsIntroDemo {

    // -------------------------------------------------------
    // 1. Programowanie przez interfejsy
    // -------------------------------------------------------
    static void demonstrateInterfaces() {
        System.out.println("=== 1. Programowanie przez interfejsy ===");

        // Zmienna typu interfejsowego — implementacja może się zmienić bez zmiany reszty kodu
        Collection<String> names = new ArrayList<>();
        names.add("Alicja");
        names.add("Bob");
        names.add("Cezary");

        System.out.println("Kolekcja: " + names);
        System.out.println("Rozmiar: " + names.size());
        System.out.println("Zawiera 'Bob': " + names.contains("Bob"));

        names.remove("Bob");
        System.out.println("Po usunięciu Bob: " + names);
    }

    // -------------------------------------------------------
    // 2. List — zachowuje kolejność, dopuszcza duplikaty
    // -------------------------------------------------------
    static void demonstrateList() {
        System.out.println("\n=== 2. List — kolejność + duplikaty ===");

        List<Integer> numbers = new ArrayList<>(List.of(5, 3, 8, 3, 1));
        System.out.println("Lista: " + numbers);
        System.out.println("Element [2]: " + numbers.get(2));
        System.out.println("Indeks pierwszego 3: " + numbers.indexOf(3));

        numbers.sort(Comparator.naturalOrder());
        System.out.println("Po sortowaniu: " + numbers);
    }

    // -------------------------------------------------------
    // 3. Set — bez duplikatów
    // -------------------------------------------------------
    static void demonstrateSet() {
        System.out.println("\n=== 3. Set — bez duplikatów ===");

        Set<String> fruits = new HashSet<>(Arrays.asList("jabłko", "gruszka", "jabłko", "śliwka"));
        System.out.println("Set (uniq): " + fruits);              // kolejność nieokreślona
        System.out.println("Rozmiar (3 unikalne): " + fruits.size());

        Set<String> sorted = new TreeSet<>(fruits);
        System.out.println("TreeSet (sorted): " + sorted);        // alfabetycznie
    }

    // -------------------------------------------------------
    // 4. Queue — kolejka FIFO
    // -------------------------------------------------------
    static void demonstrateQueue() {
        System.out.println("\n=== 4. Queue — FIFO ===");

        Queue<String> queue = new LinkedList<>();
        queue.offer("zadanie-1");
        queue.offer("zadanie-2");
        queue.offer("zadanie-3");

        System.out.println("Podgląd czoła: " + queue.peek());
        while (!queue.isEmpty()) {
            System.out.println("  przetwarzam: " + queue.poll());
        }
    }

    // -------------------------------------------------------
    // 5. Stare vs nowe API (Java 1.0 vs Java 1.2+)
    // -------------------------------------------------------
    static void demonstrateLegacyVsModern() {
        System.out.println("\n=== 5. Stare API (synchronized) vs nowe ===");

        // Vector (Java 1.0) — synchronizowany, wolniejszy
        Vector<String> vector = new Vector<>();
        vector.add("stary Vector");

        // ArrayList (Java 1.2) — niesynchronizowany, szybszy w single-threaded
        List<String> modern = new ArrayList<>();
        modern.add("nowoczesny ArrayList");

        System.out.println("Vector: " + vector);
        System.out.println("ArrayList: " + modern);
        System.out.println("Uwaga: Vector i Hashtable istnieją dla kompatybilności wstecznej.");
        System.out.println("       W nowym kodzie używaj ArrayList / HashMap.");
    }

    // -------------------------------------------------------
    // 6. Operacje pomocnicze z klasy Collections
    // -------------------------------------------------------
    static void demonstrateCollectionsUtils() {
        System.out.println("\n=== 6. Klasa narzędziowa Collections ===");

        List<Integer> nums = new ArrayList<>(List.of(4, 1, 9, 2, 7));
        System.out.println("Przed: " + nums);

        Collections.sort(nums);
        System.out.println("sort(): " + nums);

        Collections.reverse(nums);
        System.out.println("reverse(): " + nums);

        System.out.println("min: " + Collections.min(nums));
        System.out.println("max: " + Collections.max(nums));
        System.out.println("frequency(9): " + Collections.frequency(nums, 9));

        List<Integer> unmodifiable = Collections.unmodifiableList(nums);
        System.out.println("unmodifiableList: " + unmodifiable);
        try {
            unmodifiable.add(99);
        } catch (UnsupportedOperationException e) {
            System.out.println("Próba modyfikacji → UnsupportedOperationException (OK)");
        }
    }

    public static void main(String[] args) {
        demonstrateInterfaces();
        demonstrateList();
        demonstrateSet();
        demonstrateQueue();
        demonstrateLegacyVsModern();
        demonstrateCollectionsUtils();
    }
}

