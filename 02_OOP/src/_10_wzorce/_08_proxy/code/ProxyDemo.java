package _10_wzorce._08_proxy.code;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Proxy Pattern — trzy rodzaje:
 * 1. Virtual Proxy (lazy loading)
 * 2. Protection Proxy (kontrola dostepu)
 * 3. Caching Proxy (wyniki w pamieci)
 * 4. Dynamic Proxy (java.lang.reflect.Proxy)
 */
public class ProxyDemo {

    // ─── Interfejs Subject ─────────────────────────────────────────────────────
    interface Image {
        void display();
        String getInfo();
    }

    // ─── Real Subject ─────────────────────────────────────────────────────────
    static class HighResImage implements Image {
        private final String filename;
        private final byte[] data;

        HighResImage(String filename) {
            this.filename = filename;
            System.out.println("  [HighResImage] Ladowanie z dysku: " + filename + " ...");
            // Symulacja kosztownego ladowania (I/O)
            this.data = new byte[1024 * 1024]; // 1MB
            System.out.println("  [HighResImage] Zaladowano " + data.length / 1024 + "KB");
        }

        @Override public void display() {
            System.out.println("  [HighResImage] Wyswietlam: " + filename + " (" + data.length/1024 + "KB)");
        }

        @Override public String getInfo() {
            return "HighRes[" + filename + ", " + data.length/1024 + "KB]";
        }
    }

    // ─── 1. Virtual Proxy (Lazy Loading) ─────────────────────────────────────
    static class LazyImageProxy implements Image {
        private final String filename;
        private HighResImage realImage;   // tworzone leniwie!

        LazyImageProxy(String filename) {
            this.filename = filename;
            System.out.println("  [LazyProxy] Utworzono proxy dla: " + filename + " (bez ladowania!)");
        }

        @Override public void display() {
            if (realImage == null) {
                realImage = new HighResImage(filename);   // pierwsze wywolanie!
            }
            realImage.display();
        }

        @Override public String getInfo() {
            // Mozna odpowiedziec bez ladowania pelnego obrazu
            return "LazyProxy[" + filename + ", loaded=" + (realImage != null) + "]";
        }
    }

    // ─── 2. Protection Proxy (Kontrola dostepu) ──────────────────────────────
    interface DocumentService {
        String readDocument(String docId);
        void writeDocument(String docId, String content);
        void deleteDocument(String docId);
    }

    static class RealDocumentService implements DocumentService {
        private final Map<String,String> docs = new HashMap<>();

        RealDocumentService() {
            docs.put("doc1", "Poufna tresc dokumentu 1");
            docs.put("doc2", "Poufna tresc dokumentu 2");
        }

        @Override public String readDocument(String docId) {
            return docs.getOrDefault(docId, "Nie znaleziono");
        }

        @Override public void writeDocument(String docId, String content) {
            docs.put(docId, content);
            System.out.println("  [Service] Zapisano: " + docId);
        }

        @Override public void deleteDocument(String docId) {
            docs.remove(docId);
            System.out.println("  [Service] Usunieto: " + docId);
        }
    }

    enum Role { GUEST, USER, ADMIN }

    static class ProtectionProxy implements DocumentService {
        private final DocumentService real;
        private final Role userRole;
        private final String userName;

        ProtectionProxy(DocumentService real, String userName, Role role) {
            this.real = real;
            this.userName = userName;
            this.userRole = role;
        }

        private void checkPermission(String operation, Role required) {
            if (userRole.ordinal() < required.ordinal()) {
                throw new SecurityException(
                    "Uzytkownik " + userName + " (" + userRole + ") nie ma uprawnien do: " + operation
                );
            }
            System.out.println("  [Proxy] " + userName + " (" + userRole + ") → " + operation + ": dozwolone");
        }

        @Override public String readDocument(String docId) {
            checkPermission("read", Role.USER);
            return real.readDocument(docId);
        }

        @Override public void writeDocument(String docId, String content) {
            checkPermission("write", Role.USER);
            real.writeDocument(docId, content);
        }

        @Override public void deleteDocument(String docId) {
            checkPermission("delete", Role.ADMIN);
            real.deleteDocument(docId);
        }
    }

    // ─── 3. Caching Proxy ────────────────────────────────────────────────────
    interface PriceService {
        double getPrice(String productId);
    }

