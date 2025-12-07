package solid.liskov_substitution.after;

/**
 * Rectangle jako niezależna implementacja Shape.
 * ✅ Zgodne z LSP.
 */
public class Rectangle implements Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public String getName() {
        return "Rectangle";
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

