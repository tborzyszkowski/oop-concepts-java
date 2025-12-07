package solid.single_responsibility.after;

/**
 * Kalkulator wynagrodzeń - zgodnie z zasadą SRP.
 *
 * Jedyna odpowiedzialność: Obliczanie wynagrodzenia pracowników.
 * Jeden powód do zmiany: Zmiana zasad obliczania wynagrodzenia.
 *
 * Korzyści:
 * - Łatwe testowanie - nie wymaga bazy danych ani innych zależności
 * - Możliwość reużycia w różnych kontekstach
 * - Jasna separacja logiki biznesowej
 */
public class PayrollCalculator {

    /**
     * Oblicza wynagrodzenie pracownika na podstawie jego danych.
     *
     * Logika:
     * - Premia zależna od stanowiska
     * - Dodatek za staż (100 za każdy rok)
     *
     * @param employee pracownik
     * @return obliczone wynagrodzenie
     */
    public double calculatePay(Employee employee) {
        double basePay = employee.getSalary();

        // Premie zależne od stanowiska
        basePay = applyPositionBonus(basePay, employee.getPosition());

        // Dodatek za staż
        basePay = applyExperienceBonus(basePay, employee.getExperienceYears());

        return basePay;
    }

    /**
     * Stosuje premię zależną od stanowiska.
     */
    private double applyPositionBonus(double basePay, String position) {
        return switch (position) {
            case "Manager" -> basePay * 1.3;
            case "Senior Developer" -> basePay * 1.2;
            case "Developer" -> basePay * 1.1;
            default -> basePay;
        };
    }

    /**
     * Stosuje dodatek za lata doświadczenia.
     */
    private double applyExperienceBonus(double basePay, int years) {
        return basePay + (years * 100);
    }

    /**
     * Oblicza roczne wynagrodzenie.
     */
    public double calculateAnnualPay(Employee employee) {
        return calculatePay(employee) * 12;
    }

    /**
     * Oblicza miesięczną składkę na ubezpieczenie.
     */
    public double calculateInsurance(Employee employee) {
        return calculatePay(employee) * 0.15;
    }
}

