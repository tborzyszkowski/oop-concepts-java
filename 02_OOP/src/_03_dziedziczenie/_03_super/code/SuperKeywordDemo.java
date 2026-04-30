package _03_dziedziczenie._03_super.code;

class Person {
    protected final String name;

    Person(){ this.name = ""; }
    Person(String name) {
        this.name = name;
    }

    String describe() {
        return "Person: " + name;
    }
}

class Student extends Person {
    private final String indexNumber;

    Student(String name, String indexNumber) {
        super(name);
        this.indexNumber = indexNumber;
    }

    @Override
    String describe() {
        String inheritedName = super.name;
        return super.describe() + ", inheritedName=" + inheritedName + ", index=" + indexNumber;
    }

    String describeWithFieldReference() {
        return "Student(super.name=" + super.name + ", index=" + indexNumber + ")";
    }
}

public class SuperKeywordDemo {
    public static void main(String[] args) {
        Student student = new Student("Ala", "s12345");
        System.out.println(student.describe());
        System.out.println(student.describeWithFieldReference());
    }
}

