package solid.liskov_substitution.after;

/**
 * Square jako niezależna implementacja Shape.
 * ✅ Zgodne z LSP - ma własny kontrakt.
 */
public class Square implements Shape {
    private final int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public String getName() {
        return "Square";
    }

    public int getSide() {
        return side;
    }
}

