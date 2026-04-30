package _07_watki._09_synchronized.code;

/**
 * SynchronizedBlockDemo — różne formy słowa kluczowego synchronized.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_09_synchronized/code/SynchronizedBlockDemo.java
 *   java  -cp out _07_watki._09_synchronized.code.SynchronizedBlockDemo
 */
public class SynchronizedBlockDemo {

    // =========================================================
    // Część 1: synchronized method vs no sync — efekt wizualny
    // =========================================================
    static class Printer {
        // Bez synchronizacji — wyniki się mieszają
        void printUnsynchronized(String bracket1, String word, String bracket2) {
            System.out.print(bracket1 + word);
            try { Thread.sleep(80); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println(bracket2);
        }

        // Z synchronized — każde wywołanie niepodzielne
        synchronized void printSynchronized(String bracket1, String word, String bracket2) {
            System.out.print(bracket1 + word);
            try { Thread.sleep(80); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println(bracket2);
        }
    }

    static void part1_MethodVsNoSync() throws InterruptedException {
        System.out.println("=== Część 1: bez synchronizacji ===");
        Printer p = new Printer();
        Thread t1 = new Thread(() -> p.printUnsynchronized("{", "Witaj",         "}"));
        Thread t2 = new Thread(() -> p.printUnsynchronized("[", "Synchronizacja","["));
        Thread t3 = new Thread(() -> p.printUnsynchronized("(", "Świecie",       ")"));
        long t0 = System.currentTimeMillis();
        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();
        System.out.println("  Czas: " + (System.currentTimeMillis() - t0) + "ms");

        System.out.println("\n--- Z synchronized method ---");
        Thread t4 = new Thread(() -> p.printSynchronized("{", "Witaj",         "}"));
        Thread t5 = new Thread(() -> p.printSynchronized("[", "Synchronizacja","["));
        Thread t6 = new Thread(() -> p.printSynchronized("(", "Świecie",       ")"));
        t0 = System.currentTimeMillis();
        t4.start(); t5.start(); t6.start();
        t4.join(); t5.join(); t6.join();
        System.out.println("  Czas: " + (System.currentTimeMillis() - t0) + "ms (3×80ms — sekwencja)");
    }

    // =========================================================
    // Część 2: synchronized block (zewnętrzny obiekt)
    // =========================================================
    static class PrinterExternal {
        // Metoda bez synchronized — synchronizacja po stronie wywołującego
        void print(String bracket1, String word, String bracket2) {
            System.out.print(bracket1 + word);
            try { Thread.sleep(80); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println(bracket2);
        }
    }

    static class CallerWithBlock implements Runnable {
        final PrinterExternal target;
        final String b1, word, b2;

        CallerWithBlock(PrinterExternal t, String b1, String w, String b2) {
            this.target = t; this.b1 = b1; this.word = w; this.b2 = b2;
        }

        @Override
        public void run() {
            synchronized (target) {      // blokada na obiekcie
                target.print(b1, word, b2);
            }
        }
    }

    static void part2_SynchronizedBlock() throws InterruptedException {
        System.out.println("\n=== Część 2: synchronized block (na obiekcie) ===");
        PrinterExternal p = new PrinterExternal();
        Thread t1 = new Thread(new CallerWithBlock(p, "{", "Witaj",         "}"));
        Thread t2 = new Thread(new CallerWithBlock(p, "[", "Synchronizacja","["));
        Thread t3 = new Thread(new CallerWithBlock(p, "(", "Świecie",       ")"));
        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();
    }

    // =========================================================
    // Część 3: synchronized static — blokada na klasie
    // =========================================================
    static class Registry {
        private static int count = 0;

        static synchronized void register()   { count++; }
        static synchronized int  getCount()   { return count; }
    }

    static void part3_StaticSync() throws InterruptedException {
        System.out.println("\n=== Część 3: synchronized static (lock na java.lang.Class) ===");
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10_000; j++) Registry.register();
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        System.out.println("  count = " + Registry.getCount() + " (oczekiwane: " + 10 * 10_000 + ")");
    }

    // =========================================================
    // Część 4: synchronized(this) vs synchronized(prywatny lock)
    // =========================================================
    static class TwoOperations {
        private final Object lockA = new Object();
        private final Object lockB = new Object();
        int dataA = 0;
        int dataB = 0;

        // Niezależne zamki — operacje na A i B mogą być równoległe
        void updateA() {
            synchronized (lockA) { dataA++; }
        }
        void updateB() {
            synchronized (lockB) { dataB++; }
        }

        // synchronized(this) — blokuje wszystko na tym samym obiekcie
        synchronized void updateAThis() { dataA++; }
        synchronized void updateBThis() { dataB++; }
    }

    static long measureParallel(boolean separateLocks) throws InterruptedException {
        TwoOperations obj = new TwoOperations();
        Thread ta, tb;
        if (separateLocks) {
            ta = new Thread(() -> { for (int i = 0; i < 500_000; i++) obj.updateA(); });
            tb = new Thread(() -> { for (int i = 0; i < 500_000; i++) obj.updateB(); });
        } else {
            ta = new Thread(() -> { for (int i = 0; i < 500_000; i++) obj.updateAThis(); });
            tb = new Thread(() -> { for (int i = 0; i < 500_000; i++) obj.updateBThis(); });
        }
        long t0 = System.currentTimeMillis();
        ta.start(); tb.start(); ta.join(); tb.join();
        return System.currentTimeMillis() - t0;
    }

    static void part4_LockGranularity() throws InterruptedException {
        System.out.println("\n=== Część 4: granularność zamków ===");
        long timeThis  = measureParallel(false);
        long timeSep   = measureParallel(true);
        System.out.println("  synchronized(this)       : " + timeThis + "ms");
        System.out.println("  Osobne zamki (lockA/lockB): " + timeSep  + "ms");
        System.out.println("  Osobne zamki są szybsze bo A i B nie blokują się nawzajem.");
    }

    public static void main(String[] args) throws InterruptedException {
        part1_MethodVsNoSync();
        part2_SynchronizedBlock();
        part3_StaticSync();
        part4_LockGranularity();
    }
}

