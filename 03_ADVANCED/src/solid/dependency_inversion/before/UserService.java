package solid.dependency_inversion.before;

/**
 * ❌ Naruszenie DIP - zależność od konkretnej klasy.
 */
public class UserService {
    private MySQLDatabase database = new MySQLDatabase();  // ❌ Konkretna klasa

    public void saveUser(String userName) {
        System.out.println("UserService: Saving user " + userName);
        database.save(userName);
    }
}

