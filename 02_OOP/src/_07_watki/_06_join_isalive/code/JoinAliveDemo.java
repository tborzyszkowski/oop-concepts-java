package _07_watki._06_join_isalive.code;

/**
 * JoinAliveDemo — metody isAlive() i join().
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_06_join_isalive/code/JoinAliveDemo.java
 *   java  -cp out _07_watki._06_join_isalive.code.JoinAliveDemo
 */
public class JoinAliveDemo {

    static class Worker implements Runnable {
        final String name;
        final int sleepMs;
        volatile boolean done = false;

        Worker(String name, int sleepMs) {
            this.name    = name;
            this.sleepMs = sleepMs;
        }

        @Override
        public void run() {
            System.out.printf("  [%s] start%n", name);
            try { Thread.sleep(sleepMs); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            done = true;
            System.out.printf("  [%s] koniec (po %dms)%n", name, sleepMs);
        }
    }

    // -------------------------------------------------------
    // Część 1: isAlive() — sprawdzenie czy wątek żyje
    // -------------------------------------------------------
    static void part1_IsAlive() throws InterruptedException {
        System.out.println("=== Część 1: isAlive() ===");

        Worker w = new Worker("A", 300);
        Thread t = new Thread(w, "Thread-A");

        System.out.println("  Przed start(): isAlive=" + t.isAlive());   // false — NEW
        t.start();
        System.out.println("  Po start()   : isAlive=" + t.isAlive());   // true  — RUNNABLE
        Thread.sleep(400);   // czekamy dłużej niż sleepMs
        System.out.println("  Po 400ms     : isAlive=" + t.isAlive());   // false — TERMINATED
        t.join();
    }

    // -------------------------------------------------------
    // Część 2: join() — oczekiwanie na zakończenie
    // -------------------------------------------------------
    static void part2_Join() throws InterruptedException {
        System.out.println("\n=== Część 2: join() ===");

        Worker[] workers = {
            new Worker("W1", 100),
            new Worker("W2", 250),
            new Worker("W3", 180),
            new Worker("W4",  50)
        };
        Thread[] threads = new Thread[workers.length];

        for (int i = 0; i < workers.length; i++) {
            threads[i] = new Thread(workers[i], workers[i].name);
            threads[i].start();
        }

        System.out.println("  Wątki uruchomione, czekamy na zakończenie...");
        for (int i = 0; i < threads.length; i++) {
            System.out.println("  Przed join(" + workers[i].name + ")");
            threads[i].join();
            System.out.println("  Po    join(" + workers[i].name + ") isAlive=" + threads[i].isAlive());
        }
        System.out.println("  Wszystkie wątki zakończone.");
    }

    // -------------------------------------------------------
    // Część 3: join(timeout) — oczekiwanie z limitem
    // -------------------------------------------------------
    static void part3_JoinTimeout() throws InterruptedException {
        System.out.println("\n=== Część 3: join(timeout) ===");

        Thread slow = new Thread(() -> {
            try { Thread.sleep(2000); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "SlowThread");

        slow.start();

        System.out.println("  Czekamy max 300ms...");
        slow.join(300);   // czekamy co najwyżej 300ms

        if (slow.isAlive()) {
            System.out.println("  Timeout! Wątek nadal żyje, przerywamy go.");
            slow.interrupt();
        } else {
            System.out.println("  Wątek zakończył się przed timeoutem.");
        }
        slow.join();   // poczekaj aż interrupt zostanie obsłużony
    }

    // -------------------------------------------------------
    // Część 4: Fan-out + Fan-in (wzorzec równoległego przetwarzania)
    // -------------------------------------------------------
    static long computePartialSum(int from, int to) {
        long sum = 0;
        for (int i = from; i < to; i++) sum += i;
        return sum;
    }

    static void part4_FanOutFanIn() throws InterruptedException {
        System.out.println("\n=== Część 4: Fan-out + Fan-in ===");
        final int N = 10_000_000;
        final int PARTS = 4;
        long[] partials = new long[PARTS];
        Thread[] threads = new Thread[PARTS];

        // Fan-out: odpalamy PARTS wątków równolegle
        for (int i = 0; i < PARTS; i++) {
            final int idx     = i;
            final int fromVal = i * (N / PARTS);
            final int toVal   = (i + 1) * (N / PARTS);
            threads[i] = new Thread(() -> {
                partials[idx] = computePartialSum(fromVal, toVal);
            }, "Part-" + i);
            threads[i].start();
        }

        // Fan-in: czekamy na WSZYSTKIE wątki (join)
        for (Thread t : threads) t.join();

        // Agregacja wyników — bezpieczna, bo wszystkie wątki zakończone
        long total = 0;
        for (long p : partials) total += p;
        System.out.printf("  Suma[0,%d) = %d%n", N, total);
        System.out.printf("  Kontrola (n*(n-1)/2): %d%n", (long) N * (N - 1) / 2);
    }

    public static void main(String[] args) throws InterruptedException {
        part1_IsAlive();
        part2_Join();
        part3_JoinTimeout();
        part4_FanOutFanIn();
    }
}

