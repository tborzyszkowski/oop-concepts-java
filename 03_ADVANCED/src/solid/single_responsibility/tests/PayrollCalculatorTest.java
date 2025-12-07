package solid.single_responsibility.after;
}
    }
        assertEquals(15900.0, pay, 0.01);
        // Total: 15900
        // Experience: 15 * 100 = 1500
        // Base: 12000 * 1.2 = 14400
        // Then

        double pay = calculator.calculatePay(veteran);
        // When

        Employee veteran = new Employee("Veteran", "Senior Developer", 12000.0, 15);
        // Given
    void shouldHandleHighExperience() {
    @DisplayName("Should handle high experience")
    @Test

    }
        assertEquals(4400.0, pay, 0.01); // 4000 * 1.1 + 0
        // Then

        double pay = calculator.calculatePay(newbie);
        // When

        Employee newbie = new Employee("Newbie", "Developer", 4000.0, 0);
        // Given
    void shouldHandleZeroExperience() {
    @DisplayName("Should handle zero experience")
    @Test

    }
        assertEquals(855.0, insurance, 0.01);
        // Insurance: 5700 * 0.15 = 855
        // Pay: 5000 * 1.1 + 200 = 5700
        // Then

        double insurance = calculator.calculateInsurance(employee);
        // When

        Employee employee = new Employee("Test User", "Developer", 5000.0, 2);
        // Given
    void shouldCalculateInsurance() {
    @DisplayName("Should calculate insurance correctly")
    @Test

    }
        assertEquals(68400.0, annualPay, 0.01);
        // Annual: 5700 * 12 = 68400
        // Monthly: 5000 * 1.1 + 200 = 5700
        // Then

        double annualPay = calculator.calculateAnnualPay(employee);
        // When

        Employee employee = new Employee("Test User", "Developer", 5000.0, 2);
        // Given
    void shouldCalculateAnnualPay() {
    @DisplayName("Should calculate annual pay correctly")
    @Test

    }
        assertEquals(3000.0, pay, 0.01);
        // Total: 3000
        // Experience: 0 * 100 = 0
        // Base: 3000 (no bonus)
        // Then

        double pay = calculator.calculatePay(intern);
        // When

        Employee intern = new Employee("Alice Intern", "Intern", 3000.0, 0);
        // Given
    void shouldCalculatePayForUnknownPosition() {
    @DisplayName("Should calculate pay for unknown position without bonus")
    @Test

    }
        assertEquals(13800.0, pay, 0.01);
        // Total: 13800
        // Experience: 8 * 100 = 800
        // Base: 10000 * 1.3 (Manager bonus) = 13000
        // Then

        double pay = calculator.calculatePay(manager);
        // When

        Employee manager = new Employee("Bob Manager", "Manager", 10000.0, 8);
        // Given
    void shouldCalculatePayForManager() {
    @DisplayName("Should calculate pay for Manager")
    @Test

    }
        assertEquals(10100.0, pay, 0.01);
        // Total: 10100
        // Experience: 5 * 100 = 500
        // Base: 8000 * 1.2 (Senior Developer bonus) = 9600
        // Then

        double pay = calculator.calculatePay(seniorDev);
        // When

        Employee seniorDev = new Employee("Jane Smith", "Senior Developer", 8000.0, 5);
        // Given
    void shouldCalculatePayForSeniorDeveloper() {
    @DisplayName("Should calculate pay for Senior Developer")
    @Test

    }
        assertEquals(5800.0, pay, 0.01);
        // Total: 5800
        // Experience: 3 * 100 = 300
        // Base: 5000 * 1.1 (Developer bonus) = 5500
        // Then

        double pay = calculator.calculatePay(developer);
        // When

        Employee developer = new Employee("John Doe", "Developer", 5000.0, 3);
        // Given
    void shouldCalculatePayForDeveloper() {
    @DisplayName("Should calculate pay for Developer with base salary")
    @Test

    }
        calculator = new PayrollCalculator();
    void setUp() {
    @BeforeEach

    private PayrollCalculator calculator;

class PayrollCalculatorTest {
@DisplayName("PayrollCalculator Tests")
 */
 * bez potrzeby mockowania bazy danych czy generatorów raportów.
 * Dzięki SRP, PayrollCalculator może być testowany niezależnie,
 *
 * Testy jednostkowe dla PayrollCalculator.
/**

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


