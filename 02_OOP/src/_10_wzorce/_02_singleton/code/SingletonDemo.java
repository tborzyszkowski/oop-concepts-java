package _10_wzorce._02_singleton.code;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Singleton — trzy podejscia:
 * 1. Naiwne (nie thread-safe)
 * 2. Double-Checked Locking (thread-safe, lazy)
 * 3. Enum Singleton (najlepsze — Bloch Effective Java)
 */
public class SingletonDemo {

    // ─── 1. Naiwny singleton (NIE thread-safe) ───────────────────────────────
    static class NaiveSingleton {
        private static NaiveSingleton instance;
        private final int id;

        private NaiveSingleton() {
            this.id = System.identityHashCode(this);
        }

        // PROBLEM: dwa watki moga wejsc jednoczesnie i stworzyc dwa obiekty!
        public static NaiveSingleton getInstance() {
            if (instance == null) {             // race condition tutaj!
                instance = new NaiveSingleton();
            }
            return instance;
        }

        public int getId() { return id; }
    }

    // ─── 2. Double-Checked Locking (thread-safe, lazy) ───────────────────────
    static class SafeSingleton {
        // volatile: gwarantuje widocznosc i zakaz reorderowania
        private static volatile SafeSingleton instance;
        private final String config;

        private SafeSingleton(String config) {
            this.config = config;
            System.out.println("  SafeSingleton created (config=" + config + ")");
        }

        public static SafeSingleton getInstance() {
            if (instance == null) {                         // sprawdzenie bez locka (szybka sciezka)
                synchronized (SafeSingleton.class) {
                    if (instance == null) {                 // sprawdzenie z lockiem (raz!)
                        instance = new SafeSingleton("production");
                    }
                }
            }
            return instance;
        }

        public String getConfig() { return config; }
    }

    // ─── 3. Holder idiom (lazy, thread-safe, brak synchronizacji) ────────────
    static class HolderSingleton {
        private final long createdAt = System.currentTimeMillis();

        private HolderSingleton() {}

        // Inner class ladowana leniwie przy pierwszym uzyciu
        private static final class Holder {
            static final HolderSingleton INSTANCE = new HolderSingleton();
        }

        public static HolderSingleton getInstance() {
            return Holder.INSTANCE;    // ClassLoader gwarantuje thread-safety!
        }

        public long getCreatedAt() { return createdAt; }
    }

    // ─── 4. Enum Singleton (Joshua Bloch — najbezpieczniejsze) ──────────────
    enum AppConfig {
        INSTANCE;

        private String databaseUrl = "jdbc:postgresql://localhost/mydb";
        private int maxConnections = 10;

        public String getDatabaseUrl() { return databaseUrl; }
        public int getMaxConnections() { return maxConnections; }
        public void setDatabaseUrl(String url) { this.databaseUrl = url; }

        @Override
        public String toString() {
            return "AppConfig{db=" + databaseUrl + ", maxConn=" + maxConnections + "}";
        }
    }

    // ─── DEMO ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Singleton Pattern ===\n");

        // 1. Safe singleton — wiele watkow, ten sam obiekt
        System.out.println("1. SafeSingleton — Double-Checked Locking:");
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.submit(() -> {
                SafeSingleton s = SafeSingleton.getInstance();
                System.out.println("  Watek " + Thread.currentThread().getName()
                        + " → config=" + s.getConfig()
                        + " hash=" + System.identityHashCode(s));
            });
        }
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);

        // 2. Holder idiom
        System.out.println("\n2. HolderSingleton:");
        HolderSingleton h1 = HolderSingleton.getInstance();
        HolderSingleton h2 = HolderSingleton.getInstance();
        System.out.println("  h1 == h2: " + (h1 == h2));
        System.out.println("  Created at: " + h1.getCreatedAt());

        // 3. Enum Singleton
        System.out.println("\n3. Enum Singleton (Bloch):");
        AppConfig cfg = AppConfig.INSTANCE;
        System.out.println("  " + cfg);
        cfg.setDatabaseUrl("jdbc:mysql://prod-server/mydb");
        System.out.println("  After change: " + AppConfig.INSTANCE);  // ten sam obiekt!

        // 4. Enum jest serializowalny i odporny na reflection
        System.out.println("\n4. Enum Singleton a naiwny — porownanie:");
        System.out.println("  Naiwny moze byc zlamany przez: reflection, serializacje, klonowanie");
        System.out.println("  Enum jest odporny na wszystkie te ataki (gwarantuje JVM)");

        System.out.println("\n=== Kiedy uzywac Singleton? ===");
        System.out.println("  ✓ Konfiguracja aplikacji (jednorazowo ladowana)");
        System.out.println("  ✓ Pula zasobow (connection pool, thread pool)");
        System.out.println("  ✓ Cache globalny");
        System.out.println("  ✗ Zastepstwo dla globalnych zmiennych (anty-wzorzec)");
        System.out.println("  ✗ Klasy z mutowalnym stanem (utrudnia testowanie)");
    }
}

