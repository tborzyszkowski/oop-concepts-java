package object_lifecycle.tests;

import object_lifecycle.basic.Person;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy Person.
 * Demonstracja TDD: każdy test sprawdza jedno konkretne zachowanie.
 *
 * Uruchomienie:
 *   javac -cp .;junit-platform-console-standalone.jar ...
 *   java  -cp .;junit-platform-console-standalone.jar
 *         org.junit.platform.console.ConsoleLauncher --scan-class-path
 */
@DisplayName("Person - konstruktory i cykl zycia")
class PersonTest {

    // ===== Konstruktory =====

    @Test
    @DisplayName("Konstruktor domyslny tworzy obiekt z wartosciami domyslnymi")
    void defaultConstructorSetsDefaults() {
        Person p = new Person();
        assertEquals("Nieznany", p.getFirstName());
        assertEquals("Nieznany", p.getLastName());
        assertEquals(0, p.getAge());
        assertEquals("brak@email.com", p.getEmail());
    }

    @Test
    @DisplayName("Konstruktor pelny ustawia wszystkie pola")
    void fullConstructorSetsAllFields() {
        Person p = new Person("Anna", "Kowalska", 30);
        assertEquals("Anna",     p.getFirstName());
        assertEquals("Kowalska", p.getLastName());
        assertEquals(30,         p.getAge());
    }

    @Test
    @DisplayName("Konstruktor 2-arg ustawia wiek=0")
    void twoArgConstructorSetsAgeZero() {
        Person p = new Person("Jan", "Nowak");
        assertEquals(0, p.getAge());
    }

    @Test
    @DisplayName("Konstruktor kopiujacy tworzy niezalezny obiekt")
    void copyConstructorCreatesIndependentCopy() {
        Person original = new Person("Anna", "Kowalska", 30);
        original.setEmail("anna@test.com");

        Person copy = new Person(original);

        // Dane sa takie same
        assertEquals(original.getFirstName(), copy.getFirstName());
        assertEquals(original.getLastName(),  copy.getLastName());
        assertEquals(original.getAge(),       copy.getAge());
        assertEquals(original.getEmail(),     copy.getEmail());

        // Ale to sa ROZNE obiekty — modyfikacja kopii nie zmienia oryginalu
        copy.setAge(25);
        assertEquals(30, original.getAge(), "Oryginał nie powinien sie zmienic");
        assertEquals(25, copy.getAge());
    }

    @Test
    @DisplayName("Konstruktor kopiujacy — kopia nie wskazuje na ten sam obiekt")
    void copyConstructorDoesNotShareReference() {
        Person original = new Person("Anna", "Kowalska", 30);
        Person copy = new Person(original);
        assertNotSame(original, copy);
    }

    // ===== Setter z walidacja =====

    @Test
    @DisplayName("setAge akceptuje poprawny wiek")
    void setAgeAcceptsValidAge() {
        Person p = new Person("Jan", "Nowak", 25);
        p.setAge(40);
        assertEquals(40, p.getAge());
    }

    @Test
    @DisplayName("setAge rzuca wyjatek dla ujemnego wieku")
    void setAgeThrowsForNegativeAge() {
        Person p = new Person("Jan", "Nowak", 25);
        assertThrows(IllegalArgumentException.class, () -> p.setAge(-1));
    }

    @Test
    @DisplayName("setAge rzuca wyjatek dla wieku > 150")
    void setAgeThrowsForTooHighAge() {
        Person p = new Person("Jan", "Nowak", 25);
        assertThrows(IllegalArgumentException.class, () -> p.setAge(151));
    }

    @Test
    @DisplayName("setAge akceptuje wartosc graniczna 0")
    void setAgeAcceptsBoundaryZero() {
        Person p = new Person("Jan", "Nowak", 25);
        assertDoesNotThrow(() -> p.setAge(0));
        assertEquals(0, p.getAge());
    }

    @Test
    @DisplayName("setAge akceptuje wartosc graniczna 150")
    void setAgeAcceptsBoundary150() {
        Person p = new Person("Jan", "Nowak", 25);
        assertDoesNotThrow(() -> p.setAge(150));
        assertEquals(150, p.getAge());
    }

    // ===== getFullName =====

    @Test
    @DisplayName("getFullName zwraca imie i nazwisko razem")
    void getFullNameCombinesNames() {
        Person p = new Person("Maria", "Wisniewska", 28);
        assertEquals("Maria Wisniewska", p.getFullName());
    }

    // ===== Email =====

    @Test
    @DisplayName("Email domyslny jest ustawiony przez blok inicjalizacyjny")
    void defaultEmailIsSetByInitBlock() {
        Person p = new Person("X", "Y", 1);
        assertEquals("brak@email.com", p.getEmail());
    }

    @Test
    @DisplayName("setEmail aktualizuje adres email")
    void setEmailUpdatesEmail() {
        Person p = new Person("Jan", "Nowak", 30);
        p.setEmail("jan@example.com");
        assertEquals("jan@example.com", p.getEmail());
    }
}

