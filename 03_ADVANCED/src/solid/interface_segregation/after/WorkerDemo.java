package solid.interface_segregation.after;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstracja zastosowania ISP.
 */
public class WorkerDemo {

    public static void main(String[] args) {
        System.out.println("=== ROZWIĄZANIE: Zastosowanie ISP ===\n");

        Human human = new Human();
        Robot robot = new Robot();

        // Wszyscy pracują
        List<Workable> workers = Arrays.asList(human, robot);
        System.out.println("=== Praca ===");
        for (Workable worker : workers) {
            worker.work();
        }

        // Tylko ludzie jedzą i śpią
        System.out.println("\n=== Jedzenie ===");
        if (human instanceof Eatable) {
            ((Eatable) human).eat();
        }

        System.out.println("\n=== Sen ===");
        if (human instanceof Sleepable) {
            ((Sleepable) human).sleep();
        }

        System.out.println("\n=== KORZYŚCI ===");
        System.out.println("✅ Robot implementuje tylko Workable");
        System.out.println("✅ Brak pustych implementacji lub wyjątków");
        System.out.println("✅ Każdy interfejs jest mały i spójny");
        System.out.println("✅ Łatwe dodawanie nowych typów pracowników");
    }
}

