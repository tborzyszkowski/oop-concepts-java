package solid.single_responsibility.after;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Testy jednostkowe dla EmployeeRepository.
 *
 * Dzięki SRP, EmployeeRepository może być testowany niezależnie,
 * koncentrując się tylko na logice persystencji.
 */
@DisplayName("EmployeeRepository Tests")
class EmployeeRepositoryTest {

    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
    }

    @Test
    @DisplayName("Should save and find employee by name")
    void shouldSaveAndFindEmployee() {
        // Given
        Employee employee = new Employee("John Doe", "Developer", 5000.0, 3);

        // When
        repository.save(employee);
        Employee found = repository.findByName("John Doe");

        // Then
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
        assertEquals("Developer", found.getPosition());
        assertEquals(5000.0, found.getSalary());
        assertEquals(3, found.getExperienceYears());
    }

    @Test
    @DisplayName("Should return null when employee not found")
    void shouldReturnNullWhenNotFound() {
        // When
        Employee found = repository.findByName("Non Existent");

        // Then
        assertNull(found);
    }

    @Test
    @DisplayName("Should save multiple employees")
    void shouldSaveMultipleEmployees() {
        // Given
        Employee emp1 = new Employee("John Doe", "Developer", 5000.0, 3);
        Employee emp2 = new Employee("Jane Smith", "Manager", 8000.0, 5);

        // When
        repository.save(emp1);
        repository.save(emp2);

        // Then
        assertEquals(2, repository.count());
    }

    @Test
    @DisplayName("Should find all employees")
    void shouldFindAllEmployees() {
        // Given
        Employee emp1 = new Employee("John Doe", "Developer", 5000.0, 3);
        Employee emp2 = new Employee("Jane Smith", "Manager", 8000.0, 5);
        repository.save(emp1);
        repository.save(emp2);

        // When
        List<Employee> all = repository.findAll();

        // Then
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(e -> e.getName().equals("John Doe")));
        assertTrue(all.stream().anyMatch(e -> e.getName().equals("Jane Smith")));
    }

    @Test
    @DisplayName("Should delete employee")
    void shouldDeleteEmployee() {
        // Given
        Employee employee = new Employee("John Doe", "Developer", 5000.0, 3);
        repository.save(employee);
        assertEquals(1, repository.count());

        // When
        repository.delete("John Doe");

        // Then
        assertEquals(0, repository.count());
        assertNull(repository.findByName("John Doe"));
    }

    @Test
    @DisplayName("Should update employee when saving with same name")
    void shouldUpdateEmployee() {
        // Given
        Employee original = new Employee("John Doe", "Developer", 5000.0, 3);
        repository.save(original);

        // When
        Employee updated = new Employee("John Doe", "Senior Developer", 8000.0, 5);
        repository.save(updated);

        // Then
        assertEquals(1, repository.count());
        Employee found = repository.findByName("John Doe");
        assertEquals("Senior Developer", found.getPosition());
        assertEquals(8000.0, found.getSalary());
    }

    @Test
    @DisplayName("Should return zero count for empty repository")
    void shouldReturnZeroCountForEmpty() {
        // When & Then
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should return empty list when no employees")
    void shouldReturnEmptyListWhenNoEmployees() {
        // When
        List<Employee> all = repository.findAll();

        // Then
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }
}

