package _05_kolekcje._04_set_hashset_treeset.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.Supplier;

/**
 * CollectionPerformanceDemo — porównanie czasu dodawania i wyszukiwania
 * elementów w ArrayList (wyszukiwanie liniowe i binarne), HashSet i TreeSet.
 *
 * Uruchomienie z parametrami: [liczbaElementow] [liczbaZapytan]
 */
public class CollectionPerformanceDemo {

    private static final int DEFAULT_ELEMENT_COUNT = 1_000_000;
    private static final int DEFAULT_QUERY_COUNT = 1_000_000;
    private static final int LINEAR_SEARCH_QUERY_COUNT = 200;
    private static final int WARM_UP_SIZE = 50_000;
    private static final long RANDOM_SEED = 42L;

    // Zliczanie trafień uniemożliwia JIT usunięcie "niepotrzebnych" wyszukiwań.
    private static volatile int hitCount;

    /** Wynik pomiaru jednego scenariusza (czasy w milisekundach). */
    private record Result(String name, long addMillis, long searchMillis, int searchCount) {}

    public static void main(String[] args) {
        int elementCount = readArgument(args, 0, DEFAULT_ELEMENT_COUNT);
        int queryCount = readArgument(args, 1, DEFAULT_QUERY_COUNT);

        warmUp();

        Random random = new Random(RANDOM_SEED);
        Integer[] elements = generateRandomNumbers(elementCount, 2 * elementCount, random);
        Integer[] queries = generateRandomNumbers(queryCount, 2 * elementCount, random);

        System.out.printf("Elementów: %,d, zapytań: %,d%n%n", elementCount, queryCount);

        List<Result> results = List.of(
                measureSortedList("ArrayList + binarySearch",  elements, queries),
                measureSet("HashSet", HashSet::new, elements, queries),
                measureSet("TreeSet", TreeSet::new, elements, queries),
                measureLinearList("ArrayList + contains (liniowo)", elements, queries));

        printResults(results);
    }

    private static Result measureSortedList(String name, Integer[] elements, Integer[] queries) {
        List<Integer> list = new ArrayList<>(elements.length);

        long addMillis = measureMillis(() -> {
            Collections.addAll(list, elements);
            Collections.sort(list);
        });
        long searchMillis = measureMillis(() -> {
            for (Integer query : queries) {
                if (Collections.binarySearch(list, query) >= 0) {
                    hitCount++;
                }
            }
        });
        return new Result(name, addMillis, searchMillis, queries.length);
    }

    private static Result measureLinearList(String name, Integer[] elements, Integer[] queries) {
        List<Integer> list = new ArrayList<>(elements.length);
        long addMillis = measureMillis(() -> Collections.addAll(list, elements));

        // Wyszukiwanie liniowe jest O(n), więc ograniczamy liczbę zapytań.
        int searchCount = Math.min(LINEAR_SEARCH_QUERY_COUNT, queries.length);
        long searchMillis = measureMillis(() -> {
            for (int i = 0; i < searchCount; i++) {
                if (list.contains(queries[i])) {
                    hitCount++;
                }
            }
        });
        return new Result(name, addMillis, searchMillis, searchCount);
    }

    private static Result measureSet(String name, Supplier<Collection<Integer>> factory,
                                     Integer[] elements, Integer[] queries) {
        Collection<Integer> set = factory.get();

        long addMillis = measureMillis(() -> {
            for (Integer element : elements) {
                set.add(element);
            }
        });
        long searchMillis = measureMillis(() -> {
            for (Integer query : queries) {
                if (set.contains(query)) {
                    hitCount++;
                }
            }
        });
        return new Result(name, addMillis, searchMillis, queries.length);
    }

    private static long measureMillis(Runnable action) {
        long start = System.nanoTime();
        action.run();
        return (System.nanoTime() - start) / 1_000_000;
    }

    /** Rozgrzewka JIT, aby pierwszy mierzony scenariusz nie był zaniżony. */
    private static void warmUp() {
        Integer[] data = generateRandomNumbers(WARM_UP_SIZE, 2 * WARM_UP_SIZE, new Random(RANDOM_SEED));
        measureSortedList("warm-up", data, data);
        measureSet("warm-up", HashSet::new, data, data);
        measureSet("warm-up", TreeSet::new, data, data);
        measureLinearList("warm-up", data, data);
    }

    private static Integer[] generateRandomNumbers(int count, int upperBound, Random random) {
        Integer[] numbers = new Integer[count];
        for (int i = 0; i < count; i++) {
            numbers[i] = random.nextInt(upperBound);
        }
        return numbers;
    }

    private static int readArgument(String[] args, int index, int defaultValue) {
        return args.length > index ? Integer.parseInt(args[index]) : defaultValue;
    }

    private static void printResults(List<Result> results) {
        System.out.printf("%-32s %12s %14s %10s %14s%n",
                "Kolekcja", "dodawanie ms", "wyszukiwanie ms", "zapytań", "µs / zapytanie");
        for (Result result : results) {
            double microsPerQuery = result.searchMillis() * 1000.0 / result.searchCount();
            System.out.printf("%-32s %12d %14d %,10d %14.3f%n",
                    result.name(), result.addMillis(), result.searchMillis(),
                    result.searchCount(), microsPerQuery);
        }
    }
}
