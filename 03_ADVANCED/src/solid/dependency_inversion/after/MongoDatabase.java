package solid.dependency_inversion.after;

/**
 * Konkretna implementacja MongoDB.
 */
public class MongoDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("MongoDatabase: Saving to MongoDB: " + data);
    }

    @Override
    public String retrieve(String id) {
        return "MongoDB data for " + id;
    }
}

