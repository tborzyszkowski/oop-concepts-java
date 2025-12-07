package solid.dependency_inversion.before;

public class MySQLDatabase {
    public void save(String data) {
        System.out.println("MySQLDatabase: Saving to MySQL: " + data);
    }
}

