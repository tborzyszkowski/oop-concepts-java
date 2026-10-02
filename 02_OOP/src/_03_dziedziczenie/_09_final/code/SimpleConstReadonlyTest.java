package _03_dziedziczenie._09_final.code;

public class SimpleConstReadonlyTest {
    public static void main(String[] args) {
        SimpleConstReadonly ob1 = new SimpleConstReadonly(123456789);
        SimpleConstReadonly ob2 = new SimpleConstReadonly(987654321);

        System.out.println(ob1.equals(ob2));
        System.out.println(ob1.pesel);
        System.out.println(ob2.pesel);
        System.out.println(ob1.PI);
        System.out.println(ob2.PI);
    }
}
