package _10_wzorce._01_wprowadzenie.code;

/**
 * Wprowadzenie do wzorców projektowych GoF.
 * Demonstruje problem "telefonujacego konstruktora" i inne typowe problemy,
 * ktore wzorce rozwiazuja.
 */
public class PatternsIntroDemo {

    public static void main(String[] args) {
        System.out.println("=== Wzorce projektowe GoF — Wprowadzenie ===\n");

        System.out.println("1. Problem: wiele konfiguracji obiektu");
        showTelescoping();

        System.out.println("\n2. Problem: zaleznosc od konkretnej implementacji");
        showDependency();

        System.out.println("\n3. Problem: brak powiadamiania o zmianach");
        showObservationProblem();

        System.out.println("\n\nWzorce GoF rozwiazuja te i wiele innych problemow.");
        System.out.println("Omowione w tym module:");
        String[] patterns = {
            "Kreacyjne:   Singleton, Factory Method, Abstract Factory, Builder",
            "Strukturalne: Adapter, Decorator, Facade, Proxy, Composite",
            "Behawioralne: Observer, Strategy, Template Method, Command"
        };
        for (String p : patterns) System.out.println("  " + p);
    }

    static void showTelescoping() {
        // "Teleskopujacy konstruktor" - anty-wzorzec
        // Im wiecej opcji, tym wiecej przeciazen
        System.out.println("  // Anty-wzorzec: Pizza(size, crust, sauce, topping1, topping2, ...)");
        System.out.println("  Pizza p1 = new Pizza(\"large\", \"thin\", \"tomato\", null, null, false);");
        System.out.println("  Pizza p2 = new Pizza(\"medium\", \"thick\", \"bbq\", \"chicken\", \"bacon\", true);");
        System.out.println("  // Rozwiazanie: Builder pattern");
    }

    static void showDependency() {
        System.out.println("  // Anty-wzorzec: zaleznosc od konkretnej klasy");
        System.out.println("  MySqlDatabase db = new MySqlDatabase(); // twardo zakodowane!");
        System.out.println("  // Rozwiazanie: Factory Method / Abstract Factory / DI");
    }

    static void showObservationProblem() {
        System.out.println("  // Anty-wzorzec: polling co sekunde");
        System.out.println("  while (true) { if (stock.hasChanged()) update(); Thread.sleep(1000); }");
        System.out.println("  // Rozwiazanie: Observer / EventListener");
    }
}

