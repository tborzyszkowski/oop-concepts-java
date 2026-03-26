package inheritance.t05;

abstract class Shape {
    abstract double area();

    String kind() {
        return "Shape";
    }

    static String category() {
        return "shape-static";
    }
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) { this.radius = radius; }

    @Override
    double area() { return Math.PI * radius * radius; }

    @Override
    String kind() {
        return "Circle";
    }

    static String category() {
        return "circle-static";
    }
}

class Rectangle extends Shape {
    private final double width;
    private final double height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() { return width * height; }

    @Override
    String kind() {
        return "Rectangle";
    }
}

class ShapeReporter {
    void report(Shape shape) {
        System.out.printf("report(Shape): kind=%s area=%.2f%n", shape.kind(), shape.area());
    }

    void report(Circle circle) {
        System.out.printf("report(Circle): radius-based area=%.2f%n", circle.area());
    }
}

public class DynamicBindingDemo {
    public static void main(String[] args) {
        ShapeReporter reporter = new ShapeReporter();

        System.out.println("=== Wiazanie wczesne (compile-time) ===");
        Circle circle = new Circle(2);
        Shape circleAsShape = circle;

        // Przeciazanie metod: decyzja po typie referencji w czasie kompilacji.
        reporter.report(circle);
        reporter.report(circleAsShape);

        // Metody statyczne: decyzja po typie, nie po obiekcie runtime.
        System.out.println("Shape.category()  = " + Shape.category());
        System.out.println("Circle.category() = " + Circle.category());
        System.out.println("circleAsShape.category() = " + circleAsShape.category());

        System.out.println();
        System.out.println("=== Wiazanie pozne (runtime, dynamic dispatch) ===");
        Shape[] shapes = { new Circle(2), new Rectangle(3, 4) };
        for (Shape shape : shapes) {
            System.out.printf("runtimeType=%s kind=%s area=%.2f%n",
                    shape.getClass().getSimpleName(),
                    shape.kind(),
                    shape.area());
        }
    }
}

