package _05_kolekcje._09_projekt.code;

import java.util.*;
import java.util.stream.*;

/**
 * AnagramSolver — grupowanie słów w anagramy przy użyciu kolekcji i Stream API.
 *
 * <p>Problem: dla danej listy słów znajdź wszystkie grupy anagramów (słów zbudowanych
 * z tych samych liter w dowolnej kolejności). Np. "rak", "kar", "ark" to anagramy.
 *
 * <p>Klucza technika: kanoniczna forma słowa = posortowane litery → klucz w mapie.
 * Np. "rak" → "akr", "kar" → "akr", "ark" → "akr" — wszystkie mają taki sam klucz.
 */
public class AnagramSolver {

    // -------------------------------------------------------
    // Pomocnicza: klucz kanoniczny = posortowane litery
    // -------------------------------------------------------
    static String canonical(String word) {
        char[] chars = word.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // -------------------------------------------------------
    // 1. Podejście z pętlami i kolekcjami
    // -------------------------------------------------------
    static Map<String, List<String>> groupAnagramsImperative(List<String> words) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : words) {
            String key = canonical(word);
            // computeIfAbsent — tworzy listę, jeśli klucz jeszcze nie istnieje
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }

        // Usuń grupy z jednym słowem (nie są anagramami)
        groups.entrySet().removeIf(e -> e.getValue().size() < 2);

        return groups;
    }

    // -------------------------------------------------------
    // 2. Podejście ze Stream API
    // -------------------------------------------------------
    static Map<String, List<String>> groupAnagramsStream(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(AnagramSolver::canonical))
                .entrySet().stream()
                .filter(e -> e.getValue().size() >= 2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // -------------------------------------------------------
    // 3. Wypisywanie wyników
    // -------------------------------------------------------
    static void printAnagramGroups(Map<String, List<String>> groups, String label) {
        System.out.println("\n--- " + label + " ---");
        if (groups.isEmpty()) {
            System.out.println("  (brak anagramów)");
            return;
        }
        groups.entrySet().stream()
            .sorted(Comparator.comparingInt((Map.Entry<String, List<String>> e) ->
                e.getValue().size()).reversed())
            .forEach(e -> {
                List<String> sorted = new ArrayList<>(e.getValue());
                Collections.sort(sorted);
                System.out.printf("  [%s] → %s%n", e.getKey(), sorted);
            });
    }

    // -------------------------------------------------------
    // 4. Duże demo: słownik języka polskiego (próbka)
    // -------------------------------------------------------
    static void largeDemo() {
        List<String> words = List.of(
            "rak", "kar", "ark",
            "kat", "tak", "akt",
            "kos", "sok", "osk",
            "klip", "kilp", "plik",
            "most", "mots", "stom",
            "karta", "ratka", "tarka",
            "alarm", "malra", "lamar",
            "stolica", "solicat",
            "biegacz",
            "koń",
            "pilot", "polit",
            "ryba", "bary",
            "les", "sel",
            "pies",
            "mama"
        );

        System.out.println("=== Demo: grupowanie anagramów ===");
        System.out.println("Słów wejściowych: " + words.size());

        // Podejście imperatywne
        Map<String, List<String>> byImperative = groupAnagramsImperative(words);
        printAnagramGroups(byImperative, "Podejście imperatywne (pętle + computeIfAbsent)");

        // Podejście z Stream API
        Map<String, List<String>> byStream = groupAnagramsStream(words);
        printAnagramGroups(byStream, "Podejście funkcyjne (Stream + Collectors.groupingBy)");

        // Statystyki
        System.out.println("\n=== Statystyki ===");
        System.out.println("Liczba grup anagramów: " + byStream.size());
        long totalAnagramWords = byStream.values().stream().mapToLong(List::size).sum();
        System.out.println("Łączna liczba słów w grupach: " + totalAnagramWords);

        Optional<Map.Entry<String, List<String>>> largestGroup = byStream.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()));
        largestGroup.ifPresent(e ->
            System.out.println("Największa grupa [" + e.getKey() + "]: " + e.getValue()));
    }

    // -------------------------------------------------------
    // 5. Top N grup
    // -------------------------------------------------------
    static void topNGroups(Map<String, List<String>> groups, int n) {
        System.out.println("\n=== Top " + n + " grup (wg rozmiaru) ===");
        groups.entrySet().stream()
            .sorted(Comparator.comparingInt((Map.Entry<String, List<String>> e) ->
                e.getValue().size()).reversed())
            .limit(n)
            .forEach(e -> System.out.println("  " + e.getValue() + " (klucz: " + e.getKey() + ")"));
    }

    // -------------------------------------------------------
    // 6. Rozszerzenie: wyszukiwanie anagramów konkretnego słowa
    // -------------------------------------------------------
    static List<String> findAnagramsOf(String word, List<String> dictionary) {
        String key = canonical(word);
        return dictionary.stream()
                .filter(w -> !w.equalsIgnoreCase(word))  // wyklucz samo słowo
                .filter(w -> canonical(w).equals(key))
                .sorted()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        largeDemo();

        List<String> allWords = List.of(
            "rak", "kar", "ark", "kat", "tak", "akt", "kos", "sok",
            "klip", "plik", "most", "karta", "tarka", "ratka", "alarm",
            "ryba", "bary", "pies", "pilot", "polit"
        );

        Map<String, List<String>> groups = groupAnagramsStream(allWords);
        topNGroups(groups, 3);

        System.out.println("\n=== Anagramy słowa 'rak' ===");
        System.out.println(findAnagramsOf("rak", allWords));
    }
}

