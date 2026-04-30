package _07_watki._01_wprowadzenie.code;

import java.util.concurrent.*;

/**
 * ThreadingIntroDemo — wprowadzenie do programowania wielowątkowego.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_01_wprowadzenie/code/ThreadingIntroDemo.java
 *   java  -cp out _07_watki._01_wprowadzenie.code.ThreadingIntroDemo
 */
public class ThreadingIntroDemo {

    // -------------------------------------------------------
    // Część 1: Czemu wątki? — sekwencyjne vs równoległe
    // -------------------------------------------------------
    static long heavyWork(int from, int to) {
        long sum = 0;
        for (int i = from; i < to; i++) sum += (long) Math.sqrt(i);
        return sum;
    }

    static void part1_SequentialVsParallel() throws InterruptedException {
        System.out.println("=== Część 1: sekwencyjne vs równoległe ===");
        final int N = 100_000_000;

        // Sekwencyjne
        long t0 = System.currentTimeMillis();
        long r1 = heavyWork(0, N / 2);
        long r2 = heavyWork(N / 2, N);
        long seqTime = System.currentTimeMillis() - t0;
        System.out.printf("  Sekwencyjne : %d+%d=%d  czas=%dms%n", r1, r2, r1 + r2, seqTime);

        // Równoległe — dwa wątki
        long[] results = new long[2];
        Thread t1 = new Thread(() -> results[0] = heavyWork(0, N / 2));
        Thread t2 = new Thread(() -> results[1] = heavyWork(N / 2, N));
        t0 = System.currentTimeMillis();
        t1.start(); t2.start();
        t1.join();  t2.join();
        long parTime = System.currentTimeMillis() - t0;
        System.out.printf("  Równoległe  : %d+%d=%d  czas=%dms%n",
                results[0], results[1], results[0] + results[1], parTime);
        System.out.printf("  Przyśpieszenie: %.2fx%n", (double) seqTime / parTime);
    }

    // -------------------------------------------------------
    // Część 2: Najprostszy wątek — lambda jako Runnable
    // -------------------------------------------------------
    static void part2_SimpleThread() throws InterruptedException {
        System.out.println("\n=== Część 2: najprostszy wątek (lambda) ===");

        Thread t = new Thread(() -> {
            System.out.println("  [wątek] start, id=" + Thread.currentThread().getId());
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("  [wątek] koniec");
        }, "MójWątek");

        System.out.println("  Przed start()");
        t.start();
        System.out.println("  Po start() — wątek główny kontynuuje równolegle");
        t.join();
        System.out.println("  Po join() — wątek główny czeka na zakończenie dziecka");
    }

    // -------------------------------------------------------
    // Część 3: Alternatywy dla surowych wątków
    // -------------------------------------------------------
    static void part3_Alternatives() throws Exception {
        System.out.println("\n=== Część 3: alternatywy — ExecutorService ===");

        // ExecutorService — pula wątków (Java 5+)
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            pool.submit(() ->
                System.out.printf("  Zadanie %d wykonane przez %s%n",
                        taskId, Thread.currentThread().getName()));
        }
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        // Callable + Future — wątek z wynikiem
        System.out.println("  Future/Callable:");
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<Integer> future = exec.submit(() -> {
            Thread.sleep(100);
            return 42;
        });
        System.out.println("  Wynik Future: " + future.get());
        exec.shutdown();

        // Virtual threads (Java 21) — lekkie wątki
        System.out.println("  Wątki wirtualne (Java 21):");
        try (var vExec = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 3; i++) {
                final int id = i;
                vExec.submit(() ->
                    System.out.printf("  Wityualny wątek %d: %s%n",
                            id, Thread.currentThread().isVirtual() ? "virtual" : "platform"));
            }
        }
    }

    // -------------------------------------------------------
    // main
    // -------------------------------------------------------
    public static void main(String[] args) throws Exception {
        part1_SequentialVsParallel();
        part2_SimpleThread();
        part3_Alternatives();
        System.out.println("\nGotowe.");
    }
}

