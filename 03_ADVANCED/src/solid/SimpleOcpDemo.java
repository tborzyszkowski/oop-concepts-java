// Proste demo OCP - bez pakietów

public class SimpleOcpDemo {
    public static void main(String[] args) {
        System.out.println("=== OCP Demo ===");

        Shape[] shapes = {
            new Rectangle(5, 10),
            new Circle(7),
            new Triangle(6, 8)
        };

        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.area();
        }

        System.out.println("Total area: " + String.format("%.2f", totalArea));
        System.out.println("\n✅ Added Triangle without modifying existing code!");
    }
}

interface Shape {
    double area();
}

class Rectangle implements Shape {
    private double width, height;
    Rectangle(double w, double h) { width = w; height = h; }
    public double area() { return width * height; }
}

class Circle implements Shape {
    private double radius;
    Circle(double r) { radius = r; }
    public double area() { return Math.PI * radius * radius; }
}

class Triangle implements Shape {
    private double base, height;
    Triangle(double b, double h) { base = b; height = h; }
    public double area() { return 0.5 * base * height; }
}


