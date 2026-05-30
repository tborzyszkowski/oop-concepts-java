package _10_wzorce._10_strategy.code;

import java.util.*;
import java.util.function.Function;

/**
 * Strategy Pattern — wymienny algorytm sortowania + wymienna strategia platnosci.
 */
public class StrategyDemo {

    // ─── SORTOWANIE — klasyczna Strategy ─────────────────────────────────────
    interface SortStrategy<T> {
        void sort(List<T> data);
        String name();
        String complexity();
    }

    static class BubbleSort<T extends Comparable<T>> implements SortStrategy<T> {
        @Override
        public void sort(List<T> data) {
            int n = data.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (data.get(j).compareTo(data.get(j + 1)) > 0) {
                        Collections.swap(data, j, j + 1);
                    }
                }
            }
        }
        @Override public String name()       { return "BubbleSort"; }
        @Override public String complexity() { return "O(n²)"; }
    }

    static class InsertionSort<T extends Comparable<T>> implements SortStrategy<T> {
        @Override
        public void sort(List<T> data) {
            for (int i = 1; i < data.size(); i++) {
                T key = data.get(i);
                int j = i - 1;
                while (j >= 0 && data.get(j).compareTo(key) > 0) {
                    data.set(j + 1, data.get(j));
                    j--;
                }
                data.set(j + 1, key);
            }
        }
        @Override public String name()       { return "InsertionSort"; }
        @Override public String complexity() { return "O(n²), szybki dla małych zbiorów"; }
    }

    static class QuickSort<T extends Comparable<T>> implements SortStrategy<T> {
        @Override
        public void sort(List<T> data) {
            quickSort(data, 0, data.size() - 1);
        }

        private void quickSort(List<T> data, int lo, int hi) {
            if (lo < hi) {
                int pivot = partition(data, lo, hi);
                quickSort(data, lo, pivot - 1);
                quickSort(data, pivot + 1, hi);
            }
        }

        private int partition(List<T> data, int lo, int hi) {
            T pivot = data.get(hi);
            int i = lo - 1;
            for (int j = lo; j < hi; j++) {
                if (data.get(j).compareTo(pivot) <= 0) {
                    i++;
                    Collections.swap(data, i, j);
                }
            }
            Collections.swap(data, i + 1, hi);
            return i + 1;
        }

        @Override public String name()       { return "QuickSort"; }
        @Override public String complexity() { return "O(n log n) avg, O(n²) worst"; }
    }

    // Context — uzywajacy strategii
    static class Sorter<T extends Comparable<T>> {
        private SortStrategy<T> strategy;

        Sorter(SortStrategy<T> strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(SortStrategy<T> strategy) {
            System.out.println("  Zmiana strategii na: " + strategy.name());
            this.strategy = strategy;
        }

        public List<T> sort(List<T> data) {
            List<T> copy = new ArrayList<>(data);
            long start = System.nanoTime();
            strategy.sort(copy);
            long us = (System.nanoTime() - start) / 1000;
            System.out.printf("  %s (%s): %d elementow w %d µs → %s%n",
                    strategy.name(), strategy.complexity(), copy.size(), us,
                    copy.size() <= 15 ? copy.toString() : copy.subList(0,5) + "...");
            return copy;
        }
    }

    // ─── PLATNOSCI — Strategy w stylu Java 8+ (lambda) ───────────────────────
    interface PaymentStrategy {
        boolean pay(double amount);
        String describe();
    }

    static class CreditCard implements PaymentStrategy {
        private final String number;
        private double limit;

        CreditCard(String number, double limit) {
            this.number = number;
            this.limit  = limit;
        }

        @Override public boolean pay(double amount) {
            if (amount <= limit) {
                limit -= amount;
                System.out.printf("  💳 [CC:%s] Zaplacono %.2f (pozostalo: %.2f)%n",
                        number.substring(number.length()-4), amount, limit);
                return true;
            }
            System.out.printf("  💳 [CC:%s] Odrzucono — limit przekroczony (%.2f > %.2f)%n",
                    number.substring(number.length()-4), amount, limit);
            return false;
        }

        @Override public String describe() { return "Karta kredytowa " + number.substring(number.length()-4); }
    }

    static class PayPal implements PaymentStrategy {
        private final String email;
        private double balance;

        PayPal(String email, double balance) {
            this.email   = email;
            this.balance = balance;
        }

        @Override public boolean pay(double amount) {
            if (amount <= balance) {
                balance -= amount;
                System.out.printf("  🅿️  [PayPal:%s] Zaplacono %.2f (saldo: %.2f)%n",
                        email, amount, balance);
                return true;
            }
            System.out.println("  🅿️  [PayPal] Niewystarczajace saldo");
            return false;
        }

        @Override public String describe() { return "PayPal (" + email + ")"; }
    }

    static class ShoppingCart {
        private final List<double[]> items = new ArrayList<>();  // [price, qty]
        private PaymentStrategy paymentStrategy;

        void addItem(String name, double price, int qty) {
            items.add(new double[]{price, qty});
            System.out.printf("  +  %s × %d = %.2f PLN%n", name, qty, price * qty);
        }

        void setPaymentStrategy(PaymentStrategy strategy) {
            this.paymentStrategy = strategy;
            System.out.println("  Metoda platnosci: " + strategy.describe());
        }

        boolean checkout() {
            double total = items.stream().mapToDouble(i -> i[0] * i[1]).sum();
            System.out.printf("  SUMA: %.2f PLN%n", total);
            if (paymentStrategy == null) throw new IllegalStateException("Brak metody platnosci!");
            return paymentStrategy.pay(total);
        }
    }

    // ─── STRATEGIA JAKO LAMBDA ────────────────────────────────────────────────
    static void showLambdaStrategy() {
        System.out.println("\nStrategy jako lambda/Comparator (Java 8+):");
        List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob", "Dave", "Eve"));

        // Strategia 1: alfabetycznie
        names.sort(Comparator.naturalOrder());
        System.out.println("  Alfabetycznie: " + names);

        // Strategia 2: po dlugosci
        names.sort(Comparator.comparingInt(String::length));
        System.out.println("  Po dlugosci: " + names);

        // Strategia 3: po dlugosci, potem alfabetycznie
        names.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        System.out.println("  Dl + alfa: " + names);

        // Strategia 4: odwrotnie alfabetycznie
        names.sort(Comparator.reverseOrder());
        System.out.println("  Odwrotnie: " + names);
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Strategy Pattern ===\n");

        System.out.println("1. Sortowanie z wymienna strategia:");
        List<Integer> data = new ArrayList<>(Arrays.asList(64, 34, 25, 12, 22, 11, 90, 1, 55, 7));

        Sorter<Integer> sorter = new Sorter<Integer>(new BubbleSort<Integer>());
        sorter.sort(data);

        sorter.setStrategy(new InsertionSort<Integer>());
        sorter.sort(data);

        sorter.setStrategy(new QuickSort<Integer>());
        sorter.sort(data);

        System.out.println("\n  Duzy zbior (100 elementow):");
        List<Integer> big = new ArrayList<>();
        Random rnd = new Random(42);
        for (int i = 0; i < 100; i++) big.add(rnd.nextInt(1000));

        new Sorter<Integer>(new BubbleSort<Integer>()).sort(new ArrayList<>(big));
        new Sorter<Integer>(new InsertionSort<Integer>()).sort(new ArrayList<>(big));
        new Sorter<Integer>(new QuickSort<Integer>()).sort(new ArrayList<>(big));

        System.out.println("\n2. Platnosci — wymienna strategia:");
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Laptop", 2999.99, 1);
        cart.addItem("Mysz", 49.99, 2);

        System.out.println("\n  Proba 1 — karta kredytowa:");
        cart.setPaymentStrategy(new CreditCard("4111111111111234", 4000.00));
        boolean ok = cart.checkout();
        System.out.println("  Wynik: " + (ok ? "Sukces" : "Odrzucono"));

        System.out.println("\n  Proba 2 — PayPal (za malo srodkow):");
        cart.setPaymentStrategy(new PayPal("alice@example.com", 500.00));
        ok = cart.checkout();
        System.out.println("  Wynik: " + (ok ? "Sukces" : "Odrzucono"));

        showLambdaStrategy();
    }
}

