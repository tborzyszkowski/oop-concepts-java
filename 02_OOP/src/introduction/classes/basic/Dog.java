package introduction.classes.basic;

/**
 * Przykład prostej klasy reprezentującej psa.
 *
 * Klasa to "szablon" (blueprint) opisujący:
 *  - POLA (fields) - co obiekt MA (stan, dane)
 *  - METODY (methods) - co obiekt POTRAFI ROBIĆ (zachowanie)
 *
 * Obiekt to konkretna instancja klasy, tworzona operatorem new.
 */
public class Dog {

    // ========== POLA (Fields) ==========
    // Każdy obiekt Dog ma swoje własne kopie tych pól

    String name;    // imię psa
    String breed;   // rasa
    int age;        // wiek w latach
    boolean isVaccinated; // czy zaszczepiony

    // ========== METODY (Methods) ==========
    // Definiują zachowanie obiektów klasy Dog

    /**
     * Metoda bark() - pies szczeka
     * Nie przyjmuje parametrów, nie zwraca wartości (void)
     */
    void bark() {
        System.out.println(name + " mówi: Hau! Hau!");
    }

    /**
     * Metoda describe() - opisuje psa
     * Używa pól obiektu (this.name, this.breed, itd.)
     */
    void describe() {
        System.out.println("Pies: " + name
                + ", rasa: " + breed
                + ", wiek: " + age + " lat");
    }

    /**
     * Metoda greet() - pies wita się z podanym imieniem
     * @param visitorName - imię odwiedzającego (parametr metody)
     */
    void greet(String visitorName) {
        System.out.println(name + " wita się z " + visitorName + "! Hau!");
    }

    /**
     * Metoda isAdult() - zwraca true jeśli pies jest dorosły (>= 2 lata)
     * @return boolean - wynik sprawdzenia
     */
    boolean isAdult() {
        return age >= 2;
    }

    /**
     * toString() - tekstowa reprezentacja obiektu
     * Używana automatycznie przez System.out.println()
     */
    @Override
    public String toString() {
        return "Dog{name='" + name + "', breed='" + breed
                + "', age=" + age + ", vaccinated=" + isVaccinated + "}";
    }
}

