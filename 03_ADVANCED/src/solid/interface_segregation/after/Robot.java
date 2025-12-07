package solid.interface_segregation.after;

/**
 * ✅ Robot implementuje tylko potrzebne interfejsy.
 */
public class Robot implements Workable {

    @Override
    public void work() {
        System.out.println("Robot is working efficiently");
    }
}

