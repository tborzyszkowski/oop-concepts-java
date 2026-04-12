package _02_interfaces.interfaces_intro;

/**
 * Demonstruje koncepcję "interfejs = kontrakt":
 * klasy z zupełnie różnych hierarchii mogą spełniać ten sam kontrakt.
 *
 * Przykład: Zarówno Robot, jak i człowiek (HumanEmployee) mogą "pracować".
 * Nie są powiązane dziedziczeniem — łączy je tylko interfejs Worker.
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_intro/ContractDemo.java
 *   java -cp out interfaces_intro.ContractDemo
 */
public class ContractDemo {

    /** Kontrakt: "potrafi pracować" — niezależny od hierarchii klas. */
    interface Worker {
        void work();
        default String getDescription() {
            return "Pracownik bez opisu";
        }
    }

    /** Klasa z hierarchii "maszyna" */
    static class Robot implements Worker {
        private final String model;
        Robot(String model) { this.model = model; }

        @Override public void work() {
            System.out.println("Robot " + model + ": spawam element nr " + (int)(Math.random()*1000));
        }
        @Override public String getDescription() { return "Robot przemysłowy " + model; }
    }

    /** Klasa z hierarchii "człowiek" */
    static class HumanEmployee implements Worker {
        private final String name;
        HumanEmployee(String name) { this.name = name; }

        @Override public void work() {
            System.out.println(name + ": piszę kod źródłowy");
        }
        @Override public String getDescription() { return "Pracownik: " + name; }
    }

    /** Klasa z hierarchii "zewnętrzny dostawca" */
    static class FreelanceContractor implements Worker {
        private final String company;
        FreelanceContractor(String company) { this.company = company; }

        @Override public void work() {
            System.out.println("Freelancer (" + company + "): dostarczam raport");
        }
    }

    public static void main(String[] args) {
        Worker[] team = {
            new Robot("KUKA-KR6"),
            new HumanEmployee("Anna Kowalska"),
            new HumanEmployee("Jan Nowak"),
            new FreelanceContractor("DevStudio Sp. z o.o.")
        };

        System.out.println("=== Poranny stand-up ===");
        for (Worker w : team) {
            System.out.println("  [" + w.getDescription() + "]");
            w.work();
        }

        // Interfejs jako typ — polimorfizm
        System.out.println("\n=== Zlecenie pracy wszystkim ===");
        for (Worker w : team) w.work();
    }
}

