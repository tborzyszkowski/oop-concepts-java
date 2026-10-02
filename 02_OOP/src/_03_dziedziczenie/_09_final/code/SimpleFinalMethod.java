package _03_dziedziczenie._09_final.code;

public class SimpleFinalMethod {
    final public void finalMethod() {
        System.out.println("This is a final method.");
    }
}
class SubSimpleFinalMethod extends SimpleFinalMethod {
    // Nie można nadpisać finalMethod() w tej klasie, ponieważ jest oznaczona jako final.
    // public void finalMethod() {
    //     System.out.println("Trying to override a final method.");
    // }
}
