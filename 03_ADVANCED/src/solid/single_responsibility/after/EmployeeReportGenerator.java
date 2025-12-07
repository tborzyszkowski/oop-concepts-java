package solid.single_responsibility.after;

import java.util.List;

/**
 * Generator raportów pracowników - zgodnie z zasadą SRP.
 *
 * Jedyna odpowiedzialność: Generowanie raportów dla pracowników.
 * Jeden powód do zmiany: Zmiana formatu raportów.
 *
 * Korzyści:
 * - Łatwa zmiana formatu raportów
 * - Możliwość dodawania nowych formatów
 * - Nie wpływa na inne klasy
 */
public class EmployeeReportGenerator {
    private final PayrollCalculator payrollCalculator;

    public EmployeeReportGenerator(PayrollCalculator payrollCalculator) {
        this.payrollCalculator = payrollCalculator;
    }

    /**
     * Generuje raport tekstowy dla pracownika.
     */
    public String generateReport(Employee employee) {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════\n");
        report.append("         EMPLOYEE REPORT\n");
        report.append("═══════════════════════════════════════\n");
        report.append("Name:            ").append(employee.getName()).append("\n");
        report.append("Position:        ").append(employee.getPosition()).append("\n");
        report.append("Base Salary:     $").append(String.format("%.2f", employee.getSalary())).append("\n");
        report.append("Experience:      ").append(employee.getExperienceYears()).append(" years\n");
        report.append("Calculated Pay:  $").append(String.format("%.2f",
            payrollCalculator.calculatePay(employee))).append("\n");
        report.append("Annual Pay:      $").append(String.format("%.2f",
            payrollCalculator.calculateAnnualPay(employee))).append("\n");
        report.append("═══════════════════════════════════════\n");
        return report.toString();
    }

    /**
     * Generuje raport w formacie JSON dla pracownika.
     */
    public String generateJsonReport(Employee employee) {
        double calculatedPay = payrollCalculator.calculatePay(employee);
        double annualPay = payrollCalculator.calculateAnnualPay(employee);

        return "{\n" +
               "  \"name\": \"" + employee.getName() + "\",\n" +
               "  \"position\": \"" + employee.getPosition() + "\",\n" +
               "  \"baseSalary\": " + employee.getSalary() + ",\n" +
               "  \"experienceYears\": " + employee.getExperienceYears() + ",\n" +
               "  \"calculatedPay\": " + String.format("%.2f", calculatedPay) + ",\n" +
               "  \"annualPay\": " + String.format("%.2f", annualPay) + "\n" +
               "}";
    }

    /**
     * Generuje raport CSV dla pracownika.
     */
    public String generateCsvReport(Employee employee) {
        double calculatedPay = payrollCalculator.calculatePay(employee);
        return String.format("%s,%s,%.2f,%d,%.2f",
            employee.getName(),
            employee.getPosition(),
            employee.getSalary(),
            employee.getExperienceYears(),
            calculatedPay
        );
    }

    /**
     * Generuje nagłówek CSV.
     */
    public String getCsvHeader() {
        return "Name,Position,Base Salary,Experience Years,Calculated Pay";
    }

    /**
     * Generuje zbiorczy raport dla wszystkich pracowników.
     */
    public String generateSummaryReport(List<Employee> employees) {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════\n");
        report.append("      EMPLOYEES SUMMARY REPORT\n");
        report.append("═══════════════════════════════════════\n");
        report.append("Total Employees: ").append(employees.size()).append("\n\n");

        double totalPay = 0;
        for (Employee employee : employees) {
            double pay = payrollCalculator.calculatePay(employee);
            totalPay += pay;
            report.append(String.format("%-20s %-20s $%.2f\n",
                employee.getName(),
                employee.getPosition(),
                pay));
        }

        report.append("\n");
        report.append("Total Monthly Payroll: $").append(String.format("%.2f", totalPay)).append("\n");
        report.append("Average Salary:        $").append(
            String.format("%.2f", employees.isEmpty() ? 0 : totalPay / employees.size())).append("\n");
        report.append("═══════════════════════════════════════\n");

        return report.toString();
    }
}

