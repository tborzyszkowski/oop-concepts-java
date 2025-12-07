package solid.liskov_substitution.before;

/**
 * ❌ Naruszenie LSP!
 *
 * Square nadpisuje setWidth i setHeight, zmieniając oba wymiary,
 * co narusza kontrakt Rectangle.
 */
public class Square extends Rectangle {

    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // ❌ Zmienia także height!
    }

    @Override
    public void setHeight(int height) {
        this.width = height;  // ❌ Zmienia także width!
        this.height = height;
    }
}

