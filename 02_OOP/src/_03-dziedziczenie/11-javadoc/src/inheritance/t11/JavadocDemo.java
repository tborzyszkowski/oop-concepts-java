package inheritance.t11;

/**
 * Reprezentuje prostokąt i dostarcza operacje geometryczne.
 */
class Rectangle {
    private final double width;
    private final double height;

    /**
     * @param width szerokosc > 0
     * @param height wysokosc > 0
     */
    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return pole prostokata
     */
    double area() {
        return width * height;
    }
}

public class JavadocDemo {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(2.0, 5.0);
        System.out.println("area=" + rectangle.area());
    }
}

