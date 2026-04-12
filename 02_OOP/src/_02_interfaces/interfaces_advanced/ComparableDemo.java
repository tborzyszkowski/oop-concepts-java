package _02_interfaces.interfaces_advanced;

import java.util.*;

/**
 * Demonstruje Comparable<T> i Comparator<T>.
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_advanced/*.java
 *   java -cp out interfaces_advanced.ComparableDemo
 */
public class ComparableDemo {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(List.of(
            new Product("Laptop Dell",         3_499.00, "Elektronika"),
            new Product("Klawiatura mech.",      299.00, "Peryferiale"),
            new Product("Monitor 27\"",         1_199.00, "Elektronika"),
            new Product("Mysz bezprzewodowa",    149.00, "Peryferiale"),
            new Product("Słuchawki BT",          449.00, "Audio")
        ));

        // 1. Naturalne porządkowanie (Comparable — po cenie rosnąco)
        Collections.sort(products);
        System.out.println("=== Sortowanie naturalne (cena rosnąco) ===");
        products.forEach(System.out::println);

        // 2. Comparator — po nazwie A-Z
        products.sort(Comparator.comparing(Product::getName));
        System.out.println("\n=== Sortowanie po nazwie (A-Z) ===");
        products.forEach(System.out::println);

        // 3. Comparator złożony — kategoria, potem cena malejąco
        products.sort(Comparator.comparing(Product::getCategory)
                                .thenComparing(Comparator.comparingDouble(Product::getPrice).reversed()));
        System.out.println("\n=== Sortowanie: kategoria ASC, cena DESC ===");
        products.forEach(System.out::println);

        // 4. TreeSet — automatyczne sortowanie przy dodawaniu
        TreeSet<Product> priceSet = new TreeSet<>();
        priceSet.addAll(products);
        System.out.println("\n=== TreeSet (naturalne porządkowanie) ===");
        System.out.println("Najtańszy:  " + priceSet.first());
        System.out.println("Najdroższy: " + priceSet.last());
    }
}

