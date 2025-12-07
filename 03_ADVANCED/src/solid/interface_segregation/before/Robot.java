package solid.interface_segregation.before;

/**
 * ❌ Robot zmuszony do implementacji metod, których nie używa.
 */
public class Robot implements Worker {

    @Override
    public void work() {
        System.out.println("Robot is working");
    }

    @Override
    public void eat() {
        throw new UnsupportedOperationException("Robot doesn't eat!");
    }

    @Override
    public void sleep() {
        throw new UnsupportedOperationException("Robot doesn't sleep!");
    }

    @Override
    public double getSalary() {
        throw new UnsupportedOperationException("Robot doesn't get salary!");
    }
}

