package solid.dependency_inversion.after;

/**
 * Demonstracja zastosowania DIP i Dependency Injection.
 */
public class UserServiceDemo {

    public static void main(String[] args) {
        System.out.println("=== ROZWIĄZANIE: Zastosowanie DIP ===\n");

        // Tworzymy różne implementacje baz danych
        Database mysqlDb = new MySQLDatabase();
        Database mongoDb = new MongoDatabase();

        // Wstrzykujemy zależność (Dependency Injection)
        System.out.println("=== Używanie MySQL ===");
        UserService userServiceWithMySQL = new UserService(mysqlDb);
        userServiceWithMySQL.saveUser("Jan Kowalski");
        System.out.println(userServiceWithMySQL.getUser("123"));

        System.out.println("\n=== Używanie MongoDB ===");
        UserService userServiceWithMongo = new UserService(mongoDb);
        userServiceWithMongo.saveUser("Anna Nowak");
        System.out.println(userServiceWithMongo.getUser("456"));

        System.out.println("\n=== KORZYŚCI ===");
        System.out.println("✅ UserService nie zależy od konkretnej bazy");
        System.out.println("✅ Łatwa zmiana implementacji bazy danych");
        System.out.println("✅ Możliwość mockowania w testach");
        System.out.println("✅ Zgodność z Open/Closed Principle");
        System.out.println("✅ Niskie sprzężenie, wysoka elastyczność");
    }
}

