package solid.interface_segregation.after;

/**
 * ✅ Człowiek implementuje wszystkie interfejsy.
 */
public class Human implements Workable, Eatable, Sleepable, Payable {

    @Override
    public void work() {
        System.out.println("Human is working");
    }

    @Override
    public void eat() {
        System.out.println("Human is eating");
    }

    @Override
    public void sleep() {
        System.out.println("Human is sleeping");
    }

    @Override
    public double getSalary() {
        return 5000.0;
    }
}

