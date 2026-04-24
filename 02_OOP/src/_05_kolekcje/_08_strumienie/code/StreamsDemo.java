package _05_kolekcje._08_strumienie.code;

import java.util.*;
import java.util.stream.*;

/**
 * StreamsDemo — Stream API: potok, operacje pośrednie i terminalne, Collectors.
 */
public class StreamsDemo {

    record Product(String name, String category, double price, int quantity) {
        @Override public String toString() {
            return "%s(%s, %.0f)".formatted(name, category, price);
        }
    }

    static List<Product> sampleProducts() {
        return List.of(
            new Product("Laptop",   "elektronika", 3500, 10),
            new Product("Telefon",  "elektronika", 1200, 25),
            new Product("Słuchawki","elektronika",  250, 50),
            new Product("Kurtka",   "odzież",       450,  8),
            new Product("Spodnie",  "odzież",       200, 15),
            new Product("Buty",     "odzież",       350, 20),
            new Product("Książka",  "edukacja",      60, 100),
            new Product("Kurs online","edukacja",   299,  30)
        );
    }

    // -------------------------------------------------------
    // 1. Podstawowe operacje: filter, map, collect
    // -------------------------------------------------------
    static void basicPipeline() {
        System.out.println("=== 1. Podstawowy potok: filter → map → collect ===");

        List<String> expensiveElectronics = sampleProducts().stream()
                .filter(p -> p.category().equals("elektronika"))
                .filter(p -> p.price() > 500)
                .map(Product::name)
                .sorted()
                .collect(Collectors.toList());

        System.out.println("Droga elektronika (> 500 zł): " + expensiveElectronics);
    }

    // -------------------------------------------------------
    // 2. Operacje terminalne: count, sum, min, max, findFirst
    // -------------------------------------------------------
    static void terminalOperations() {
        System.out.println("\n=== 2. Operacje terminalne ===");
        List<Product> products = sampleProducts();

        long count = products.stream()
                .filter(p -> p.category().equals("odzież"))
                .count();
        System.out.println("Liczba produktów odzież: " + count);

        double totalValue = products.stream()
                .mapToDouble(p -> p.price() * p.quantity())
                .sum();
        System.out.printf("Łączna wartość magazynu: %.0f zł%n", totalValue);

        OptionalDouble avg = products.stream()
                .mapToDouble(Product::price)
                .average();
        System.out.printf("Średnia cena: %.2f zł%n", avg.orElse(0));

        Optional<Product> cheapest = products.stream()
                .min(Comparator.comparingDouble(Product::price));
        cheapest.ifPresent(p -> System.out.println("Najtańszy: " + p));

        boolean anyExpensive = products.stream().anyMatch(p -> p.price() > 3000);
        boolean allPositive  = products.stream().allMatch(p -> p.price() > 0);
        System.out.println("Jakiś produkt > 3000: " + anyExpensive);
        System.out.println("Wszystkie > 0: " + allPositive);
    }

    // -------------------------------------------------------
    // 3. Collectors — grupowanie i statystyki
    // -------------------------------------------------------
    static void collectors() {
        System.out.println("\n=== 3. Collectors ===");
        List<Product> products = sampleProducts();

        // groupingBy — pogrupuj po kategorii
        Map<String, List<Product>> byCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category));

        byCategory.forEach((cat, prods) ->
            System.out.println("  " + cat + ": " + prods.stream().map(Product::name).toList()));

        // groupingBy + counting
        Map<String, Long> countByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.counting()));
        System.out.println("Liczby wg kategorii: " + countByCategory);

        // groupingBy + averaging
        Map<String, Double> avgPriceByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category,
                         Collectors.averagingDouble(Product::price)));
        System.out.println("Śred. cena wg kat.: " + avgPriceByCategory);

        // join
        String names = products.stream()
                .map(Product::name)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Złączone: " + names);

        // toMap
        Map<String, Double> priceMap = products.stream()
                .collect(Collectors.toMap(Product::name, Product::price));
        System.out.println("Mapa cen: " + priceMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", ", "{", "}")));
    }

    // -------------------------------------------------------
    // 4. flatMap — spłaszczanie
    // -------------------------------------------------------
    static void flatMapDemo() {
        System.out.println("\n=== 4. flatMap ===");

        List<List<Integer>> nested = List.of(
            List.of(1, 2, 3),
            List.of(4, 5),
            List.of(6, 7, 8, 9)
        );

        List<Integer> flat = nested.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println("Spłaszczona: " + flat);

        // Użycie praktyczne — słowa ze zdań
        List<String> sentences = List.of("Kolekcje w Java", "Stream API jest potężne");
        List<String> words = sentences.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Unikalne słowa: " + words);
    }

    // -------------------------------------------------------
    // 5. reduce — własna agregacja
    // -------------------------------------------------------
    static void reduceDemo() {
        System.out.println("\n=== 5. reduce ===");

        List<Integer> nums = List.of(1, 2, 3, 4, 5);

        int sum = nums.stream().reduce(0, Integer::sum);
        System.out.println("Suma: " + sum);

        int product = nums.stream().reduce(1, (a, b) -> a * b);
        System.out.println("Iloczyn: " + product);

        Optional<Integer> max = nums.stream().reduce(Integer::max);
        System.out.println("Max: " + max.orElse(-1));
    }

    // -------------------------------------------------------
    // 6. IntStream, DoubleStream — strumienie prymitywów
    // -------------------------------------------------------
    static void primitiveStreams() {
        System.out.println("\n=== 6. IntStream — strumienie prymitywów ===");

        IntStream.rangeClosed(1, 5).forEach(i -> System.out.print(i + " "));
        System.out.println("(range 1..5)");

        int sumOfSquares = IntStream.rangeClosed(1, 10)
                .map(i -> i * i)
                .sum();
        System.out.println("Suma kwadratów 1..10: " + sumOfSquares);

        // boxing: IntStream → Stream<Integer>
        List<Integer> squares = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Kwadraty (boxed): " + squares);
    }

    // -------------------------------------------------------
    // 7. Strumienie równoległe
    // -------------------------------------------------------
    static void parallelStreams() {
        System.out.println("\n=== 7. Strumienie równoległe (parallelStream) ===");

        long count = sampleProducts().parallelStream()
                .filter(p -> p.price() > 300)
                .count();
        System.out.println("Produkty > 300 zł (parallel): " + count);

        // Uwaga: kolejność wyników może się różnić!
        List<String> names = sampleProducts().parallelStream()
                .map(Product::name)
                .sorted()          // sorted w parallel daje te same wyniki, ale jest wolniejsze
                .collect(Collectors.toList());
        System.out.println("Nazwy (sorted parallel): " + names);
    }

    public static void main(String[] args) {
        basicPipeline();
        terminalOperations();
        collectors();
        flatMapDemo();
        reduceDemo();
        primitiveStreams();
        parallelStreams();
    }
}

