package _01_introduction.object_lifecycle.copies;

import java.util.ArrayList;
import java.util.List;

public class CopyDemo {

    public static class Address {
        public String street;
        public String city;

        public Address(String street, String city) {
            this.street = street;
            this.city = city;
        }

        public Address(Address other) {
            this.street = other.street;
            this.city = other.city;
        }

        @Override
        public String toString() { return street + ", " + city; }
    }

    // =====================================================================
    // Klasa z PŁYTKĄ kopią (shallow copy)
    // =====================================================================
    public static class PersonShallow {
        public String name;
        public int age;
        public Address address;
        public List<String> hobbies;

        public PersonShallow(String name, int age, Address address, List<String> hobbies) {
            this.name = name;
            this.age = age;
            this.address = address;
            this.hobbies = hobbies;
        }

        public PersonShallow shallowCopy() {
            return new PersonShallow(this.name, this.age, this.address, this.hobbies);
        }

        @Override
        public String toString() {
            return "PersonShallow{name='" + name + "', age=" + age
                    + ", address=" + address + ", hobbies=" + hobbies + "}";
        }
    }

    // =====================================================================
    // Klasa z GŁĘBOKĄ kopią (deep copy)
    // =====================================================================
    public static class PersonDeep {
        public String name;
        public int age;
        public Address address;
        public List<String> hobbies;

        public PersonDeep(String name, int age, Address address, List<String> hobbies) {
            this.name = name;
            this.age = age;
            this.address = address;
            this.hobbies = hobbies;
        }

        public PersonDeep deepCopy() {
            return new PersonDeep(this.name, this.age,
                    new Address(this.address),
                    new ArrayList<>(this.hobbies));
        }

        @Override
        public String toString() {
            return "PersonDeep{name='" + name + "', age=" + age
                    + ", address=" + address + ", hobbies=" + hobbies + "}";
        }
    }

    // =====================================================================
    public static void main(String[] args) {

        System.out.println("=== Kopia plytka vs Kopia gleboka ===");
        System.out.println();

        System.out.println("--- 1. Kopia PLATKA (shallow copy) ---");
        Address addr1 = new Address("Kwiatowa 5", "Warszawa");
        List<String> hobbies1 = new ArrayList<>(List.of("programowanie", "ksiazki"));

        PersonShallow original = new PersonShallow("Anna", 30, addr1, hobbies1);
        PersonShallow shallowCopy = original.shallowCopy();

        System.out.println("Oryginal:  " + original);
        System.out.println("Kopia:     " + shallowCopy);

        shallowCopy.name = "Barbara";
        shallowCopy.age = 25;
        shallowCopy.address.city = "Krakow";
        shallowCopy.hobbies.add("sport");

        System.out.println();
        System.out.println("Po modyfikacji kopii:");
        System.out.println("  Oryginal: " + original);
        System.out.println("  Kopia:    " + shallowCopy);
        System.out.println("  To samo address? " + (original.address == shallowCopy.address));
        System.out.println("  Ta sama lista?   " + (original.hobbies == shallowCopy.hobbies));

        System.out.println();
        System.out.println("--- 2. Kopia GLEBOKA (deep copy) ---");
        Address addr2 = new Address("Sloneczna 10", "Gdansk");
        List<String> hobbies2 = new ArrayList<>(List.of("muzyka", "gotowanie"));

        PersonDeep original2 = new PersonDeep("Kamil", 35, addr2, hobbies2);
        PersonDeep deepCopy = original2.deepCopy();

        System.out.println("Oryginal: " + original2);
        System.out.println("Kopia:    " + deepCopy);

        deepCopy.address.city = "Poznan";
        deepCopy.hobbies.add("bieganie");
        deepCopy.name = "Krzysztof";

        System.out.println();
        System.out.println("Po modyfikacji kopii:");
        System.out.println("  Oryginal: " + original2);
        System.out.println("  Kopia:    " + deepCopy);
        System.out.println("  To samo address? " + (original2.address == deepCopy.address));
        System.out.println("  Ta sama lista?   " + (original2.hobbies == deepCopy.hobbies));

        System.out.println();
        System.out.println("--- 3. String jest IMMUTABLE - zawsze 'bezpieczny' ---");
        String s1 = "Hello";
        String s2 = s1;
        s2 = "World";
        System.out.println("s1 = " + s1 + "  (niezmieniony)");
        System.out.println("s2 = " + s2);
    }
}
