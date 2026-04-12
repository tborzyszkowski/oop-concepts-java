package _03_dziedziczenie._10_object.code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Objects;

class Student {
    private final String id;
    private final String name;

    Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{id='" + id + "', name='" + name + "'}";
    }
}

class BrokenStudent {
    private final String id;
    private final String name;

    BrokenStudent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BrokenStudent that)) return false;
        return Objects.equals(id, that.id);
    }

    // Celowo brak hashCode(): demonstracja złamania kontraktu.
    @Override
    public String toString() {
        return "BrokenStudent{id='" + id + "', name='" + name + "'}";
    }
}

class CollisionKey {
    private final String value;

    CollisionKey(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollisionKey that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value.length();
    }

    @Override
    public String toString() {
        return "CollisionKey{value='" + value + "'}";
    }
}

class MutableKey {
    private String id;

    MutableKey(String id) {
        this.id = id;
    }

    void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MutableKey that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MutableKey{id='" + id + "'}";
    }
}

public class ObjectClassDemo {
    private static void validContractDemo() {
        Student first = new Student("1", "Ala");
        Student second = new Student("1", "Alicja");

        System.out.println("--- Poprawny kontrakt ---");
        System.out.println("first.equals(second) = " + first.equals(second));
        System.out.println("first.hashCode() == second.hashCode() = " + (first.hashCode() == second.hashCode()));

        Set<Student> students = new HashSet<>();
        students.add(first);
        students.add(second);
        System.out.println("HashSet size (oczekiwane 1) = " + students.size());
    }

    private static void brokenContractDemo() {
        BrokenStudent first = new BrokenStudent("1", "Ala");
        BrokenStudent second = new BrokenStudent("1", "Alicja");

        System.out.println("\n--- Zlamany kontrakt: equals bez hashCode ---");
        System.out.println("first.equals(second) = " + first.equals(second));
        System.out.println("first.hashCode() == second.hashCode() = " + (first.hashCode() == second.hashCode()));

        Set<BrokenStudent> students = new HashSet<>();
        students.add(first);
        students.add(second);
        System.out.println("HashSet size (blednie 2) = " + students.size());
    }

    private static void collisionDemo() {
        CollisionKey first = new CollisionKey("AB");
        CollisionKey second = new CollisionKey("CD");

        System.out.println("\n--- Kolizje hashCode ---");
        System.out.println("first.hashCode() = " + first.hashCode() + ", second.hashCode() = " + second.hashCode());
        System.out.println("first.equals(second) = " + first.equals(second));

        Map<CollisionKey, String> map = new HashMap<>();
        map.put(first, "pierwszy");
        map.put(second, "drugi");
        System.out.println("HashMap size (mimo kolizji poprawnie 2) = " + map.size());
    }

    private static void mutableKeyDemo() {
        MutableKey key = new MutableKey("A-01");
        Map<MutableKey, String> map = new HashMap<>();
        map.put(key, "zapis przed modyfikacja");

        System.out.println("\n--- Mutowalny klucz: antywzorzec ---");
        System.out.println("Odczyt przed zmiana = " + map.get(key));

        key.setId("B-99");
        System.out.println("Klucz po zmianie = " + key);
        System.out.println("Odczyt po zmianie (czesto null) = " + map.get(key));
        System.out.println("Map containsKey(key) = " + map.containsKey(key));
    }

    public static void main(String[] args) {
        validContractDemo();
        brokenContractDemo();
        collisionDemo();
        mutableKeyDemo();

        Student sample = new Student("42", "Jan");
        System.out.println("\nroot class = " + sample.getClass().getSuperclass().getName());
    }
}

