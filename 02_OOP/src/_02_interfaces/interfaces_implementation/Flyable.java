package _02_interfaces.interfaces_implementation;

public interface Flyable {
    void fly();
    int getAltitude();
    default String status() {
        return getClass().getSimpleName() + " leci na wys. " + getAltitude() + " m";
    }
}

