package _05_kolekcje._04_set_hashset_treeset.code;

import java.util.*;

/**
 * SetComparisonDemo — porównanie HashSet i TreeSet; kontrakt equals/hashCode.
 */
public class SetComparisonDemo {

    // -------------------------------------------------------
    // 1. HashSet — kontrakt hashCode/equals
    // -------------------------------------------------------
    static class Point {
        final int x, y;

        Point(int x, int y) { this.x = x; this.y = y; }

        // Bez equals/hashCode — dwa Point(1,2) to różne obiekty!
        @Override
        public String toString() { return "(" + x + "," + y + ")"; }
    }

    static class PointCorrect {
        final int x, y;

        PointCorrect(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PointCorrect p)) return false;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        @Override
        public String toString() { return "(" + x + "," + y + ")"; }
    }

    static void hashContractDemo() {
        System.out.println("=== 1. Kontrakt hashCode/equals ===");

        // Bez hashCode/equals — Set nie rozpoznaje duplikatów!
        Set<Point> badSet = new HashSet<>();
        badSet.add(new Point(1, 2));
        badSet.add(new Point(1, 2));   // inny obiekt, ale te same dane
        System.out.println("Point BEZ @Override: size=" + badSet.size() + " (oczekiwano 1, jest 2!)");

        // Z hashCode/equals — działa poprawnie
        Set<PointCorrect> goodSet = new HashSet<>();
        goodSet.add(new PointCorrect(1, 2));
        goodSet.add(new PointCorrect(1, 2));
        System.out.println("PointCorrect Z @Override: size=" + goodSet.size() + " (poprawnie: 1)");
    }

    // -------------------------------------------------------
    // 2. HashSet vs TreeSet
    // -------------------------------------------------------
    static void setComparisonDemo() {
        System.out.println("\n=== 2. HashSet vs TreeSet ===");

        List<String> words = List.of("banan", "jabłko", "banan", "gruszka", "ananas", "jabłko");

        Set<String> hash = new HashSet<>(words);
        Set<String> tree = new TreeSet<>(words);

        System.out.println("HashSet (losowa kolejność): " + hash);
        System.out.println("TreeSet (posortowane):      " + tree);
        System.out.println("TreeSet.first(): " + ((TreeSet<String>)tree).first());
        System.out.println("TreeSet.last():  " + ((TreeSet<String>)tree).last());

        // headSet — elementy mniejsze od podanego
        TreeSet<String> treeSet = new TreeSet<>(words);
        System.out.println("headSet(\"jabłko\"): " + treeSet.headSet("jabłko"));   // < jabłko
        System.out.println("tailSet(\"gruszka\"): " + treeSet.tailSet("gruszka")); // >= gruszka
    }

    // -------------------------------------------------------
    // 3. TreeSet z własnym Comparatorem
    // -------------------------------------------------------
    static void treeSetComparatorDemo() {
        System.out.println("\n=== 3. TreeSet z własnym Comparatorem ===");

        // Sortowanie wg długości, a przy równej długości alfabetycznie
        TreeSet<String> byLength = new TreeSet<>(
                Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        byLength.addAll(List.of("banan", "jabłko", "kiwi", "fig", "mango", "gruszka"));
        System.out.println("Wg długości: " + byLength);
    }

    // -------------------------------------------------------
    // 4. Wydajność — symulacja
    // -------------------------------------------------------
    static void performanceDemo() {
        System.out.println("\n=== 4. Wydajność: HashSet O(1) vs TreeSet O(log n) ===");

        int N = 500_000;
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();

        long t0 = System.nanoTime();
        for (int i = 0; i < N; i++) hashSet.add(i);
        for (int i = 0; i < N; i++) hashSet.contains(i);
        long hashTime = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < N; i++) treeSet.add(i);
        for (int i = 0; i < N; i++) treeSet.contains(i);
        long treeTime = System.nanoTime() - t0;

        System.out.printf("HashSet (%,d ops): %,d ms%n", 2 * N, hashTime / 1_000_000);
        System.out.printf("TreeSet (%,d ops): %,d ms%n", 2 * N, treeTime / 1_000_000);
        System.out.println("(HashSet jest szybszy dla dużych zbiorów)");
    }

    // -------------------------------------------------------
    // 5. SortedSet API — operacje na zakresach
    // -------------------------------------------------------
    static void sortedSetRangeDemo() {
        System.out.println("\n=== 5. SortedSet — operacje na zakresach ===");

        TreeSet<Integer> ts = new TreeSet<>(List.of(10, 20, 30, 40, 50, 60, 70));
        System.out.println("Cały zbiór: " + ts);
        System.out.println("headSet(40):          " + ts.headSet(40));     // < 40
        System.out.println("tailSet(40):          " + ts.tailSet(40));     // >= 40
        System.out.println("subSet(20, 60):       " + ts.subSet(20, 60));  // [20, 60)
        System.out.println("floor(35):            " + ts.floor(35));       // <= 35 → 30
        System.out.println("ceiling(35):          " + ts.ceiling(35));     // >= 35 → 40
        System.out.println("higher(30):           " + ts.higher(30));      // > 30 → 40
        System.out.println("lower(30):            " + ts.lower(30));       // < 30 → 20
    }

    public static void main(String[] args) {
        hashContractDemo();
        setComparisonDemo();
        treeSetComparatorDemo();
        performanceDemo();
        sortedSetRangeDemo();
    }
}

