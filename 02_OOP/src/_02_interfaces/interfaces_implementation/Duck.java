package _02_interfaces.interfaces_implementation;

/**
 * Kaczka implementuje DWA interfejsy jednocześnie.
 * Wymagane jest zaimplementowanie WSZYSTKICH metod z obu interfejsów.
 * W przypadku konfliktu nazw (status()) klasa musi nadpisać metodę explicite.
 */
public class Duck implements Flyable, Swimmable {
    private final String name;
    private int altitude  = 0;
    private int diveDepth = 0;

    public Duck(String name) { this.name = name; }

    // --- Flyable ---
    @Override public void fly() {
        altitude = 50 + (int)(Math.random() * 200);
        System.out.println(name + " wzlatuje: " + altitude + " m");
    }
    @Override public int getAltitude() { return altitude; }

    // --- Swimmable ---
    @Override public void swim() {
        diveDepth = (int)(Math.random() * 5);
        System.out.println(name + " pływa, głębokość: " + diveDepth + " m");
    }
    @Override public int getDiveDepth() { return diveDepth; }

    // Konflikt default status() — musimy zdefiniować explicite
    @Override public String status() {
        return name + " [alt=" + altitude + "m, depth=" + diveDepth + "m]";
    }

    // Można wywołać konkretną default metodę interfejsu przez super:
    public String flyStatus()  { return Flyable.super.status(); }
    public String swimStatus() { return Swimmable.super.status(); }
}

