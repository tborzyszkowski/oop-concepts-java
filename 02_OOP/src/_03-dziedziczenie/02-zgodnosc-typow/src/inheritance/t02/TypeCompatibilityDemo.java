package inheritance.t02;

class Vehicle {
    String name() { return "vehicle"; }
}

class Car extends Vehicle {
    @Override
    String name() { return "car"; }

    void openTrunk() {
        System.out.println("Bagaznik otwarty");
    }
}

public class TypeCompatibilityDemo {
    public static void main(String[] args) {
        Vehicle upcasted = new Car();
        System.out.println(upcasted.name());

        if (upcasted instanceof Car car) {
            car.openTrunk();
        }
    }
}

