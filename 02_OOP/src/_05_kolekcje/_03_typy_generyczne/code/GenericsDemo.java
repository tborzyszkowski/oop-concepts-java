package _05_kolekcje._03_typy_generyczne.code;

import java.util.*;
import java.util.function.Predicate;

/**
 * GenericsDemo — typy generyczne w Javie: historia, zastosowanie, ograniczenia.
 *
 * <p>Pokazuje:
 * <ul>
 *   <li>dlaczego generyki? (problem z Object)</li>
 *   <li>klasa generyczna i metoda generyczna</li>
 *   <li>ograniczenia górne (extends) i dolne (super)</li>
 *   <li>wildcard (?) — nieznany typ</li>
 *   <li>type erasure — co z tym robi JVM</li>
 * </ul>
 */
public class GenericsDemo {

    // -------------------------------------------------------
    // 1. Problem przed generykami (Java < 5)
    // -------------------------------------------------------
    static void problemBeforeGenerics() {
        System.out.println("=== 1. Problem przed generykami ===");

        // Stara lista oparta na Object — brak bezpieczeństwa typów w compile time
        List rawList = new ArrayList();   // unchecked warning
        rawList.add("tekst");
        rawList.add(42);                  // błąd nie jest widoczny w compile time!
        rawList.add(3.14);

        System.out.println("Raw list: " + rawList);
        // Przy pobieraniu wymagany rzut — może rzucić ClassCastException w runtime:
        try {
            String s = (String) rawList.get(1);  // 42 → ClassCastException!
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage() + " (błąd w runtime!)");
        }

        // Z generykami — błąd wykrywany w compile time:
        List<String> safeList = new ArrayList<>();
        safeList.add("tekst");
        // safeList.add(42);  // ← błąd kompilacji! Bezpiecznie.
        String s2 = safeList.get(0);      // nie wymaga rzutu
        System.out.println("Generyczne: " + s2 + " (typ bezpieczny)");
    }

    // -------------------------------------------------------
    // 2. Klasa generyczna
    // -------------------------------------------------------
    static class Pair<A, B> {
        private final A first;
        private final B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        A getFirst() { return first; }
        B getSecond() { return second; }

        @Override
        public String toString() {
            return "(%s, %s)".formatted(first, second);
        }

        // Metoda generyczna wewnątrz klasy generycznej
        <C> Pair<A, C> withSecond(C newSecond) {
            return new Pair<>(this.first, newSecond);
        }
    }

    static void genericClassDemo() {
        System.out.println("\n=== 2. Klasa generyczna Pair<A,B> ===");

        Pair<String, Integer> p1 = new Pair<>("klucz", 42);
        System.out.println("Para: " + p1);
        System.out.println("Pierwszy: " + p1.getFirst());  // typ String bez rzutu

        Pair<String, Double> p2 = p1.withSecond(3.14);
        System.out.println("Nowa para: " + p2);
    }

    // -------------------------------------------------------
    // 3. Metoda generyczna
    // -------------------------------------------------------
    static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    static <T> List<T> repeat(T element, int count) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < count; i++) result.add(element);
        return result;
    }

    static void genericMethodDemo() {
        System.out.println("\n=== 3. Metody generyczne ===");
        System.out.println("max(3, 7) = " + max(3, 7));
        System.out.println("max(\"jabłko\", \"gruszka\") = " + max("jabłko", "gruszka"));
        System.out.println("repeat(\"ha\", 3) = " + repeat("ha", 3));
    }

    // -------------------------------------------------------
    // 4. Ograniczenia: extends (górne) i super (dolne)
    // -------------------------------------------------------
    // extends — przyjmuje typ T i jego podtypy → "producent"
    static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0;
        for (Number n : numbers) sum += n.doubleValue();
        return sum;
    }

    // super — przyjmuje typ T i jego nadtypy → "konsument"
    static void addNumbers(List<? super Integer> target, int count) {
        for (int i = 1; i <= count; i++) target.add(i);
    }

    static void wildcardDemo() {
        System.out.println("\n=== 4. Wildcard i ograniczenia ===");

        List<Integer> ints = List.of(1, 2, 3, 4, 5);
        List<Double> doubles = List.of(1.5, 2.5, 3.5);

        System.out.println("sumNumbers(ints): " + sumNumbers(ints));       // List<? extends Number>
        System.out.println("sumNumbers(doubles): " + sumNumbers(doubles));  // też działa!

        List<Number> numbers = new ArrayList<>();
        addNumbers(numbers, 5);   // List<? super Integer>
        System.out.println("addNumbers do List<Number>: " + numbers);
    }

    // -------------------------------------------------------
    // 5. Type Erasure — co robi kompilator
    // -------------------------------------------------------
    static void typeErasureDemo() {
        System.out.println("\n=== 5. Type Erasure ===");

        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();

        // W runtime oba mają ten sam typ (ArrayList)!
        System.out.println("List<String>.class == List<Integer>.class → " +
                (strings.getClass() == integers.getClass()));

        // Sprawdzanie instanceof z generykami niemożliwe:
        // if (strings instanceof List<String>) {}  // błąd kompilacji
        if (strings instanceof List<?>) {
            System.out.println("List<?> instanceof List<?> → true (ok)");
        }

        System.out.println("Runtime class: " + strings.getClass().getName());
        // → java.util.ArrayList (bez parametru typu!)
    }

    // -------------------------------------------------------
    // 6. Generyczny Stack
    // -------------------------------------------------------
    static class Stack<T> {
        private final Deque<T> storage = new ArrayDeque<>();

        void push(T item) { storage.push(item); }
        T pop() { return storage.pop(); }
        T peek() { return storage.peek(); }
        boolean isEmpty() { return storage.isEmpty(); }
        int size() { return storage.size(); }

        @Override public String toString() { return storage.toString(); }
    }

    static void genericStackDemo() {
        System.out.println("\n=== 6. Generyczny Stack<T> ===");

        Stack<String> wordStack = new Stack<>();
        wordStack.push("Java");
        wordStack.push("Collections");
        wordStack.push("Generics");

        System.out.println("Stos: " + wordStack);
        System.out.println("Pop: " + wordStack.pop());
        System.out.println("Po pop: " + wordStack);
    }

    public static void main(String[] args) {
        problemBeforeGenerics();
        genericClassDemo();
        genericMethodDemo();
        wildcardDemo();
        typeErasureDemo();
        genericStackDemo();
    }
}

