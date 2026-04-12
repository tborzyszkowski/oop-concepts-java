package _02_interfaces.interfaces_implementation;

public interface Swimmable {
    void swim();
    int getDiveDepth();
    default String status() {
        return getClass().getSimpleName() + " pływa na głęb. " + getDiveDepth() + " m";
    }
}

