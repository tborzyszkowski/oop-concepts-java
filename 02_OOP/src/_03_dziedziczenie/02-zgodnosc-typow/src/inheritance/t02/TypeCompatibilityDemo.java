package inheritance.t02;

import java.util.Random;

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

class Bike extends Vehicle {
    @Override
    String name() { return "bike"; }
}

public class TypeCompatibilityDemo {
    public static void main(String[] args) {
        Car car = new Car();
        Bike bike = new Bike();
        Random random = new Random();
        boolean choice = random.nextBoolean();
        Vehicle vehicle;
        if  (choice) {
            vehicle = car;
        } else {
            vehicle = bike;
        }
        System.out.println(vehicle.name());

        if (vehicle instanceof Car car1) {
            car1.openTrunk();
        }
    }
}

