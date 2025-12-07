package solid.dependency_inversion.after;

/**
 * ✅ Abstrakcja - zgodnie z DIP.
 */
public interface Database {
    void save(String data);
    String retrieve(String id);
}

