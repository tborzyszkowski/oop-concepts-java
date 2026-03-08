package object_lifecycle.copies;

import java.util.ArrayList;
import java.util.List;

/**
 * Wzorzec Prototype — tworzenie nowych obiektów przez klonowanie istniejących.
 *
 * Zastosowanie:
 *  - Tworzenie obiektów jest kosztowne (np. wymaga połączenia z DB, długiej inicjalizacji)
 *  - Chcemy tworzyć wariacje obiektu na bazie "szablonu" (prototypu)
 *  - Alternatywa dla konstruktora kopiującego — polimorficzne klonowanie
 *
 * Interfejs Cloneable w Javie jest przestarzały (odradzany).
 * Nowoczesne podejście: własny interfejs Prototype z metodą copy().
 */
public class PrototypeDemo {

    // =====================================================================
    // Interfejs Prototype — kontrakt klonowania
    // =====================================================================
    interface Prototype<T> {
        T copy(); // głęboka kopia
    }

    // =====================================================================
    // Obiekt zagnieżdżony
    // =====================================================================
    static class Position {
        double x, y;

        Position(double x, double y) { this.x = x; this.y = y; }
        Position(Position other)     { this.x = other.x; this.y = other.y; }

        @Override public String toString() { return "(" + x + ", " + y + ")"; }
    }

    // =====================================================================
    // Konkretny Prototype: GameCharacter
    // =====================================================================
    static class GameCharacter implements Prototype<GameCharacter> {
        private String name;
        private String type;      // "Warrior", "Mage", "Archer"
        private int health;
        private int mana;
        private int level;
        private Position position;
        private List<String> inventory;

        // Pełny konstruktor — używany raz do stworzenia "prototypu"
        GameCharacter(String name, String type, int health, int mana, int level, Position pos) {
            this.name      = name;
            this.type      = type;
            this.health    = health;
            this.mana      = mana;
            this.level     = level;
            this.position  = pos;
            this.inventory = new ArrayList<>();
            System.out.println("[EXPENSIVE INIT] Tworzenie GameCharacter: " + name
                    + " (symulacja dlugiej inicjalizacji...)");
        }

        // Prywatny konstruktor kopiujący — używany wewnętrznie przez copy()
        private GameCharacter(GameCharacter other) {
            this.name      = other.name;
            this.type      = other.type;
            this.health    = other.health;
            this.mana      = other.mana;
            this.level     = other.level;
            this.position  = new Position(other.position);        // gleboka kopia
            this.inventory = new ArrayList<>(other.inventory);    // gleboka kopia
            // NIE drukujemy [EXPENSIVE INIT] — kopia jest tania!
        }

        @Override
        public GameCharacter copy() {
            return new GameCharacter(this);
        }

        // Settery — umożliwiają modyfikację klonu
        public GameCharacter withName(String name) { this.name = name; return this; }
        public GameCharacter atPosition(double x, double y) { this.position = new Position(x, y); return this; }
        public GameCharacter withLevel(int level) { this.level = level; return this; }
        public GameCharacter addItem(String item) { this.inventory.add(item); return this; }

        @Override
        public String toString() {
            return type + " " + name + " | HP:" + health + " MP:" + mana
                    + " Lvl:" + level + " @ " + position
                    + " | inv:" + inventory;
        }
    }

    // =====================================================================
    // "Registry" — rejestr prototypów
    // =====================================================================
    static class CharacterRegistry {
        private final java.util.Map<String, GameCharacter> prototypes = new java.util.HashMap<>();

        void register(String key, GameCharacter prototype) {
            prototypes.put(key, prototype);
            System.out.println("[REGISTRY] Zarejestrowano prototyp: " + key);
        }

        GameCharacter spawn(String key) {
            GameCharacter proto = prototypes.get(key);
            if (proto == null) throw new IllegalArgumentException("Brak prototypu: " + key);
            System.out.println("[SPAWN] Klonowanie: " + key);
            return proto.copy();
        }
    }

    // =====================================================================
    public static void main(String[] args) {

        System.out.println("=== Wzorzec Prototype ===");
        System.out.println();

        System.out.println("--- 1. Tworzenie prototypów (kosztowna inicjalizacja) ---");
        GameCharacter warriorProto = new GameCharacter(
                "Warrior_PROTO", "Warrior", 200, 50, 1, new Position(0, 0));
        warriorProto.addItem("Sword").addItem("Shield");

        GameCharacter mageProto = new GameCharacter(
                "Mage_PROTO", "Mage", 100, 200, 1, new Position(0, 0));
        mageProto.addItem("Staff").addItem("Spellbook");

        System.out.println();
        System.out.println("--- 2. Rejestracja w CharacterRegistry ---");
        CharacterRegistry registry = new CharacterRegistry();
        registry.register("warrior", warriorProto);
        registry.register("mage", mageProto);

        System.out.println();
        System.out.println("--- 3. Szybkie spawning przez klonowanie ---");

        GameCharacter w1 = registry.spawn("warrior")
                .withName("Aragorn").atPosition(10, 20).withLevel(5);
        GameCharacter w2 = registry.spawn("warrior")
                .withName("Boromir").atPosition(15, 25).withLevel(3).addItem("Horn");
        GameCharacter m1 = registry.spawn("mage")
                .withName("Gandalf").atPosition(5, 5).withLevel(10).addItem("Ring");

        System.out.println();
        System.out.println("Spawned characters:");
        System.out.println("  " + w1);
        System.out.println("  " + w2);
        System.out.println("  " + m1);
        System.out.println("  Proto: " + warriorProto);  // prototyp niezmieniony!

        System.out.println();
        System.out.println("--- 4. Kopia jest NIEZALEZNA od prototypu ---");
        w1.addItem("Elvish Bow");
        System.out.println("Po dodaniu itemu do w1:");
        System.out.println("  w1 inventory: " + w1);
        System.out.println("  w2 inventory: " + w2);        // niezalezna lista
        System.out.println("  proto inventory: " + warriorProto); // niezmieniony!
    }
}

