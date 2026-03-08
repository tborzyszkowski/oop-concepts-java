package classes.basic;

/**
 * Demonstracja tworzenia i używania obiektów klasy Dog.
 *
 * Kluczowe pojęcia:
 *  - Tworzenie obiektów: new Dog()
 *  - Dostęp do pól: obiekt.pole
 *  - Wywołanie metod: obiekt.metoda()
 *  - Referencja vs obiekt
 */
public class ClassesDemo {

    public static void main(String[] args) {

        System.out.println("=== 1. Tworzenie obiektów (instancji) ===");
        System.out.println();

        // Deklaracja zmiennej referencyjnej i tworzenie obiektu
        // Dog rex  - zmienna referencyjna (na stosie / stack)
        // new Dog() - tworzy obiekt na stercie (heap)
        Dog rex = new Dog();

        // Przypisanie wartości polom obiektu
        rex.name = "Rex";
        rex.breed = "Owczarek Niemiecki";
        rex.age = 3;
        rex.isVaccinated = true;

        System.out.println("Obiekt rex: " + rex);

        // Drugi obiekt - niezależna kopia wszystkich pól!
        Dog buddy = new Dog();
        buddy.name = "Buddy";
        buddy.breed = "Labrador";
        buddy.age = 1;
        buddy.isVaccinated = false;

        System.out.println("Obiekt buddy: " + buddy);

        System.out.println();
        System.out.println("=== 2. Wywoływanie metod ===");
        System.out.println();

        rex.bark();
        buddy.bark();

        System.out.println();
        rex.describe();
        buddy.describe();

        System.out.println();
        rex.greet("Ania");

        System.out.println();
        System.out.println("=== 3. Metody z wartością zwracaną ===");
        System.out.println();

        System.out.println("Rex jest dorosły? " + rex.isAdult());
        System.out.println("Buddy jest dorosły? " + buddy.isAdult());

        System.out.println();
        System.out.println("=== 4. Referencje - wiele zmiennych, jeden obiekt ===");
        System.out.println();

        // Przypisanie referencji - NIE kopiuje obiektu!
        Dog anotherRefToRex = rex;
        anotherRefToRex.age = 4;  // modyfikuje TEN SAM obiekt

        System.out.println("rex.age = " + rex.age);           // 4
        System.out.println("anotherRefToRex.age = " + anotherRefToRex.age); // 4

        System.out.println();
        System.out.println("Czy rex == anotherRefToRex? " + (rex == anotherRefToRex)); // true - ta sama referencja
        System.out.println("Czy rex == buddy? " + (rex == buddy));   // false - różne obiekty

        System.out.println();
        System.out.println("=== 5. Null - brak obiektu ===");
        System.out.println();

        Dog nodog = null; // zmienna nie wskazuje na żaden obiekt
        System.out.println("nodog = " + nodog);
        // UWAGA: nodog.bark() spowoduje NullPointerException!

        System.out.println();
        System.out.println("=== 6. Każdy obiekt ma swoje własne pola ===");
        System.out.println();

        Dog[] pack = new Dog[3];
        String[] names = {"Luna", "Max", "Bella"};
        String[] breeds = {"Beagle", "Husky", "Poodle"};
        int[] ages = {2, 5, 1};

        for (int i = 0; i < pack.length; i++) {
            pack[i] = new Dog();
            pack[i].name = names[i];
            pack[i].breed = breeds[i];
            pack[i].age = ages[i];
            pack[i].describe();
        }
    }
}

