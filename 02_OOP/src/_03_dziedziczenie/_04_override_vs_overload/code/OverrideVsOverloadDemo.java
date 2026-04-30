package _03_dziedziczenie._04_override_vs_overload.code;

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

    String supermode() {
        return super.mode();
    }
}

public class OverrideVsOverloadDemo {
    public static void main(String[] args) {
        Printer p = new ColorPrinter();
        System.out.println(p.print(7));
        System.out.println(p.print("abc"));
        System.out.println("mode=" + p.mode());
        System.out.println(p.getClass().getName());
        if(p instanceof ColorPrinter cp) {
            System.out.println("supermode=" + cp.supermode());
        }
    }
}

