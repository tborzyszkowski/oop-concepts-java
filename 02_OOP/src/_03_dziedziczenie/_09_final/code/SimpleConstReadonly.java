package _03_dziedziczenie._09_final.code;

public class SimpleConstReadonly {
    final public double PI = 3.14159;
    final public int pesel;

    SimpleConstReadonly(int pesel) {
        double x = this.PI;
        this.pesel = pesel;
    }
//    void setPesel(int pesel) {
//        this.pesel = pesel;
//    }
}


