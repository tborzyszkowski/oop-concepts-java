package _07_watki._07_priorytety_volatile.code;

/**
 * PriorityVolatileDemo — priorytety wątków i słowo kluczowe volatile.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_07_priorytety_volatile/code/PriorityVolatileDemo.java
 *   java  -cp out _07_watki._07_priorytety_volatile.code.PriorityVolatileDemo
 */
public class PriorityVolatileDemo {

    // =========================================================
    // Kliker — wątek zliczający iteracje przez określony czas
    // =========================================================
    static class Clicker implements Runnable {
        long clicks = 0;
        // volatile — zapis w jednym wątku widoczny natychmiast w innym
        private volatile boolean running = true;
        private final Thread thread;

        Clicker(int priority) {
            thread = new Thread(this);
            thread.setPriority(priority);
        }

        @Override
        public void run() {
            while (running) clicks++;
        }

        void start()                          { thread.start(); }
        void stopAndWait() throws InterruptedException {
            running = false;           // atomowe zapisanie przez volatile
            thread.join();
        }
        int getPriority() { return thread.getPriority(); }
    }

    // -------------------------------------------------------
    // Część 1: Trzy poziomy priorytetów — porównanie
    // -------------------------------------------------------
    static void part1_PriorityComparison() throws InterruptedException {
        System.out.println("=== Część 1: porównanie priorytetów (5 sekund) ===");
        System.out.println("  MIN=" + Thread.MIN_PRIORITY
                + " NORM=" + Thread.NORM_PRIORITY
                + " MAX=" + Thread.MAX_PRIORITY);

        final int N = 6;   // wątki w każdej grupie
        Clicker[] lo  = new Clicker[N];
        Clicker[] mid = new Clicker[N];
        Clicker[] hi  = new Clicker[N];

        for (int i = 0; i < N; i++) {
            lo[i]  = new Clicker(Thread.MIN_PRIORITY);     // 1
            mid[i] = new Clicker(Thread.NORM_PRIORITY);    // 5
            hi[i]  = new Clicker(Thread.MAX_PRIORITY);     // 10
        }

        // Wątek główny na MAX — zapewnia że pomiar finish się po mierzonym czasie
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        for (int i = 0; i < N; i++) { lo[i].start(); mid[i].start(); hi[i].start(); }
        System.out.println("  Mierzę przez 3 sekundy...");
        Thread.sleep(3000);

        // Zatrzymanie wszystkich
        for (int i = 0; i < N; i++) { lo[i].stopAndWait(); mid[i].stopAndWait(); hi[i].stopAndWait(); }

        long loSum = 0, midSum = 0, hiSum = 0;
        for (int i = 0; i < N; i++) { loSum += lo[i].clicks; midSum += mid[i].clicks; hiSum += hi[i].clicks; }
        long total = loSum + midSum + hiSum;

        System.out.printf("  lo  (p=%d): suma=%,d  (%.1f%%)%n", Thread.MIN_PRIORITY,  loSum,  100.0 * loSum  / total);
        System.out.printf("  mid (p=%d): suma=%,d  (%.1f%%)%n", Thread.NORM_PRIORITY, midSum, 100.0 * midSum / total);
        System.out.printf("  hi  (p=%d): suma=%,d  (%.1f%%)%n", Thread.MAX_PRIORITY,  hiSum,  100.0 * hiSum  / total);
        System.out.println("  Uwaga: wyniki zależą od systemu OS, liczby rdzeni i schedulera JVM.");
    }

    // -------------------------------------------------------
    // Część 2: volatile — widoczność zmian między wątkami
    // -------------------------------------------------------
    static void part2_Volatile() throws InterruptedException {
        System.out.println("\n=== Część 2: rola volatile — widoczność zmian ===");

        // Bez volatile — flaga może być keszowana lokalnie przez JIT
        // Z volatile — gwarancja natychmiastowego zapisu do pamięci głównej
        class VolatileDemo {
            volatile boolean stop = false;   // ← kluczowe!
            long count = 0;

            void run(long durationMs) throws InterruptedException {
                Thread worker = new Thread(() -> {
                    while (!stop) count++;   // odczytuje stop z pamięci głównej
                });
                worker.start();
                Thread.sleep(durationMs);
                stop = true;               // zapisuje do pamięci głównej
                worker.join();
                System.out.printf("  Wątek policzył %,d iteracji w %dms%n", count, durationMs);
            }
        }

        new VolatileDemo().run(500);
    }

    // -------------------------------------------------------
    // Część 3: volatile NIE zastępuje synchronizacji
    // -------------------------------------------------------
    static void part3_VolatileNotEnough() throws InterruptedException {
        System.out.println("\n=== Część 3: volatile – nie zastępuje synchronized ===");

        // volatile counter — widoczność OK, ale nie-atomowość: count++ to 3 operacje
        class VolatileCounter {
            volatile int count = 0;
            void increment() { count++; }   // read-increment-write — nie atomowe!
        }

        VolatileCounter vc = new VolatileCounter();
        Thread t1 = new Thread(() -> { for (int i = 0; i < 100_000; i++) vc.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 100_000; i++) vc.increment(); });
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("  Bez sync:  oczekiwane=200 000, faktyczne=" + vc.count);
        System.out.println("  (wynik < 200 000 wskazuje na race condition)");

        // Poprawna wersja: AtomicInteger (lepsza) lub synchronized
        java.util.concurrent.atomic.AtomicInteger atomicCount = new java.util.concurrent.atomic.AtomicInteger(0);
        Thread t3 = new Thread(() -> { for (int i = 0; i < 100_000; i++) atomicCount.incrementAndGet(); });
        Thread t4 = new Thread(() -> { for (int i = 0; i < 100_000; i++) atomicCount.incrementAndGet(); });
        t3.start(); t4.start(); t3.join(); t4.join();
        System.out.println("  AtomicInt: oczekiwane=200 000, faktyczne=" + atomicCount.get());
    }

    public static void main(String[] args) throws InterruptedException {
        part1_PriorityComparison();
        part2_Volatile();
        part3_VolatileNotEnough();
    }
}

