package _01_introduction.exercises.tasks;

/**
 * ZADANIE 2: Głęboka kopia klasy TeamMember
 * ==========================================
 * Poziom: Średni — oparty na module "object_lifecycle/copies"
 *
 * POLECENIE:
 *   Zaimplementuj klasy TeamMember i Team tak, aby kopia Teamu
 *   była GŁĘBOKA (deep copy) — modyfikacja kopii nie zmienia oryginału.
 *
 * WYMAGANIA:
 *
 *   Klasa TeamMember:
 *     - Pola: String name, String role, List<String> skills (mutowalna lista!)
 *     - Konstruktor pełny + konstruktor kopiujący (deep copy!)
 *     - Metody: addSkill(String), getSkills(), getName(), getRole()
 *
 *   Klasa Team:
 *     - Pola: String teamName, List<TeamMember> members
 *     - Konstruktor(String teamName)
 *     - addMember(TeamMember)
 *     - deepCopy() — głęboka kopia Teamu (nowy Team, nowe TeamMember, nowe listy skills)
 *     - getMembers(), getTeamName(), size()
 *
 * ZACHOWANIE:
 *   Team original = new Team("Alpha");
 *   original.addMember(new TeamMember("Anna", "Dev", List.of("Java", "SQL")));
 *   Team copy = original.deepCopy();
 *   copy.getMembers().get(0).addSkill("Python");
 *   // original.getMembers().get(0).getSkills() NIE zawiera "Python"!
 *
 * TESTY (w TeamTest.java):
 *   - Sprawdzają głębokość kopii
 *   - Sprawdzają niezależność list skills
 *   - Sprawdzają niezależność listy members
 *
 * WSKAZÓWKI:
 *   - Patrz: CopyDemo.java — PersonDeep.deepCopy()
 *   - Użyj: new ArrayList<>(otherList) dla kopii listy
 *   - Konstruktor kopiujący TeamMember powinien kopiować listę skills!
 */
public class TeamCopyTask {

    // TODO: Zaimplementuj klasy TeamMember i Team

    public static class TeamMember {
        // TODO
    }

    public static class Team {
        // TODO
    }

    public static void main(String[] args) {
        // TODO: Przetestuj implementację
        // Stwórz team, dodaj członków z umiejętnościami
        // Zrób deepCopy, zmodyfikuj kopię, sprawdź że oryginał niezmieniony
    }
}

