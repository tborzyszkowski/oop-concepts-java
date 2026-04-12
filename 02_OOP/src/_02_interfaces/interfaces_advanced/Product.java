package _02_interfaces.interfaces_advanced;

import java.util.Objects;

/**
 * Produkt implementujący Comparable<Product> — naturalne porządkowanie po cenie.
 */
public class Product implements Comparable<Product> {
    private final String name;
    private final double price;
    private final String category;

    public Product(String name, double price, String category) {
        this.name     = name;
        this.price    = price;
        this.category = category;
    }

    /** Naturalne porządkowanie: rosnąco po cenie, przy równej cenie — po nazwie. */
    @Override
    public int compareTo(Product other) {
        int cmp = Double.compare(this.price, other.price);
        if (cmp != 0) return cmp;
        return this.name.compareTo(other.name);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product p)) return false;
        return Double.compare(price, p.price) == 0 && Objects.equals(name, p.name);
    }
    @Override public int hashCode() { return Objects.hash(name, price); }

    @Override public String toString() {
        return String.format("%-22s %8.2f PLN  [%s]", name, price, category);
    }

    public String getName()     { return name; }
    public double getPrice()    { return price; }
    public String getCategory() { return category; }
}

