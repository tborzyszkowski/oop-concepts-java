package solid.single_responsibility.after;
}
    }
               '}';
               ", experienceYears=" + experienceYears +
               ", salary=" + salary +
               ", position='" + position + '\'' +
               "name='" + name + '\'' +
        return "Employee{" +
    public String toString() {
    @Override

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

    }
        this.experienceYears = experienceYears;
        this.salary = salary;
        this.position = position;
        this.name = name;
    public Employee(String name, String position, double salary, int experienceYears) {

    private final int experienceYears;
    private final double salary;
    private final String position;
    private final String name;
public class Employee {
 */
 * Jeden powód do zmiany: Zmiana struktury danych pracownika.
 * Jedyna odpowiedzialność: Reprezentacja danych pracownika.
 *
 * Model danych pracownika - zgodnie z zasadą SRP.
/**


