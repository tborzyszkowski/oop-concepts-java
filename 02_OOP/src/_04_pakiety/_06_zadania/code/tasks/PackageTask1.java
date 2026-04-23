package _04_pakiety._06_zadania.code.tasks;

/**
 * Zadanie 1 (⭐ łatwe): Pierwszy pakiet — system szkolny
 *
 * INSTRUKCJA:
 * 1) Utwórz katalog: _06_zadania/src/school/people/
 *    i w nim plik Teacher.java z zawartością:
 *
 *    package school.people;
 *    public record Teacher(String name, String subject) {}
 *
 * 2) Utwórz katalog: _06_zadania/src/school/app/
 *    i w nim plik SchoolApp.java:
 *
 *    package school.app;
 *    import school.people.Teacher;
 *    public class SchoolApp {
 *        public static void main(String[] args) {
 *            Teacher t1 = new Teacher("dr Nowak", "Matematyka");
 *            Teacher t2 = new Teacher("mgr Kowalski", "Java");
 *            System.out.println("Nauczyciel: " + t1.name() + ", przedmiot: " + t1.subject());
 *            System.out.println("Nauczyciel: " + t2.name() + ", przedmiot: " + t2.subject());
 *        }
 *    }
 *
 * 3) Skompiluj i uruchom ręcznie:
 *    javac --release 21 -d out src\school\people\Teacher.java src\school\app\SchoolApp.java
 *    java -cp out school.app.SchoolApp
 *
 * OCZEKIWANY WYNIK:
 *   Nauczyciel: dr Nowak, przedmiot: Matematyka
 *   Nauczyciel: mgr Kowalski, przedmiot: Java
 *
 * Patrz: moduł 4.2 (struktura katalogów) i 4.5 (import)
 */
public final class PackageTask1 {
    private PackageTask1() {}
}

