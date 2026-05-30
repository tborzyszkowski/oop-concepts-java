package _10_wzorce._06_decorator.code;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator Pattern — dodawanie zachowania bez dziedziczenia.
 * Przyklad: kawiarnia z dodatkiami do kawy.
 */
public class DecoratorDemo {

    // ─── Komponent bazowy ─────────────────────────────────────────────────────
    interface Coffee {
        double getCost();
        String getDescription();
    }

    static class SimpleCoffee implements Coffee {
        @Override public double getCost()          { return 2.0; }
        @Override public String getDescription()   { return "Kawa (2.0)"; }
    }

    static class Espresso implements Coffee {
        @Override public double getCost()          { return 3.5; }
        @Override public String getDescription()   { return "Espresso (3.5)"; }
    }

    // ─── Bazowy dekorator ─────────────────────────────────────────────────────
    abstract static class CoffeeDecorator implements Coffee {
        protected final Coffee coffee;

        CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }

        @Override public double getCost()        { return coffee.getCost(); }
        @Override public String getDescription() { return coffee.getDescription(); }
    }

    // ─── Konkretne dekoratory ─────────────────────────────────────────────────
    static class MilkDecorator extends CoffeeDecorator {
        MilkDecorator(Coffee coffee) { super(coffee); }

        @Override public double getCost() { return super.getCost() + 0.5; }
        @Override public String getDescription() {
            return super.getDescription() + " + Mleko (+0.5)";
        }
    }

    static class SugarDecorator extends CoffeeDecorator {
        private final int spoons;

        SugarDecorator(Coffee coffee, int spoons) {
            super(coffee);
            this.spoons = spoons;
        }

        @Override public double getCost() { return super.getCost() + spoons * 0.2; }
        @Override public String getDescription() {
            return super.getDescription() + " + Cukier x" + spoons + " (+" + (spoons * 0.2) + ")";
        }
    }

    static class VanillaDecorator extends CoffeeDecorator {
        VanillaDecorator(Coffee coffee) { super(coffee); }

        @Override public double getCost() { return super.getCost() + 0.8; }
        @Override public String getDescription() {
            return super.getDescription() + " + Wanilia (+0.8)";
        }
    }

    static class WhipDecorator extends CoffeeDecorator {
        WhipDecorator(Coffee coffee) { super(coffee); }

        @Override public double getCost() { return super.getCost() + 0.6; }
        @Override public String getDescription() {
            return super.getDescription() + " + Bita Smietana (+0.6)";
        }
    }

    // ─── Dekorator logowania (inny cel) ──────────────────────────────────────
    static class LoggingDecorator extends CoffeeDecorator {
        private final List<String> log = new ArrayList<>();

        LoggingDecorator(Coffee coffee) { super(coffee); }

        @Override public double getCost() {
            double cost = super.getCost();
            log.add("getCost() → " + cost);
            return cost;
        }

        @Override public String getDescription() {
            String desc = super.getDescription();
            log.add("getDescription() → " + desc.substring(0, 20) + "...");
            return desc;
        }

        public List<String> getLog() { return List.copyOf(log); }
    }

    // ─── Dekorator porownajac z dziedziczeniem ─────────────────────────────
    // Gdybysmy uzywali dziedziczenia:
    // SimpleCoffeeWithMilk, SimpleCoffeeWithSugar, SimpleCoffeeWithMilkAndSugar,
    // SimpleCoffeeWithMilkAndSugarAndVanilla, Espresso*..., EspressoWithMilk*...
    // → 2^N klas przy N dodatkach! To eksplozja klas.
    // Dekorator rozwiazuje to przez kompozycje.

    static void printOrder(Coffee coffee) {
        System.out.printf("  %-55s  Cena: %.1f PLN%n",
                coffee.getDescription(), coffee.getCost());
    }

    // ─── MAIN ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Decorator Pattern ===\n");

        System.out.println("1. Prosta kawa bez dodatkow:");
        Coffee c1 = new SimpleCoffee();
        printOrder(c1);

        System.out.println("\n2. Kawa z mlekiem:");
        Coffee c2 = new MilkDecorator(new SimpleCoffee());
        printOrder(c2);

        System.out.println("\n3. Espresso z mlekiem i cukrem (2 lyzeczki):");
        Coffee c3 = new SugarDecorator(new MilkDecorator(new Espresso()), 2);
        printOrder(c3);

        System.out.println("\n4. Mega kaawa z wszystkim (warstwowe dekoratory):");
        Coffee c4 = new WhipDecorator(
                        new VanillaDecorator(
                            new SugarDecorator(
                                new MilkDecorator(
                                    new Espresso()), 1)));
        printOrder(c4);

        System.out.println("\n5. Dekorator logowania (cross-cutting concern):");
        LoggingDecorator c5 = new LoggingDecorator(
                new MilkDecorator(new SimpleCoffee()));
        printOrder(c5);
        System.out.println("  Log wywolan:");
        c5.getLog().forEach(entry -> System.out.println("    " + entry));

        System.out.println("\n6. Dekorator w JDK — BufferedReader:");
        System.out.println("  // Kazdy nowy dekorator dodaje funkcjonalnosc:");
        System.out.println("  FileReader fr = new FileReader(\"file.txt\");");
        System.out.println("  BufferedReader br = new BufferedReader(fr);  // buforowanie");
        System.out.println("  LineNumberReader lr = new LineNumberReader(br);  // numery linii");
        System.out.println("  // To samo co: new VanillaDecorator(new MilkDecorator(new Coffee()))");

        System.out.println("\n  Collections.unmodifiableList(list) — dekorator listy");
        List<String> mutable = new ArrayList<>(List.of("a", "b", "c"));
        List<String> readOnly = java.util.Collections.unmodifiableList(mutable);
        System.out.println("  Original: " + mutable);
        System.out.println("  ReadOnly: " + readOnly);
        try {
            readOnly.add("d");
        } catch (UnsupportedOperationException e) {
            System.out.println("  readOnly.add() → UnsupportedOperationException (poprawne!)");
        }
    }
}

