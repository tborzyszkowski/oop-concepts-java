package fields_and_methods.tests;

import fields_and_methods.after.Counter;
import fields_and_methods.after.MathUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla Counter i MathUtils.
 * Demonstracja JUnit 5: @Test, @ParameterizedTest, @BeforeEach, @Nested.
 */
@DisplayName("Counter i MathUtils - pola statyczne i instancyjne")
class CounterAndMathTest {

    @Nested
    @DisplayName("Counter - podstawowe operacje")
    class CounterTests {

        private Counter counter;

        @BeforeEach
        void setUp() {
            counter = new Counter("Test", 0, 10);
        }

        @Test
        @DisplayName("Nowy Counter startuje od minValue")
        void newCounterStartsAtMin() {
            Counter c = new Counter("X", 5, 20);
            assertEquals(5, c.getCount());
        }

        @Test
        @DisplayName("increment zwraca true i zwiekasza count")
        void incrementReturnsTrueAndIncreases() {
            assertTrue(counter.increment());
            assertEquals(1, counter.getCount());
        }

        @Test
        @DisplayName("increment zwraca false gdy osiagnieto maksimum")
        void incrementReturnsFalseAtMax() {
            for (int i = 0; i < 10; i++) counter.increment();
            assertFalse(counter.increment());
            assertEquals(10, counter.getCount()); // nie przekracza max
        }

        @Test
        @DisplayName("decrement zwraca true i zmniejsza count")
        void decrementReturnsTrueAndDecreases() {
            counter.increment();
            counter.increment();
            assertTrue(counter.decrement());
            assertEquals(1, counter.getCount());
        }

        @Test
        @DisplayName("decrement zwraca false gdy osiagnieto minimum")
        void decrementReturnsFalseAtMin() {
            assertFalse(counter.decrement());
            assertEquals(0, counter.getCount());
        }

        @Test
        @DisplayName("reset przywraca do minValue")
        void resetRestoresToMin() {
            counter.increment();
            counter.increment();
            counter.reset();
            assertEquals(0, counter.getCount());
        }

        @Test
        @DisplayName("Counter z ujemnym zakresem - reset do minValue")
        void counterWithNegativeMin() {
            Counter c = new Counter("Neg", -5, 5);
            assertEquals(-5, c.getCount());
            c.increment();
            c.increment();
            c.reset();
            assertEquals(-5, c.getCount());
        }

        @Test
        @DisplayName("Konstruktor rzuca wyjatek gdy minValue > maxValue")
        void constructorThrowsWhenMinGreaterThanMax() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Counter("Bad", 10, 5));
        }

        @Test
        @DisplayName("setName rzuca wyjatek dla pustej nazwy")
        void setNameThrowsForBlankName() {
            assertThrows(IllegalArgumentException.class,
                    () -> counter.setName(""));
            assertThrows(IllegalArgumentException.class,
                    () -> counter.setName("   "));
            assertThrows(IllegalArgumentException.class,
                    () -> counter.setName(null));
        }

        @Test
        @DisplayName("getTotalInstances jest polem statycznym - wspolne dla wszystkich")
        void totalInstancesIsSharedAcrossInstances() {
            int before = Counter.getTotalInstances();
            Counter c1 = new Counter("A", 0, 10);
            Counter c2 = new Counter("B", 0, 10);
            assertEquals(before + 2, Counter.getTotalInstances());
        }
    }

    @Nested
    @DisplayName("MathUtils - metody statyczne")
    class MathUtilsTests {

        @Test
        @DisplayName("square(int) zwraca kwadrat liczby")
        void squareIntReturnsCorrectValue() {
            assertEquals(0,  MathUtils.square(0));
            assertEquals(1,  MathUtils.square(1));
            assertEquals(9,  MathUtils.square(3));
            assertEquals(25, MathUtils.square(5));
        }

        @Test
        @DisplayName("square dla liczby ujemnej zwraca wartosc dodatnia")
        void squareNegativeReturnsPositive() {
            assertEquals(9,   MathUtils.square(-3));
            assertEquals(100, MathUtils.square(-10));
        }

        @ParameterizedTest(name = "factorial({0}) = {1}")
        @CsvSource({"0,1", "1,1", "2,2", "3,6", "4,24", "5,120", "10,3628800"})
        @DisplayName("factorial oblicza silnie poprawnie")
        void factorialIsCorrect(int n, long expected) {
            assertEquals(expected, MathUtils.factorial(n));
        }

        @Test
        @DisplayName("factorial rzuca wyjatek dla ujemnej liczby")
        void factorialThrowsForNegative() {
            assertThrows(IllegalArgumentException.class,
                    () -> MathUtils.factorial(-1));
        }

        @ParameterizedTest(name = "isPrime({0}) = {1}")
        @CsvSource({"0,false","1,false","2,true","3,true","4,false",
                    "7,true","9,false","11,true","17,true","49,false"})
        @DisplayName("isPrime rozpoznaje liczby pierwsze")
        void isPrimeIsCorrect(int n, boolean expected) {
            assertEquals(expected, MathUtils.isPrime(n));
        }

        @Test
        @DisplayName("circleArea oblicza pole kola")
        void circleAreaIsCorrect() {
            assertEquals(Math.PI * 25, MathUtils.circleArea(5), 1e-9);
            assertEquals(0.0,          MathUtils.circleArea(0), 1e-9);
        }

        @ParameterizedTest(name = "clamp({0},{1},{2}) = {3}")
        @CsvSource({"5,0,10,5", "15,0,10,10", "-5,0,10,0", "0,0,10,0", "10,0,10,10"})
        @DisplayName("clamp ogranicza wartosc do zakresu")
        void clampIsCorrect(int value, int min, int max, int expected) {
            assertEquals(expected, MathUtils.clamp(value, min, max));
        }

        @ParameterizedTest(name = "isPrime({0})")
        @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 97})
        @DisplayName("Znane liczby pierwsze sa poprawnie rozpoznawane")
        void knownPrimesAreRecognized(int prime) {
            assertTrue(MathUtils.isPrime(prime));
        }
    }
}

