package solid.single_responsibility.before;
}
    }
               "}";
               "  \"calculatedPay\": " + calculatePay() + "\n" +
               "  \"experienceYears\": " + experienceYears + ",\n" +
               "  \"salary\": " + salary + ",\n" +
               "  \"position\": \"" + position + "\",\n" +
               "  \"name\": \"" + name + "\",\n" +
        return "{\n" +
    public String toJson() {
    // ODPOWIEDZIALNOŚĆ 4: Dodatkowe raportowanie - generowanie JSON

    }
        return report.toString();
        report.append("═══════════════════════════════════════\n");
        report.append("Calculated Pay:  $").append(String.format("%.2f", calculatePay())).append("\n");
        report.append("Experience:      ").append(experienceYears).append(" years\n");
        report.append("Base Salary:     $").append(String.format("%.2f", salary)).append("\n");
        report.append("Position:        ").append(position).append("\n");
        report.append("Name:            ").append(name).append("\n");
        report.append("═══════════════════════════════════════\n");
        report.append("         EMPLOYEE REPORT\n");
        report.append("═══════════════════════════════════════\n");
        StringBuilder report = new StringBuilder();
    public String generateReport() {
    // ODPOWIEDZIALNOŚĆ 3: Raportowanie - generowanie raportu

    }
        System.out.println("Employee saved successfully!");
            + name + "', '" + position + "', " + salary + ")");
        System.out.println("SQL: INSERT INTO employees (name, position, salary) VALUES ('"
        System.out.println("Saving employee: " + name);
        System.out.println("Connecting to database...");
    public void save() {
    // ODPOWIEDZIALNOŚĆ 2: Persystencja - zapis do bazy danych

    }
        return basePay;

        basePay += experienceYears * 100;
        // Dodatek za staż

        }
            basePay *= 1.1;
        } else if (position.equals("Developer")) {
            basePay *= 1.2;
        } else if (position.equals("Senior Developer")) {
            basePay *= 1.3;
        if (position.equals("Manager")) {
        // Premie zależne od stanowiska

        double basePay = salary;
    public double calculatePay() {
    // ODPOWIEDZIALNOŚĆ 1: Logika biznesowa - obliczanie wynagrodzenia

    }
        return experienceYears;
    public int getExperienceYears() {

    }
        return salary;
    public double getSalary() {

    }
        return position;
    public String getPosition() {

    }
        return name;
    public String getName() {
    // Gettery i settery

    }
        this.experienceYears = experienceYears;
        this.salary = salary;
        this.position = position;
        this.name = name;
    public Employee(String name, String position, double salary, int experienceYears) {

    private int experienceYears;
    private double salary;
    private String position;
    private String name;
public class Employee {
 */
 * - Zmiana formatu raportów
 * - Zmiana technologii bazy danych
 * - Zmiana zasad obliczania wynagrodzenia
 * - Zmiana struktury danych pracownika
 * Powody do zmiany:
 *
 * 4. Raportowanie (generowanie raportów)
 * 3. Persystencja danych (zapis do bazy)
 * 2. Obliczanie wynagrodzenia (logika biznesowa)
 * 1. Reprezentacja danych pracownika
 * Ta klasa ma zbyt wiele odpowiedzialności:
 *
 * Przykład naruszenia zasady Single Responsibility Principle.
/**


