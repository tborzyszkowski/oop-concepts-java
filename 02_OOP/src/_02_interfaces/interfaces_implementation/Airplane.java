package _02_interfaces.interfaces_implementation;

/** Samolot — implementuje tylko Flyable (nie umie pływać). */
public class Airplane implements Flyable {
    private final String registration;
    private int altitude = 0;

    public Airplane(String registration) { this.registration = registration; }

    @Override public void fly() {
        altitude = 10_000 + (int)(Math.random() * 2000);
        System.out.println("Samolot " + registration + " wznosi się: " + altitude + " m");
    }
    @Override public int getAltitude() { return altitude; }
}

