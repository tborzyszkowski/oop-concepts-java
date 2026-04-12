package _03_dziedziczenie._01_dziedziczenie_intro.code;

class Animal {
    public String speak() {
        return "...";
    }
}

class Dog extends Animal {
    @Override
    public String speak() {
        return "Hau";
    }
}

public class InheritanceIntroDemo {
    public static void main(String[] args) {
        Animal a = new Animal();
        Animal d = new Dog();
//        Dog dog = new Animal();
        System.out.println("Animal: " + a.speak());
        System.out.println("Dog jako Animal: " + d.speak());
    }
}

