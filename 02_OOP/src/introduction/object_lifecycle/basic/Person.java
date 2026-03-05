package introduction.object_lifecycle.basic;

/**
 * Klasa Person demonstrująca konstruktory i ich przeciążanie.
 *
 * Demonstracja:
 *  - Konstruktor domyślny (bezargumentowy)
 *  - Konstruktor z parametrami
 *  - Konstruktor kopiujący
 *  - Delegowanie konstruktorów przez this(...)
 *  - Blok inicjalizacyjny i blok statyczny
 *  - Kolejność inicjalizacji
 */
public class Person {

    // ===== POLA =====
    private String firstName;
    private String lastName;
    private int age;
    private String email;

    // ===== BLOK STATYCZNY =====
    // Wykonywany RAZ — gdy klasa jest ładowana przez JVM
    static {
        System.out.println("[STATIC BLOCK] Klasa Person załadowana przez JVM");
    }

    // ===== BLOK INICJALIZACYJNY INSTANCJI =====
    // Wykonywany PRZY KAŻDYM tworzeniu obiektu, PRZED konstruktorem
    {
        System.out.println("[INIT BLOCK] Blok inicjalizacyjny — przed konstruktorem");
        email = "brak@email.com"; // wartość domyślna dla wszystkich konstruktorów
    }

    // ===== KONSTRUKTOR 1: domyślny (bezargumentowy) =====
    public Person() {
        // Deleguje do konstruktora z 3 argumentami
        this("Nieznany", "Nieznany", 0);
        System.out.println("[CONSTRUCTOR] Person() — domyślny");
    }

    // ===== KONSTRUKTOR 2: imię i nazwisko =====
    public Person(String firstName, String lastName) {
        this(firstName, lastName, 0);  // deleguje do konstruktora 3-arg
        System.out.println("[CONSTRUCTOR] Person(firstName, lastName)");
    }

    // ===== KONSTRUKTOR 3: pełny =====
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.age       = age;
        System.out.println("[CONSTRUCTOR] Person(firstName, lastName, age) — docelowy");
    }

    // ===== KONSTRUKTOR 4: kopiujący =====
    public Person(Person other) {
        this(other.firstName, other.lastName, other.age);
        this.email = other.email;
        System.out.println("[CONSTRUCTOR] Person(Person) — kopiujący");
    }

    // ===== GETTERY I SETTERY =====
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public int    getAge()       { return age; }
    public String getEmail()     { return email; }

    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) {
        if (age < 0 || age > 150)
            throw new IllegalArgumentException("Wiek poza zakresem: " + age);
        this.age = age;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Person{'" + firstName + " " + lastName
                + "', age=" + age + ", email='" + email + "'}";
    }
}

