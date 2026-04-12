package _02_interfaces.interfaces_intro;

/** Zdjęcie — druga implementacja kontraktu Printable (niezwiązana z Document!). */
public class Photo implements Printable {
    private final String filename;
    private final int    widthPx;
    private final int    heightPx;

    public Photo(String filename, int widthPx, int heightPx) {
        this.filename = filename;
        this.widthPx  = widthPx;
        this.heightPx = heightPx;
    }

    @Override
    public void print() {
        System.out.println("[FOTO] " + filename
                + "  (" + widthPx + "x" + heightPx + " px)");
    }
}

