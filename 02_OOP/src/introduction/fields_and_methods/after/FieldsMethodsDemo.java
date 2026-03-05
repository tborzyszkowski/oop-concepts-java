package introduction.fields_and_methods.after;

/**
 * Demonstracja pól i metod: statycznych, publicznych i prywatnych.
 */
public class FieldsMethodsDemo {

    public static void main(String[] args) {

        System.out.println("=== PO refaktoryzacji — enkapsulacja ===");
        System.out.println();

        // ===== 1. Pola i metody statyczne =====
        System.out.println("--- 1. Metody statyczne (bez obiektu) ---");
        // Wywołanie PRZEZ KLASĘ — nie potrzeba obiektu
        System.out.println("MathUtils.square(7)      = " + MathUtils.square(7));
        System.out.println("MathUtils.factorial(5)   = " + MathUtils.factorial(5));
        System.out.println("MathUtils.isPrime(17)    = " + MathUtils.isPrime(17));
        System.out.println("MathUtils.circleArea(5)  = " + String.format("%.4f", MathUtils.circleArea(5)));
        System.out.println("MathUtils.clamp(150,0,100) = " + MathUtils.clamp(150, 0, 100));
        System.out.println("Stała PI = " + MathUtils.PI);

        System.out.println();
        System.out.println("--- 2. Pole statyczne instanceCount w Counter ---");

        // Przed stworzeniem jakiegokolwiek Counter-a
        System.out.println("Liczba instancji (przed): " + Counter.getTotalInstances()); // 0

        Counter c1 = new Counter("Zamówienia", 0, 10);
        Counter c2 = new Counter("Wizyta klientów", 0, 100);
        Counter c3 = new Counter("Błędy serwera", 0, 50);

        // Pole statyczne jest wspólne — zna wszystkie instancje
        System.out.println("Liczba instancji (po): " + Counter.getTotalInstances()); // 3

        System.out.println();
        System.out.println("--- 3. Prywatne pola — kontrolowany dostęp ---");

        c1.increment();
        c1.increment();
        c1.increment();
        System.out.println(c1);

        // Próba ustawienia nieprawidłowej wartości jest niemożliwa!
        // c1.count = -999;  // BŁĄD KOMPILACJI: count is private!
        // c1.minValue = 500; // BŁĄD KOMPILACJI

        System.out.println();
        System.out.println("--- 4. Walidacja w setterze ---");
        c1.setName("Nowe zamówienia");
        System.out.println("Nowa nazwa: " + c1.getName());

        try {
            c1.setName("");  // nieprawidłowa wartość
        } catch (IllegalArgumentException e) {
            System.out.println("Wyjątek przy pustej nazwie: " + e.getMessage());
        }

        System.out.println();
        System.out.println("--- 5. Ograniczenia zakresu (min/max) ---");

        Counter small = new Counter("Mini-licznik", 0, 3);
        for (int i = 0; i < 5; i++) {
            boolean ok = small.increment();
            System.out.println("increment() -> " + ok + " | " + small);
        }

        System.out.println();
        System.out.println("--- 6. Pola statyczne vs instancyjne ---");
        System.out.println("c1.getCount() = " + c1.getCount()    + "  (pole instancyjne c1)");
        System.out.println("c2.getCount() = " + c2.getCount()    + "  (pole instancyjne c2)");
        System.out.println("getTotalInstances() = " + Counter.getTotalInstances() + "  (pole STATYCZNE — wspólne)");
    }
}

