package solid.open_closed.before;
}
    public double getRadius() { return radius; }

    }
        this.radius = radius;
    public Circle(double radius) {

    private double radius;
class Circle {

}
    public double getHeight() { return height; }
    public double getWidth() { return width; }

    }
        this.height = height;
        this.width = width;
    public Rectangle(double width, double height) {

    private double height;
    private double width;
class Rectangle {

}
    }
        return total;
        }
            total += calculateArea(shape);
        for (Object shape : shapes) {
        double total = 0;
    public double calculateTotalArea(Object[] shapes) {

    }
        return 0;
        // ❌ Każdy nowy kształt wymaga dodania kolejnego if!
        }
            return Math.PI * circle.getRadius() * circle.getRadius();
            Circle circle = (Circle) shape;
        } else if (shape instanceof Circle) {
            return rectangle.getWidth() * rectangle.getHeight();
            Rectangle rectangle = (Rectangle) shape;
        if (shape instanceof Rectangle) {
    public double calculateArea(Object shape) {

public class AreaCalculator {
 */
 * Problem: Dodanie nowego kształtu wymaga modyfikacji tej klasy.
 *
 * Naruszenie Open/Closed Principle.
/**


