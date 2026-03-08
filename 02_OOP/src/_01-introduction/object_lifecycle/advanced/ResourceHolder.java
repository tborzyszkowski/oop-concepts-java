package object_lifecycle.advanced;

/**
 * Demonstracja wzorca AutoCloseable + try-with-resources.
 *
 * Zamiast finalize() (przestarzałe) używamy:
 *  1. AutoCloseable + try-with-resources — do zasobów (pliki, połączenia DB, sockets)
 *  2. java.lang.ref.Cleaner — do rejestrowania akcji przy GC (Java 9+)
 */
public class ResourceHolder implements AutoCloseable {

    private final String resourceName;
    private boolean open;

    public ResourceHolder(String resourceName) {
        this.resourceName = resourceName;
        this.open = true;
        System.out.println("[OPEN]  ResourceHolder: " + resourceName);
    }

    public void doWork() {
        if (!open) throw new IllegalStateException("Zasób zamknięty: " + resourceName);
        System.out.println("[WORK]  Pracuję z zasobem: " + resourceName);
    }

    /**
     * Zwalnianie zasobu — wywoływane automatycznie przez try-with-resources.
     * Odpowiednik: zamykanie pliku, rozłączanie z bazą danych, zwalnianie socketów.
     */
    @Override
    public void close() {
        if (open) {
            open = false;
            System.out.println("[CLOSE] ResourceHolder zamknięty: " + resourceName);
        }
    }

    public boolean isOpen() { return open; }

    // =====================================================================

    public static void main(String[] args) {

        System.out.println("=== AutoCloseable + try-with-resources ===");
        System.out.println();

        // --- 1. try-with-resources — close() wywołane automatycznie ---
        System.out.println("--- 1. try-with-resources (zalecane) ---");
        try (ResourceHolder res = new ResourceHolder("Połączenie z DB")) {
            res.doWork();
            res.doWork();
        } // <- res.close() wywołane automatycznie tutaj!
        System.out.println("Po bloku try — close() już wywołane");

        System.out.println();
        System.out.println("--- 2. try-with-resources z wyjątkiem ---");
        try (ResourceHolder res = new ResourceHolder("Plik CSV")) {
            res.doWork();
            throw new RuntimeException("Symulowany błąd");
        } catch (RuntimeException e) {
            System.out.println("Wyjątek: " + e.getMessage());
            System.out.println("close() zostało wywołane MIMO wyjątku!");
        }

        System.out.println();
        System.out.println("--- 3. Wiele zasobów w jednym try ---");
        try (ResourceHolder r1 = new ResourceHolder("Socket");
             ResourceHolder r2 = new ResourceHolder("Plik logu")) {
            r1.doWork();
            r2.doWork();
        } // r2.close(), r1.close() — w odwrotnej kolejności!
        System.out.println("Zasoby zamknięte w odwrotnej kolejności otwarcia");

        System.out.println();
        System.out.println("--- 4. Bez try-with-resources (stary styl — NIE ROBIĆ TAK) ---");
        ResourceHolder legacyRes = new ResourceHolder("Legacy DB");
        try {
            legacyRes.doWork();
        } finally {
            legacyRes.close(); // trzeba ręcznie zamknąć — ryzyko zapomnienia!
        }
    }
}

