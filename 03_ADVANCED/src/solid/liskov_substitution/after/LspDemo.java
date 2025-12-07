package solid.liskov_substitution.after;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstracja rozwiązania zgodnego z LSP.
 */
public class LspDemo {

    public static void main(String[] args) {
        System.out.println("=== ROZWIĄZANIE: Zgodność z LSP ===\n");

        // Wszystkie figury implementują Shape
        List<Shape> shapes = Arrays.asList(
            new Rectangle(5, 4),
            new Square(5),
            new Rectangle(10, 2)
        );

        System.out.println("=== Test podstawialności ===");
        for (Shape shape : shapes) {
            processShape(shape);  // ✅ Działa dla wszystkich
        }

        System.out.println("\n=== KORZYŚCI ===");
        System.out.println("✅ Wszystkie figury są podstawialne");
        System.out.println("✅ Każda ma własny, spójny kontrakt");
        System.out.println("✅ Brak nieoczekiwanych zachowań");
        System.out.println("✅ Łatwe testowanie");
    }

    static void processShape(Shape shape) {
        System.out.println(shape.getName() + ":");
        System.out.println("  Powierzchnia: " + shape.getArea());
    }
}

