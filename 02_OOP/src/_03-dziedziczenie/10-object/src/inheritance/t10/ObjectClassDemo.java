package inheritance.t10;

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

public class ObjectClassDemo {
    public static void main(String[] args) {
        Student a = new Student("1", "Ala");
        Student b = new Student("1", "Alicja");
        System.out.println(a.equals(b));
        System.out.println(a);
        System.out.println("root class=" + a.getClass().getSuperclass().getName());
    }
}

