package _05_kolekcje._07_komparatory.code;

import java.util.*;

/**
 * ComparatorDemo — Comparable, Comparator, łańcuchowanie, odwracanie porządku.
 */
public class ComparatorDemo {

    // -------------------------------------------------------
    // Klasa User z Comparable (podejście 1: porządek naturalny)
    // -------------------------------------------------------
    record User(String name, int age, double salary) implements Comparable<User> {
        @Override
        public int compareTo(User other) {
            return this.name.compareTo(other.name);  // naturalny = alfabetycznie po imieniu
        }
        @Override public String toString() {
            return "%s(%d, %.0f)".formatted(name, age, salary);
        }
    }

    // -------------------------------------------------------
    // 1. Comparable — naturalny porządek
    // -------------------------------------------------------
    static void comparableDemo() {
        System.out.println("=== 1. Comparable — porządek naturalny (alfabetycznie) ===");

        List<User> users = new ArrayList<>(List.of(
            new User("Zofia", 30, 8000),
            new User("Adam", 25, 5000),
            new User("Maria", 35, 9000),
            new User("Bartek", 28, 6500)
        ));

        Collections.sort(users);  // używa User.compareTo
        System.out.println("Po nazwie (Comparable): " + users);

        // TreeSet z Comparable
        TreeSet<User> byName = new TreeSet<>(users);
        System.out.println("TreeSet (naturalny): " + byName);
    }

    // -------------------------------------------------------
    // 2. Comparator — alternatywne porządki
    // -------------------------------------------------------
    static void comparatorDemo() {
        System.out.println("\n=== 2. Comparator — alternatywne porządki ===");

        List<User> users = new ArrayList<>(List.of(
            new User("Zofia", 30, 8000),
            new User("Adam", 25, 5000),
            new User("Maria", 35, 9000),
            new User("Bartek", 28, 6500)
        ));

        // Wg wieku (rosnąco)
        Comparator<User> byAge = Comparator.comparingInt(User::age);
        users.sort(byAge);
        System.out.println("Wg wieku:          " + users);

        // Wg wynagrodzenia (malejąco)
        Comparator<User> bySalaryDesc = Comparator.comparingDouble(User::salary).reversed();
        users.sort(bySalaryDesc);
        System.out.println("Wg salary (desc):  " + users);

        // Wg długości imienia, potem alfabetycznie
        Comparator<User> byNameLen = Comparator
                .comparingInt((User u) -> u.name().length())
                .thenComparing(User::name);
        users.sort(byNameLen);
        System.out.println("Wg dł. imienia:    " + users);
    }

    // -------------------------------------------------------
    // 3. Comparator.comparing + thenComparing
    // -------------------------------------------------------
    static void chainedComparatorsDemo() {
        System.out.println("\n=== 3. Łańcuchowanie komparatorów ===");

        List<String> words = new ArrayList<>(
            List.of("banan", "jabłko", "kiwi", "fig", "mango", "gruszka", "ananas")
        );

        // Sortuj wg długości, przy równej długości — alfabetycznie
        words.sort(
            Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        System.out.println("Wg długości, potem alfa: " + words);

        // Odwróć cały porządek
        words.sort(
            Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()).reversed()
        );
        System.out.println("Odwrócony:              " + words);
    }

    // -------------------------------------------------------
    // 4. Comparator do Map i PriorityQueue
    // -------------------------------------------------------
    static void comparatorInCollections() {
        System.out.println("\n=== 4. Comparator w kolekcjach ===");

        // TreeMap z odwróconym porządkiem kluczy
        TreeMap<String, Integer> reverseMap = new TreeMap<>(Comparator.reverseOrder());
        reverseMap.put("gamma", 3); reverseMap.put("alpha", 1); reverseMap.put("beta", 2);
        System.out.println("TreeMap (odwrócony porządek kluczy): " + reverseMap.keySet());

        // PriorityQueue wg jakiegoś pola
        PriorityQueue<User> byAgeQueue = new PriorityQueue<>(Comparator.comparingInt(User::age));
        byAgeQueue.offer(new User("Zofia", 30, 0));
        byAgeQueue.offer(new User("Adam", 25, 0));
        byAgeQueue.offer(new User("Maria", 35, 0));
        System.out.print("PriorityQueue wg wieku: ");
        while (!byAgeQueue.isEmpty()) System.out.print(byAgeQueue.poll().name() + " ");
        System.out.println();
    }

    // -------------------------------------------------------
    // 5. Comparator.nullsFirst / nullsLast
    // -------------------------------------------------------
    static void nullHandlingDemo() {
        System.out.println("\n=== 5. nullsFirst / nullsLast ===");

        List<String> withNulls = new ArrayList<>(Arrays.asList("banana", null, "apple", null, "cherry"));

        withNulls.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        System.out.println("nullsFirst: " + withNulls);

        withNulls.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        System.out.println("nullsLast:  " + withNulls);
    }

    public static void main(String[] args) {
        comparableDemo();
        comparatorDemo();
        chainedComparatorsDemo();
        comparatorInCollections();
        nullHandlingDemo();
    }
}

