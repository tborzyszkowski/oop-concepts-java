package inheritance.t03;

class Person {
    protected final String name;

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
        return super.describe() + ", index=" + indexNumber;
    }
}

public class SuperKeywordDemo {
    public static void main(String[] args) {
        Student student = new Student("Ala", "s12345");
        System.out.println(student.describe());
    }
}

