package _05_kolekcje._06_mapy.code;

import java.util.*;

/**
 * MapDemo — interfejs Map, HashMap, TreeMap, LinkedHashMap i wzorzec słownika.
 */
public class MapDemo {

    // -------------------------------------------------------
    // 1. Podstawy HashMap
    // -------------------------------------------------------
    static void hashMapBasics() {
        System.out.println("=== 1. Podstawy HashMap ===");

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alicja", 95);
        scores.put("Bob", 82);
        scores.put("Cezary", 78);
        scores.put("Alicja", 99);  // nadpisuje poprzednią wartość!

        System.out.println("scores: " + scores);
        System.out.println("get('Bob'): " + scores.get("Bob"));
        System.out.println("get('Diana'): " + scores.get("Diana"));         // null
        System.out.println("getOrDefault('Diana', 0): " + scores.getOrDefault("Diana", 0));
        System.out.println("containsKey('Bob'): " + scores.containsKey("Bob"));
        System.out.println("containsValue(99): " + scores.containsValue(99));

        scores.remove("Cezary");
        System.out.println("Po remove('Cezary'): " + scores);
    }

    // -------------------------------------------------------
    // 2. Iteracja po mapie
    // -------------------------------------------------------
    static void mapIteration() {
        System.out.println("\n=== 2. Iteracja po mapie ===");

        Map<String, Integer> ages = Map.of("Jan", 30, "Anna", 25, "Piotr", 35);

        System.out.println("keySet():");
        for (String key : ages.keySet()) {
            System.out.println("  " + key);
        }

        System.out.println("values():");
        for (int age : ages.values()) {
            System.out.print(age + " ");
        }
        System.out.println();

        System.out.println("entrySet() (klucz→wartość):");
        for (Map.Entry<String, Integer> entry : ages.entrySet()) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        }

        // Najczystszy styl — forEach z lambdą
        System.out.println("forEach:");
        ages.forEach((name, age) -> System.out.println("  " + name + " lat " + age));
    }

    // -------------------------------------------------------
    // 3. Wzorzec licznika częstości
    // -------------------------------------------------------
    static void wordFrequency() {
        System.out.println("\n=== 3. Wzorzec: licznik częstości ===");

        String text = "kot pies kot ryba pies kot ptak ryba";
        String[] words = text.split(" ");

        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            // Stary styl:
            // freq.put(word, freq.getOrDefault(word, 0) + 1);

            // Java 8+ — merge
            freq.merge(word, 1, Integer::sum);
        }

        System.out.println("Częstości: " + freq);

        // Posortowane wg wartości (malejąco)
        freq.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(e -> System.out.printf("  %-8s %d%n", e.getKey(), e.getValue()));
    }

    // -------------------------------------------------------
    // 4. LinkedHashMap — kolejność wstawiania / dostępu LRU
    // -------------------------------------------------------
    static void linkedHashMapDemo() {
        System.out.println("\n=== 4. LinkedHashMap — kolejność wstawiania ===");

        // Zachowuje kolejność wstawiania
        Map<String, String> capitals = new LinkedHashMap<>();
        capitals.put("Polska", "Warszawa");
        capitals.put("Niemcy", "Berlin");
        capitals.put("Francja", "Paryż");
        capitals.put("Włochy", "Rzym");

        System.out.println("LinkedHashMap (kolejność wstawiania): " + capitals);

        // LRU Cache — accessOrder=true
        Map<Integer, String> lru = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > 3;  // max 3 elementy
            }
        };
        lru.put(1, "jeden"); lru.put(2, "dwa"); lru.put(3, "trzy");
        lru.get(1);           // dostęp → przenosi 1 na koniec (ostatnio użyty)
        lru.put(4, "cztery"); // dodanje 4 → usuwa najstarszy (2)
        System.out.println("LRU cache (max 3): " + lru);
    }

    // -------------------------------------------------------
    // 5. TreeMap — posortowane klucze
    // -------------------------------------------------------
    static void treeMapDemo() {
        System.out.println("\n=== 5. TreeMap — posortowane klucze ===");

        TreeMap<String, Integer> population = new TreeMap<>();
        population.put("Kraków", 780_000);
        population.put("Warszawa", 1_800_000);
        population.put("Gdańsk", 470_000);
        population.put("Wrocław", 640_000);

        System.out.println("Klucze posortowane: " + population.keySet());
        System.out.println("Pierwsze miasto: " + population.firstKey());
        System.out.println("Ostatnie miasto: " + population.lastKey());
        System.out.println("headMap('Kraków' exl): " + population.headMap("Kraków"));
        System.out.println("tailMap('Kraków' incl): " + population.tailMap("Kraków"));
    }

    // -------------------------------------------------------
    // 6. computeIfAbsent — grupowanie
    // -------------------------------------------------------
    static void groupingDemo() {
        System.out.println("\n=== 6. computeIfAbsent — grupowanie ===");

        List<String> names = List.of("Anna", "Adam", "Bartek", "Beata", "Celina", "Cezary", "Damian");

        Map<Character, List<String>> byInitial = new TreeMap<>();
        for (String name : names) {
            char initial = name.charAt(0);
            // computeIfAbsent — tworzy listę jeśli klucz nie istnieje
            byInitial.computeIfAbsent(initial, k -> new ArrayList<>()).add(name);
        }

        System.out.println("Grupowanie wg pierwszej litery:");
        byInitial.forEach((letter, group) -> System.out.println("  " + letter + ": " + group));
    }

    public static void main(String[] args) {
        hashMapBasics();
        mapIteration();
        wordFrequency();
        linkedHashMapDemo();
        treeMapDemo();
        groupingDemo();
    }
}

