package introduction.exercises.tasks;

/**
 * ZADANIE 4: TDD — Kalkulator w stylu Red-Green-Refactor
 * ========================================================
 * Poziom: Średni — oparty na module "tdd"
 *
 * POLECENIE:
 *   Zaimplementuj klasę Calculator metodą TDD.
 *   NAJPIERW napisz testy (CalcTest.java), POTEM implementację.
 *
 * WYMAGANIA FUNKCJONALNE:
 *
 *   Klasa Calculator:
 *     - add(double a, double b) : double
 *     - subtract(double a, double b) : double
 *     - multiply(double a, double b) : double
 *     - divide(double a, double b) : double
 *         → rzuca ArithmeticException dla b == 0
 *     - power(double base, int exponent) : double
 *         → obsługuje ujemne wykładniki: power(2, -1) = 0.5
 *     - sqrt(double n) : double
 *         → rzuca IllegalArgumentException dla n < 0
 *     - getHistory() : List<String>
 *         → każda operacja dodawana jako "add(2,3)=5.0"
 *     - clearHistory() : void
 *
 * KOLEJNOŚĆ TDD (cykle):
 *   Cykl 1: add — proste przypadki (0+0, 2+3, -1+1)
 *   Cykl 2: subtract
 *   Cykl 3: multiply — w tym 0*n=0
 *   Cykl 4: divide — w tym wyjątek dla dzielenia przez 0
 *   Cykl 5: power — w tym ujemne wykładniki i exponent=0
 *   Cykl 6: sqrt — w tym wyjątek dla ujemnej
 *   Cykl 7: history — rejestrowanie operacji
 *
 * WSKAZÓWKA TDD:
 *   1. Napisz JEDEN failing test (RED)
 *   2. Zaimplementuj MINIMUM kodu aby przeszedł (GREEN)
 *   3. Popraw kod bez zmiany testów (REFACTOR)
 *   4. Powtórz od 1
 *
 * Plik do wypełnienia: CalcTask.java (implementacja Calculator)
 * Plik testów:         CalcTest.java (napisz sam!)
 */
public class CalcTask {

    // TODO: Zaimplementuj klasę Calculator

    public static class Calculator {
        // TODO: historia operacji (List<String>)

        // TODO: add, subtract, multiply, divide, power, sqrt

        // TODO: getHistory(), clearHistory()
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.add(2, 3));       // 5.0
        System.out.println(calc.divide(10, 4));   // 2.5
        System.out.println(calc.power(2, 10));    // 1024.0
        System.out.println(calc.sqrt(16));        // 4.0
        System.out.println(calc.getHistory());
        // [add(2.0,3.0)=5.0, divide(10.0,4.0)=2.5, power(2.0,10)=1024.0, sqrt(16.0)=4.0]
    }
}

