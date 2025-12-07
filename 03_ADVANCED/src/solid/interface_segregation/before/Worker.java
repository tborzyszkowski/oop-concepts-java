package solid.interface_segregation.before;

/**
 * ❌ "Fat Interface" - zbyt szeroki interfejs.
 *
 * Problem: Niektórzy klienci są zmuszeni implementować
 * metody, których nie używają.
 */
public interface Worker {
    void work();
    void eat();
    void sleep();
    double getSalary();
}

