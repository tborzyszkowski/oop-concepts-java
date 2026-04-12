package _02_interfaces.interfaces_advanced;

import java.util.List;
import java.util.function.*;

/**
 * Demonstruje interfejsy funkcyjne z java.util.function:
 *  - @FunctionalInterface + lambda
 *  - Predicate<T>, Function<T,R>, Consumer<T>, Supplier<T>
 *  - Method references (referencje do metod)
 *  - Kompozycja (andThen, compose, and, or)
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_advanced/*.java
 *   java -cp out interfaces_advanced.FunctionalDemo
 */
public class FunctionalDemo {
    public static void main(String[] args) {

        // 1. Własny @FunctionalInterface — lambda jako implementacja
        Validator<String> notEmpty  = s -> !s.isBlank();
        Validator<String> minLength = s -> s.length() >= 3;
        Validator<String> noSpaces  = s -> !s.contains(" ");

        // Kompozycja walidatorów (metody default and/or/negate)
        Validator<String> usernameOk = notEmpty.and(minLength).and(noSpaces);

        System.out.println("=== Walidator username ===");
        String[] names = {"", "Al", "Alice", "Jan Kowalski", "bob123"};
        for (String name : names) {
            System.out.printf("  %-15s -> %s%n", "\"" + name + "\"",
                usernameOk.validate(name) ? "OK" : "BLAD");
        }

        // 2. Predicate<T> — java.util.function
        Predicate<Integer> isPositive = n -> n > 0;
        Predicate<Integer> isEven     = n -> n % 2 == 0;
        Predicate<Integer> posEven    = isPositive.and(isEven);

        System.out.println("\n=== Predicate ===");
        List.of(-4, -1, 0, 2, 3, 8).forEach(n ->
            System.out.printf("  %3d -> positive=%b, even=%b, posEven=%b%n",
                n, isPositive.test(n), isEven.test(n), posEven.test(n)));

        // 3. Function<T,R> — transformacja + kompozycja
        Function<String, String>  toUpper     = String::toUpperCase;   // method reference
        Function<String, String>  addBrackets = s -> "[" + s + "]";
        Function<String, Integer> strLength   = String::length;

        Function<String, String> pipeline = toUpper.andThen(addBrackets);

        System.out.println("\n=== Function + andThen ===");
        List.of("java", "interfejs", "lambda").forEach(w ->
            System.out.printf("  %-12s -> %s  (len=%d)%n",
                w, pipeline.apply(w), strLength.apply(w)));

        // 4. Consumer<T> — efekt uboczny + andThen
        Consumer<String> print   = System.out::println;
        Consumer<String> printUp = s -> System.out.println(s.toUpperCase());
        Consumer<String> both    = print.andThen(printUp);

        System.out.println("\n=== Consumer + andThen ===");
        both.accept("hello consumer");

        // 5. Supplier<T> — fabryka wartości (leniwa)
        Supplier<java.util.List<String>> listFactory = java.util.ArrayList::new;
        var list1 = listFactory.get();
        var list2 = listFactory.get();
        list1.add("a"); list2.add("b");
        System.out.println("\n=== Supplier (osobne instancje) ===");
        System.out.println("list1=" + list1 + "  list2=" + list2);
    }
}

