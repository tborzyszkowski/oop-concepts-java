package object_lifecycle.basic;

/**
 * Demonstracja konstruktorów i kolejności inicjalizacji obiektów.
 */
public class PersonDemo {

    public static void main(String[] args) {

        System.out.println("=== Demonstracja konstruktorów i cyklu życia obiektów ===");
        System.out.println();

        // --- Kolejność inicjalizacji ---
        System.out.println("--- 1. Kolejność inicjalizacji przy tworzeniu obiektu ---");
        System.out.println("Tworzenie: new Person() ...");
        System.out.println();
        Person p1 = new Person();
        System.out.println("Wynik: " + p1);

        System.out.println();
        System.out.println("--- 2. Konstruktor z parametrami ---");
        Person p2 = new Person("Anna", "Kowalska", 30);
        p2.setEmail("anna@example.com");
        System.out.println("Wynik: " + p2);

        System.out.println();
        System.out.println("--- 3. Konstruktor delegujący this(...) ---");
        System.out.println("Tworzenie: new Person(\"Jan\", \"Nowak\")");
        Person p3 = new Person("Jan", "Nowak");
        System.out.println("Wynik: " + p3);

        System.out.println();
        System.out.println("--- 4. Konstruktor kopiujący ---");
        System.out.println("Tworzenie: new Person(p2) — kopia p2");
        Person p4 = new Person(p2);
        System.out.println("Kopia: " + p4);
        System.out.println("Oryginał: " + p2);

        // Modyfikacja kopii nie wpływa na oryginał (shallow copy dla String jest OK — String jest immutable)
        p4.setAge(25);
        System.out.println("Po zmianie wieku kopii:");
        System.out.println("  Kopia: " + p4.getAge());
        System.out.println("  Oryginał: " + p2.getAge());

        System.out.println();
        System.out.println("--- 5. Walidacja w setterze ---");
        try {
            p2.setAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Wyjątek: " + e.getMessage());
        }

        System.out.println();
        System.out.println("--- 6. Zasięg zmiennej i czas życia (scope) ---");
        demonstrateScope();
    }

    private static void demonstrateScope() {
        // Zmienna p istnieje tylko w tym bloku
        {
            Person tempPerson = new Person("Tymczasowy", "Obiekt", 1);
            System.out.println("Wewnątrz bloku: " + tempPerson.getFullName());
        }
        // tempPerson nie istnieje tutaj!
        // Po wyjściu z bloku — obiekt jest kandydatem do GC (brak referencji)
        System.out.println("Po bloku: zmienna tempPerson niewidoczna — obiekt może być usunięty przez GC");

        for (int i = 0; i < 3; i++) {
            // person tworzone i "tracone" w każdej iteracji
            Person loopPerson = new Person("Pętla", "Nr" + i, i);
            System.out.println("Iteracja " + i + ": " + loopPerson.getFullName());
        }
        // Wszystkie trzy obiekty z pętli są kandydatami do GC
    }
}

