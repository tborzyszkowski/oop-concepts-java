package Lab;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        if (isValidName(name)) {
            this.name = name;
        } else {
            System.out.println("Invalid name. Name must be at least 2 characters long.");
        }
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    private boolean isValidName(String name) {
        return name != null && name.length() >= 2;
    }
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}