    static class SlowPriceService implements PriceService {
        @Override public double getPrice(String productId) {
            System.out.println("  [SlowService] Pobieranie ceny z API dla: " + productId);
            // Symulacja wolnego API
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return switch (productId) {
                case "A1" -> 49.99;
                case "B2" -> 129.00;
                default   -> 0.0;
            };
        }
    }

    static class CachingPriceProxy implements PriceService {
        private final PriceService real;
        private final Map<String, Double> cache = new HashMap<>();
        private int hits = 0, misses = 0;

        CachingPriceProxy(PriceService real) { this.real = real; }

        @Override public double getPrice(String productId) {
            if (cache.containsKey(productId)) {
                hits++;
                System.out.println("  [Cache HIT] " + productId + " → " + cache.get(productId));
                return cache.get(productId);
            }
            misses++;
            double price = real.getPrice(productId);
            cache.put(productId, price);
            return price;
        }

        public void printStats() {
            System.out.printf("  Cache stats: hits=%d, misses=%d, ratio=%.0f%%%n",
                    hits, misses, (double)hits/(hits+misses)*100);
        }
    }

    // ─── 4. Dynamic Proxy (Java Reflection) ──────────────────────────────────
    static class LoggingHandler implements InvocationHandler {
        private final Object target;

        LoggingHandler(Object target) { this.target = target; }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("  [LOG] → " + method.getName() + "(" +
                    (args != null ? java.util.Arrays.toString(args) : "") + ")");
            long start = System.nanoTime();
            Object result = method.invoke(target, args);
            long ms = (System.nanoTime() - start) / 1_000_000;
            System.out.println("  [LOG] ← " + method.getName() + " = " + result + " (" + ms + "ms)");
            return result;
        }
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Proxy Pattern ===\n");

        // 1. Virtual Proxy (Lazy Loading)
        System.out.println("1. Virtual Proxy (Lazy Loading):");
        Image img1 = new LazyImageProxy("holiday-photo.jpg");
        Image img2 = new LazyImageProxy("portrait.png");
        System.out.println("  getInfo() bez ladowania: " + img1.getInfo());
        System.out.println("  Pierwsze display() — teraz laduje...");
        img1.display();
        System.out.println("  Drugie display() — juz zaladowane:");
        img1.display();

        // 2. Protection Proxy
        System.out.println("\n2. Protection Proxy:");
        DocumentService realSvc = new RealDocumentService();
        DocumentService adminSvc  = new ProtectionProxy(realSvc, "admin", Role.ADMIN);
        DocumentService userSvc   = new ProtectionProxy(realSvc, "alice", Role.USER);
        DocumentService guestSvc  = new ProtectionProxy(realSvc, "guest", Role.GUEST);

        System.out.println("  Admin czyta doc1: " + adminSvc.readDocument("doc1"));
        adminSvc.deleteDocument("doc2");
        System.out.println("  User czyta doc1: " + userSvc.readDocument("doc1"));
        try {
            guestSvc.readDocument("doc1");
        } catch (SecurityException e) {
            System.out.println("  BLAD (oczekiwany): " + e.getMessage());
        }
        try {
            userSvc.deleteDocument("doc1");
        } catch (SecurityException e) {
            System.out.println("  BLAD (oczekiwany): " + e.getMessage());
        }

        // 3. Caching Proxy
        System.out.println("\n3. Caching Proxy:");
        CachingPriceProxy priceProxy = new CachingPriceProxy(new SlowPriceService());
        System.out.println("  Cena A1: " + priceProxy.getPrice("A1"));
        System.out.println("  Cena A1: " + priceProxy.getPrice("A1")); // cache hit
        System.out.println("  Cena B2: " + priceProxy.getPrice("B2"));
        System.out.println("  Cena A1: " + priceProxy.getPrice("A1")); // cache hit
        priceProxy.printStats();

        // 4. Dynamic Proxy
        System.out.println("\n4. Dynamic Proxy (java.lang.reflect.Proxy):");
        PriceService logged = (PriceService) java.lang.reflect.Proxy.newProxyInstance(
                PriceService.class.getClassLoader(),
                new Class[]{PriceService.class},
                new LoggingHandler(new SlowPriceService())
        );
        logged.getPrice("A1");
    }
}

