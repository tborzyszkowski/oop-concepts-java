package inheritance.t04;

class Printer {
    String print(int value) {
        return "int=" + value;
    }

    String print(String value) {
        return "String=" + value;
    }

    String mode() {
        return "base";
    }
}

class ColorPrinter extends Printer {
    @Override
    String mode() {
        return "color";
    }
}

public class OverrideVsOverloadDemo {
    public static void main(String[] args) {
        Printer p = new ColorPrinter();
        System.out.println(p.print(7));
        System.out.println(p.print("abc"));
        System.out.println("mode=" + p.mode());
    }
}

