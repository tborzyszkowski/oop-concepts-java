package pakiety.t01;

/**
 * Pokazuje konwencję odwróconej domeny i znaczenie segmentów nazwy pakietu.
 * Uruchom: java pakiety.t01.ReverseDomainsDemo
 */
public class ReverseDomainsDemo {

    // Przykładowe pełne nazwy kwalifikowane (FQN) z ekosystemu Java
    private static final String[] REAL_WORLD_FQNS = {
        "com.google.gson.Gson",
        "org.springframework.web.servlet.DispatcherServlet",
        "org.apache.commons.lang3.StringUtils",
        "pl.edu.agh.oop.pakiety.t01.ReverseDomainsDemo"
    };

    public static void main(String[] args) {
        System.out.println("=== Konwencja odwróconej domeny ===");
        System.out.println();

        for (String fqn : REAL_WORLD_FQNS) {
            analyzePackage(fqn);
        }

        System.out.println();
        System.out.println("=== Porównanie: klasa z pakietem vs bez pakietu ===");
        System.out.println("Z pakietem:   pl.edu.demo.Student  →  import pl.edu.demo.Student");
        System.out.println("Bez pakietu:  Student              →  import niemożliwy z innego pakietu!");
    }

    private static void analyzePackage(String fqn) {
        String[] parts = fqn.split("\\.");
        String simpleName = parts[parts.length - 1];
        String packageName = fqn.substring(0, fqn.lastIndexOf('.'));

        System.out.printf("FQN      : %s%n", fqn);
        System.out.printf("  Klasa  : %s%n", simpleName);
        System.out.printf("  Pakiet : %s%n", packageName);
        System.out.printf("  Domena : %s.%s%n%n",
                parts[1], parts[0]);   // odwrócona domena = pierwsze dwa segmenty
    }
}

