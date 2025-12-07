package solid.dependency_inversion.after;

/**
 * ✅ UserService zależy od abstrakcji (Database), nie konkretnej implementacji.
 * Dependency Injection przez konstruktor.
 */
public class UserService {
    private final Database database;  // ✅ Abstrakcja

    // Constructor Injection
    public UserService(Database database) {
        this.database = database;
    }

    public void saveUser(String userName) {
        System.out.println("UserService: Saving user " + userName);
        database.save(userName);
    }

    public String getUser(String userId) {
        System.out.println("UserService: Retrieving user " + userId);
        return database.retrieve(userId);
    }
}

