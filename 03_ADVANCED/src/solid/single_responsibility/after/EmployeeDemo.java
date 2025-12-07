package solid.single_responsibility.after;

import java.util.List;

/**
 * Demonstracja rozwiązania z zastosowaniem zasady SRP.
 *
 * Każda klasa ma jedną, dobrze zdefiniowaną odpowiedzialność:
 * - Employee: reprezentacja danych
 * - PayrollCalculator: logika obliczania wynagrodzeń
 * - EmployeeRepository: persystencja danych
 * - EmployeeReportGenerator: generowanie raportów
 *
 * Korzyści:
 * - Łatwe testowanie każdej klasy osobno
 * - Możliwość reużycia poszczególnych komponentów
 * - Łatwe wprowadzanie zmian (tylko w jednej klasie)
 * - Niskie sprzężenie między komponentami
 */
public class EmployeeDemo {

    public static void main(String[] args) {
        System.out.println("=== ROZWIĄZANIE: Zastosowanie SRP ===\n");

        // Tworzenie zależności (dependency injection)
        PayrollCalculator payrollCalculator = new PayrollCalculator();
        EmployeeRepository repository = new EmployeeRepository();
        EmployeeReportGenerator reportGenerator = new EmployeeReportGenerator(payrollCalculator);

        // Tworzenie pracowników
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

        Employee employee3 = new Employee(
            "Piotr Wiśniewski",
            "Developer",
            6000.0,
            2
        );

        System.out.println("=== 1. Operacje na repozytorium ===");
        repository.save(employee1);
        System.out.println();
        repository.save(employee2);
        System.out.println();
        repository.save(employee3);
        System.out.println();

        System.out.println("Liczba pracowników w bazie: " + repository.count());
        System.out.println("\n" + "─".repeat(50) + "\n");

        System.out.println("=== 2. Obliczanie wynagrodzeń ===");
        demonstratePayrollCalculation(employee1, payrollCalculator);
        System.out.println();
        demonstratePayrollCalculation(employee2, payrollCalculator);
        System.out.println("\n" + "─".repeat(50) + "\n");

        System.out.println("=== 3. Generowanie raportów ===");
        System.out.println("Raport tekstowy:");
        System.out.println(reportGenerator.generateReport(employee1));

        System.out.println("Raport JSON:");
        System.out.println(reportGenerator.generateJsonReport(employee2));
        System.out.println();

        System.out.println("Raport CSV:");
        System.out.println(reportGenerator.getCsvHeader());
        System.out.println(reportGenerator.generateCsvReport(employee3));
        System.out.println("\n" + "─".repeat(50) + "\n");

        System.out.println("=== 4. Raport zbiorczy ===");
        List<Employee> allEmployees = repository.findAll();
        System.out.println(reportGenerator.generateSummaryReport(allEmployees));

        System.out.println("=== KORZYŚCI ===");
        System.out.println("✅ Każda klasa ma jedną odpowiedzialność");
        System.out.println("✅ Łatwe testowanie - każda klasa osobno");
        System.out.println("✅ Zmiana formatu raportu nie wpływa na inne klasy");
        System.out.println("✅ Logika obliczania płacy może być reużyta");
        System.out.println("✅ Niskie sprzężenie - komponenty niezależne");
        System.out.println("✅ Wysoka spójność - każda klasa robi jedną rzecz dobrze");
    }

    private static void demonstratePayrollCalculation(Employee employee, PayrollCalculator calculator) {
        System.out.println("Pracownik: " + employee.getName());
        System.out.println("Stanowisko: " + employee.getPosition());
        System.out.println("Płaca podstawowa: $" + employee.getSalary());
        System.out.println("Doświadczenie: " + employee.getExperienceYears() + " lat");
        System.out.println("Obliczona płaca: $" + String.format("%.2f", calculator.calculatePay(employee)));
        System.out.println("Płaca roczna: $" + String.format("%.2f", calculator.calculateAnnualPay(employee)));
        System.out.println("Ubezpieczenie: $" + String.format("%.2f", calculator.calculateInsurance(employee)));
    }
}

