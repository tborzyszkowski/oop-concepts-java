package inheritance.t09;

final class ImmutablePoint {
    private final int x;
    private final int y;

    ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }
}

class BaseService {
    public final void audit() {
        System.out.println("audit");
    }
}

public class FinalKeywordDemo {
    public static void main(String[] args) {
        ImmutablePoint p = new ImmutablePoint(3, 4);
        System.out.println("(" + p.x() + "," + p.y() + ")");
        new BaseService().audit();
    }
}

