package solid.single_responsibility.before;

/**
 * Demonstracja problemu z naruszeniem zasady SRP.
 *
 * Klasa Employee ma zbyt wiele odpowiedzialności, co prowadzi do:
 * - Trudności w testowaniu (trzeba mockować bazę danych)
 * - Trudności w utrzymaniu (różne powody zmian)
 * - Niskiej reużywalności (nie można użyć samej logiki obliczania płacy)
 * - Wysokiego sprzężenia (wszystko w jednej klasie)
 */
public class EmployeeDemo {

    public static void main(String[] args) {
        System.out.println("=== PROBLEM: Naruszenie SRP ===\n");

        // Tworzenie pracownika
        Employee employee1 = new Employee(
            "Jan Kowalski",
            "Senior Developer",
            8000.0,
            5
        );

        Employee employee2 = new Employee(
            "Anna Nowak",
            "Manager",
            10000.0,
            8
        );

        // Wszystkie operacje przez jedną klasę
        demonstrateProblems(employee1);
        System.out.println();
        demonstrateProblems(employee2);

        System.out.println("\n=== PROBLEMY ===");
        System.out.println("❌ Klasa Employee ma 4 różne odpowiedzialności");
        System.out.println("❌ Trudne testowanie - wymaga mockowania bazy danych");
        System.out.println("❌ Zmiana formatu raportu wymaga modyfikacji klasy Employee");
        System.out.println("❌ Zmiana logiki obliczania płacy wpływa na całą klasę");
        System.out.println("❌ Nie można reużyć samej logiki obliczania płacy");
        System.out.println("❌ Wysokie sprzężenie - wszystko w jednym miejscu");
    }

    private static void demonstrateProblems(Employee employee) {
        System.out.println("Pracownik: " + employee.getName());
        System.out.println("Stanowisko: " + employee.getPosition());

        // Problem 1: Obliczanie wynagrodzenia zmiksowane z logiką klasy
        System.out.println("Obliczone wynagrodzenie: $" + employee.calculatePay());

        // Problem 2: Zapis do bazy wymaga dostępu do całego obiektu Employee
        employee.save();

        // Problem 3: Generowanie raportów również w klasie Employee
        System.out.println("\nRaport tekstowy:");
        System.out.println(employee.generateReport());

        // Problem 4: Jeszcze jeden format raportowania
        System.out.println("Raport JSON:");
        System.out.println(employee.toJson());

        System.out.println("\n" + "─".repeat(50));
    }
}

