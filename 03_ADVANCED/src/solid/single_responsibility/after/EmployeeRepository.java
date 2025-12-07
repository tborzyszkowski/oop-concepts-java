package solid.single_responsibility.after;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Repozytorium pracowników - zgodnie z zasadą SRP.
 *
 * Jedyna odpowiedzialność: Zarządzanie persystencją danych pracowników.
 * Jeden powód do zmiany: Zmiana sposobu przechowywania danych.
 *
 * Korzyści:
 * - Izolacja logiki dostępu do danych
 * - Łatwa zmiana technologii bazy danych
 * - Możliwość mockowania w testach
 */
public class EmployeeRepository {
    // Symulacja bazy danych
    private final Map<String, Employee> database = new HashMap<>();

    /**
     * Zapisuje pracownika do bazy danych.
     */
    public void save(Employee employee) {
        System.out.println("Connecting to database...");
        System.out.println("Saving employee: " + employee.getName());

        database.put(employee.getName(), employee);

        System.out.println("SQL: INSERT INTO employees (name, position, salary, experience) VALUES ('"
            + employee.getName() + "', '"
            + employee.getPosition() + "', "
            + employee.getSalary() + ", "
            + employee.getExperienceYears() + ")");
        System.out.println("Employee saved successfully!");
    }

    /**
     * Znajduje pracownika po nazwisku.
     */
    public Employee findByName(String name) {
        System.out.println("Searching for employee: " + name);
        Employee employee = database.get(name);

        if (employee == null) {
            System.out.println("Employee not found!");
        } else {
            System.out.println("Employee found: " + employee);
        }

        return employee;
    }

    /**
     * Zwraca wszystkich pracowników.
     */
    public List<Employee> findAll() {
        System.out.println("Fetching all employees from database...");
        return new ArrayList<>(database.values());
    }

    /**
     * Usuwa pracownika.
     */
    public void delete(String name) {
        System.out.println("Deleting employee: " + name);
        database.remove(name);
        System.out.println("SQL: DELETE FROM employees WHERE name = '" + name + "'");
        System.out.println("Employee deleted successfully!");
    }

    /**
     * Zwraca liczbę pracowników w bazie.
     */
    public int count() {
        return database.size();
    }
}

