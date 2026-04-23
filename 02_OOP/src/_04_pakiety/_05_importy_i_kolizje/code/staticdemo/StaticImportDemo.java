package _04_pakiety._05_importy_i_kolizje.code.staticdemo;

// Import static — importuje konkretną stałą lub metodę statyczną bez prefiksu klasy
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstruje import static:
 *  - Math.PI, Math.sqrt, Math.pow — bez prefiksu "Math."
 *  - Collections.sort / unmodifiableList — bez prefiksu "Collections."
 *
 * Bez import static:
 *   double obwod = 2 * Math.PI * r;
 *   double x = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
 *
 * Z import static:
 *   double obwod = 2 * PI * r;
 *   double x = sqrt(pow(a, 2) + pow(b, 2));
 */
public class StaticImportDemo {

    public static void main(String[] args) {
        // --- Matematyka ---
        double r = 5.0;
        double obwod  = 2 * PI * r;
        double pole   = PI * pow(r, 2);

        System.out.printf("Promień  : %.1f%n", r);
        System.out.printf("Obwód    : %.4f%n", obwod);
        System.out.printf("Pole     : %.4f%n", pole);

        double a = 3, b = 4;
        double c = sqrt(pow(a, 2) + pow(b, 2));
        System.out.printf("Hipotenu : %.1f (twierdzenie Pitagorasa 3-4-5)%n%n", c);

        // --- Kolekcje ---
        List<String> languages = new ArrayList<>(List.of("Java", "Kotlin", "Scala", "Clojure"));
        sort(languages);   // Collections.sort bez prefiksu
        System.out.println("Jezyki (sortowane): " + languages);

        List<String> readOnly = unmodifiableList(languages);
        System.out.println("Tylko do odczytu:   " + readOnly);
        try {
            readOnly.add("Groovy");
        } catch (UnsupportedOperationException e) {
            System.out.println("Proba modyfikacji: UnsupportedOperationException (oczekiwane)");
        }
    }
}

