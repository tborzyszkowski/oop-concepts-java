package solid.dependency_inversion.after;

/**
 * Konkretna implementacja MySQL.
 */
public class MySQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("MySQLDatabase: Saving to MySQL: " + data);
    }

    @Override
    public String retrieve(String id) {
        return "MySQL data for " + id;
    }
}

