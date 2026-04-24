package _05_kolekcje._10_zadania.solutions;

import java.util.*;
import java.util.stream.*;

/**
 * CollectionsExercisesSolutions — rozwiązania referencyjne zadań z modułu 10.
 */
public class CollectionsExercisesSolutions {

    // -----------------------------------------------------------
    // Zadanie 1: Usuń duplikaty zachowując kolejność
    // -----------------------------------------------------------
    public static <T> List<T> removeDuplicatesPreserveOrder(List<T> list) {
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    // -----------------------------------------------------------
    // Zadanie 2: Odwróć mapę (wartość → klucz)
    // -----------------------------------------------------------
    public static <K, V> Map<V, K> invertMap(Map<K, V> original) {
        Map<V, K> inverted = new HashMap<>();
        original.forEach((k, v) -> inverted.put(v, k));
        return inverted;
    }

    // -----------------------------------------------------------
    // Zadanie 3: Najczęstszy element w liście
    // -----------------------------------------------------------
    public static <T> Optional<T> mostFrequent(List<T> list) {
        return list.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // -----------------------------------------------------------
    // Zadanie 4: Grupuj liczby na parzyste/nieparzyste
    // -----------------------------------------------------------
    public static Map<Boolean, List<Integer>> partitionByParity(List<Integer> nums) {
        return nums.stream().collect(Collectors.partitioningBy(n -> n % 2 == 0));
    }

    // -----------------------------------------------------------
    // Zadanie 5: Znajdź k-ty element (0-based) w posortowanym zbiorze
    // -----------------------------------------------------------
    public static <T extends Comparable<T>> Optional<T> kthSmallest(Collection<T> col, int k) {
        return col.stream().sorted().skip(k).findFirst();
    }

    // -----------------------------------------------------------
    // Zadanie 6: Zamień listę słów na mapę słowo → długość
    // -----------------------------------------------------------
    public static Map<String, Integer> wordsToLengthMap(List<String> words) {
        return words.stream()
                .collect(Collectors.toMap(w -> w, String::length, (a, b) -> a));
    }

    // -----------------------------------------------------------
    // Zadanie 7: Sprawdź czy lista jest posortowana
    // -----------------------------------------------------------
    public static <T extends Comparable<T>> boolean isSorted(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) return false;
        }
        return true;
    }

    // -----------------------------------------------------------
    // Zadanie 8: Transpozycja: List<List<T>> wierszy → kolumny
    // -----------------------------------------------------------
    public static <T> List<List<T>> transpose(List<List<T>> matrix) {
        if (matrix.isEmpty()) return Collections.emptyList();
        int cols = matrix.get(0).size();
        List<List<T>> result = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            List<T> col = new ArrayList<>();
            for (List<T> row : matrix) col.add(row.get(c));
            result.add(col);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== Rozwiązania ===");

        // 1
        List<Integer> withDups = List.of(1, 3, 2, 1, 4, 3, 5);
        System.out.println("removeDups: " + removeDuplicatesPreserveOrder(withDups));

        // 2
        Map<String, Integer> m = Map.of("a", 1, "b", 2, "c", 3);
        System.out.println("invertMap: " + invertMap(m));

        // 3
        List<String> words = List.of("kot", "pies", "kot", "ryba", "pies", "kot");
        System.out.println("mostFrequent: " + mostFrequent(words));

        // 4
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println("partitionByParity: " + partitionByParity(nums));

        // 5
        System.out.println("kthSmallest(2): " + kthSmallest(List.of(5, 3, 8, 1, 9), 2));

        // 6
        System.out.println("wordsToLength: " + wordsToLengthMap(List.of("Java", "Kolekcje", "Stream")));

        // 7
        System.out.println("isSorted([1,2,5]): " + isSorted(List.of(1, 2, 5)));
        System.out.println("isSorted([1,5,2]): " + isSorted(List.of(1, 5, 2)));

        // 8
        List<List<Integer>> matrix = List.of(
            List.of(1, 2, 3),
            List.of(4, 5, 6)
        );
        System.out.println("transpose: " + transpose(matrix));
    }
}

