package _07_watki._03_cykl_zycia.code;

import java.util.concurrent.locks.LockSupport;

/**
 * ThreadLifecycleDemo — demonstracja stanów cyklu życia wątku.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_03_cykl_zycia/code/ThreadLifecycleDemo.java
 *   java  -cp out _07_watki._03_cykl_zycia.code.ThreadLifecycleDemo
 */
public class ThreadLifecycleDemo {

    static void printState(Thread t, String label) {
        System.out.printf("  %-30s → %s%n", label, t.getState());
    }

    // -------------------------------------------------------
    // Część 1: NEW → RUNNABLE → TERMINATED
    // -------------------------------------------------------
    static void part1_BasicLifecycle() throws InterruptedException {
        System.out.println("=== Część 1: NEW → RUNNABLE → TERMINATED ===");

        Thread t = new Thread(() -> {
            // RUNNABLE (jeśli aktywnie wykonuje)
            long sum = 0;
            for (int i = 0; i < 10_000_000; i++) sum += i;
            System.out.println("  [wątek] suma=" + sum);
        }, "Worker");

        printState(t, "Po new Thread() — przed start()");   // NEW
        t.start();
        printState(t, "Po start()");                         // RUNNABLE
        t.join();
        printState(t, "Po join()");                          // TERMINATED
    }

    // -------------------------------------------------------
    // Część 2: TIMED_WAITING (Thread.sleep)
    // -------------------------------------------------------
    static void part2_TimedWaiting() throws InterruptedException {
        System.out.println("\n=== Część 2: TIMED_WAITING (sleep) ===");

        Thread t = new Thread(() -> {
            try { Thread.sleep(3000); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Sleeper");

        t.start();
        Thread.sleep(50);   // daj wątkowi czas wejść do sleep
        printState(t, "Podczas sleep(3000)");  // TIMED_WAITING
        t.interrupt();      // przerwij sleep
        t.join();
        printState(t, "Po interrupt()");       // TERMINATED
    }

    // -------------------------------------------------------
    // Część 3: BLOCKED (oczekiwanie na monitor)
    // -------------------------------------------------------
    static final Object LOCK = new Object();

    static void part3_Blocked() throws InterruptedException {
        System.out.println("\n=== Część 3: BLOCKED (oczekiwanie na synchronized) ===");

        // Wątek 1 zajmuje zamek na 500ms
        Thread holder = new Thread(() -> {
            synchronized (LOCK) {
                try { Thread.sleep(500); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }, "LockHolder");

        // Wątek 2 próbuje wejść do synchronized bloku — będzie BLOCKED
        Thread waiter = new Thread(() -> {
            synchronized (LOCK) { /* nic */ }
        }, "LockWaiter");

        holder.start();
        Thread.sleep(30);   // czekamy aż holder wejdzie do bloku
        waiter.start();
        Thread.sleep(30);   // czekamy aż waiter spróbuje wejść

        printState(holder, "LockHolder (trzyma zamek)");  // TIMED_WAITING
        printState(waiter, "LockWaiter (czeka na zamek)"); // BLOCKED

        holder.join();
        waiter.join();
    }

    // -------------------------------------------------------
    // Część 4: WAITING (Object.wait)
    // -------------------------------------------------------
    static void part4_Waiting() throws InterruptedException {
        System.out.println("\n=== Część 4: WAITING (wait/notify) ===");

        Object monitor = new Object();

        Thread waiting = new Thread(() -> {
            synchronized (monitor) {
                try { monitor.wait(); }   // WAITING — bez limitu czasu
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }, "WaitingThread");

        waiting.start();
        Thread.sleep(50);
        printState(waiting, "Podczas wait()");   // WAITING

        synchronized (monitor) { monitor.notify(); }
        waiting.join();
        printState(waiting, "Po notify()");      // TERMINATED
    }

    // -------------------------------------------------------
    // Część 5: Podsumowanie wszystkich stanów
    // -------------------------------------------------------
    static void part5_Summary() {
        System.out.println("\n=== Część 5: podsumowanie stanów Thread.State ===");
        for (Thread.State s : Thread.State.values()) {
            System.out.println("  " + s + " — " + switch (s) {
                case NEW            -> "wątek utworzony, start() nie wywołany";
                case RUNNABLE       -> "wykonuje się lub gotowy do wykonania";
                case BLOCKED        -> "czeka na zamek synchronized";
                case WAITING        -> "czeka na notify() / join() bez limitu";
                case TIMED_WAITING  -> "czeka z limitem czasu (sleep, wait(ms))";
                case TERMINATED     -> "run() zakończone";
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        part1_BasicLifecycle();
        part2_TimedWaiting();
        part3_Blocked();
        part4_Waiting();
        part5_Summary();
    }
}

