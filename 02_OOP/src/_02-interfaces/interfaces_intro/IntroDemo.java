package interfaces_intro;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstruje:
 *  - polimorfizm przez interfejs (List<Printable>)
 *  - instanceof + pattern matching (Java 16+)
 *  - różne klasy niepowiązane hierarchią spełniające ten sam kontrakt
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_intro/*.java
 *   java -cp out interfaces_intro.IntroDemo
 */
public class IntroDemo {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // 1. Polimorfizm: Document i Photo traktowane jednakowo
        // -------------------------------------------------------
        List<Printable> queue = new ArrayList<>();
        queue.add(new Document("Raport kwartalny", "Wyniki za Q1 2026: +12% r/r"));
        queue.add(new Photo("wakacje.jpg", 4000, 3000));
        queue.add(new Document("Faktura FV/2026/001", "Kwota: 1230,00 PLN"));
        queue.add(new Photo("avatar.png", 256, 256));

        System.out.println("=== Kolejka wydruku ===");
        for (Printable item : queue) {
            item.print();   // wywołanie polimorficzne — każdy wie jak się wydrukować
        }

        // -------------------------------------------------------
        // 2. instanceof + pattern matching (Java 16+)
        // -------------------------------------------------------
        System.out.println("\n=== Statystyki kolejki ===");
        int docs = 0, photos = 0;
        for (Printable item : queue) {
            if (item instanceof Document d) {      // pattern matching: zmienna d od razu dostępna
                System.out.println("  Dokument: " + d.getTitle());
                docs++;
            } else if (item instanceof Photo) {
                photos++;
            }
        }
        System.out.println("Dokumenty: " + docs + ", Zdjęcia: " + photos);

        // -------------------------------------------------------
        // 3. Zmienna typu interfejsowego
        // -------------------------------------------------------
        System.out.println("\n=== Zmienna typu interfejsowego ===");
        Printable p = new Document("Test", "treść");  // OK — Document jest Printable
        p.print();
        // p.getTitle();  // BŁĄD KOMPILACJI: Printable nie ma metody getTitle()!
        System.out.println("Typ rzeczywisty: " + p.getClass().getSimpleName());
    }
}

