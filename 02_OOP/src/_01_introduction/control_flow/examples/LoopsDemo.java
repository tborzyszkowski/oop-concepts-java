package control_flow.examples;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Demonstracja pętli w Javie: for, while, do-while, for-each, Iterator.
 * Zawiera: break, continue, labeled break.
 */
public class LoopsDemo {

    public static void main(String[] args) {

        System.out.println("=== Pętle w Javie ===");
        System.out.println();

        // ===== for — klasyczna pętla z licznikiem =====
        System.out.println("--- 1. Pętla for (klasyczna) ---");
        for (int i = 0; i < 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Pętla for wstecz
        System.out.print("Wstecz: ");
        for (int i = 4; i >= 0; i--) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Pętla for z krokiem 2
        System.out.print("Co 2: ");
        for (int i = 0; i < 20; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();

        // ===== while =====
        System.out.println();
        System.out.println("--- 2. Pętla while ---");
        int n = 1;
        System.out.print("Potęgi 2: ");
        while (n <= 1024) {
            System.out.print(n + " ");
            n *= 2;
        }
        System.out.println();

        // ===== do-while — warunek sprawdzany NA KOŃCU =====
        System.out.println();
        System.out.println("--- 3. Pętla do-while (co najmniej jeden przebieg) ---");
        int x = 10;
        do {
            System.out.println("Wykonanie: x=" + x);
            x++;
        } while (x < 5); // warunek FALSE od razu, ale pętla wykonała się raz!
        System.out.println("(pętla while z tym samym warunkiem nie wykonałaby się wcale)");

        // ===== for-each — dla tablic i kolekcji =====
        System.out.println();
        System.out.println("--- 4. Pętla for-each (enhanced for) ---");
        int[] numbers = {10, 20, 30, 40, 50};
        int sum = 0;
        for (int num : numbers) {  // "dla każdego num w numbers"
            sum += num;
            System.out.print(num + " ");
        }
        System.out.println("\nSuma = " + sum);

        List<String> fruits = Arrays.asList("Jabłko", "Banan", "Wiśnia", "Daktyl");
        System.out.println("Owoce:");
        for (String fruit : fruits) {
            System.out.println("  - " + fruit);
        }

        // ===== Iterator =====
        System.out.println();
        System.out.println("--- 5. Iterator (styl explicite) ---");
        Iterator<String> it = fruits.iterator();
        while (it.hasNext()) {
            String fruit = it.next();
            System.out.println("  Iterator: " + fruit);
        }

        // ===== break i continue =====
        System.out.println();
        System.out.println("--- 6. break i continue ---");

        System.out.print("break przy i=5: ");
        for (int i = 0; i < 10; i++) {
            if (i == 5) break;   // wychodzi z pętli
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("continue (pomiń parzyste): ");
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) continue;  // przechodzi do następnej iteracji
            System.out.print(i + " ");
        }
        System.out.println();

        // ===== Labeled break — wychodzi z zewnętrznej pętli =====
        System.out.println();
        System.out.println("--- 7. Labeled break (wyjście z zagnieżdżonych pętli) ---");
        outer:                       // etykieta
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i + j == 6) {
                    System.out.println("Znaleziono: i=" + i + ", j=" + j);
                    break outer;    // wychodzi z ZEWNĘTRZNEJ pętli
                }
            }
        }
        System.out.println("Po labeled break");

        // ===== Nieskończona pętla z break =====
        System.out.println();
        System.out.println("--- 8. Nieskończona pętla z break ---");
        int counter = 0;
        while (true) {
            counter++;
            if (counter >= 5) break;
        }
        System.out.println("Licznik osiągnął: " + counter);
    }
}

