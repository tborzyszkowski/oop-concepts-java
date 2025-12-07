package solid.liskov_substitution.before;

/**
 * Demonstracja naruszenia LSP.
 */
public class LspDemo {

    public static void main(String[] args) {
        System.out.println("=== PROBLEM: Naruszenie LSP ===\n");

        System.out.println("Test 1: Rectangle");
        Rectangle rect = new Rectangle();
        testRectangle(rect);

        System.out.println("\nTest 2: Square jako Rectangle");
        Rectangle square = new Square();
        testRectangle(square);  // ❌ FAIL - naruszenie LSP!

        System.out.println("\n=== PROBLEMY ===");
        System.out.println("❌ Square nie jest podstawialny za Rectangle");
        System.out.println("❌ Naruszenie kontraktu - setWidth zmienia height");
        System.out.println("❌ Test przechodzi dla Rectangle, ale nie dla Square");
    }

    static void testRectangle(Rectangle r) {
        r.setWidth(5);
        r.setHeight(4);

        System.out.println("Szerokość: " + r.getWidth());
        System.out.println("Wysokość: " + r.getHeight());
        System.out.println("Powierzchnia: " + r.getArea());
        System.out.println("Oczekiwana powierzchnia: 20");

        if (r.getArea() == 20) {
            System.out.println("✅ Test PASSED");
        } else {
            System.out.println("❌ Test FAILED - LSP violated!");
        }
    }
}

