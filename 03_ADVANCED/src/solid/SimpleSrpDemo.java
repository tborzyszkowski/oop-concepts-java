// Proste demo SRP - bez pakietów dla łatwego testowania

public class SimpleSrpDemo {
    public static void main(String[] args) {
        System.out.println("=== SRP Demo ===");

        Employee emp = new Employee("Jan Kowalski", 5000.0);
        PayrollCalculator calc = new PayrollCalculator();
        EmployeeRepository repo = new EmployeeRepository();

        System.out.println("Employee: " + emp.getName());
        System.out.println("Pay: " + calc.calculatePay(emp));
        repo.save(emp);

        System.out.println("\n✅ Each class has ONE responsibility!");
    }
}

class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }
}

class PayrollCalculator {
    public double calculatePay(Employee employee) {
        return employee.getSalary() * 1.2;
    }
}

class EmployeeRepository {
    public void save(Employee employee) {
        System.out.println("Saving: " + employee.getName());
    }
}

