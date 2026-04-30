package _07_watki._13_filozofowie.code;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DiningPhilosophers — problem pięciu filozofów z kilkoma wariantami rozwiązania.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_13_filozofowie/code/DiningPhilosophers.java
 *   java  -cp out _07_watki._13_filozofowie.code.DiningPhilosophers
 */

// =========================================================
// Wariant 1: Zakleszczenie — każdy bierze lewą, potem prawą
// =========================================================
class DeadlockTable {
    static final int N = 5;
    private final ReentrantLock[] forks = new ReentrantLock[N];

    DeadlockTable() {
        for (int i = 0; i < N; i++) forks[i] = new ReentrantLock();
    }

    /** Wersja prowadząca do zakleszczenia — TYLKO DO DEMONSTRACJI. */
    void eatDeadlock(int id) throws InterruptedException {
        int left  = id;
        int right = (id + 1) % N;

        forks[left].lockInterruptibly();
        System.out.printf("[⚠] Filozof %d: wziął lewy widelec %d%n", id, left);
        try {
            Thread.sleep(50);   // daje czas innym na wzięcie swoich lewych
            forks[right].lockInterruptibly();
            try {
                System.out.printf("[✓] Filozof %d: je (widelce %d i %d)%n", id, left, right);
                Thread.sleep(100);
            } finally {
                forks[right].unlock();
            }
        } finally {
            forks[left].unlock();
        }
    }

    ReentrantLock[] getForks() { return forks; }
}

// =========================================================
// Wariant 2: Asymetryczna kolejność (jeden filozof odwrócony)
// =========================================================
class AsymmetricTable {
    static final int N = 5;
    private final ReentrantLock[] forks = new ReentrantLock[N];

    AsymmetricTable() {
        for (int i = 0; i < N; i++) forks[i] = new ReentrantLock();
    }

    void eat(int id) throws InterruptedException {
        // Jeden filozof (np. 0) bierze PRAWY WIDELEC PIERWSZY
        int first  = (id == 0) ? (id + 1) % N : id;
        int second = (id == 0) ? id             : (id + 1) % N;

        forks[first].lockInterruptibly();
        try {
            forks[second].lockInterruptibly();
            try {
                System.out.printf("[✓] Filozof %d: je (widelce %d i %d)%n", id, first, second);
                Thread.sleep(80 + new Random().nextInt(40));
            } finally { forks[second].unlock(); }
        } finally { forks[first].unlock(); }
    }
}

// =========================================================
// Wariant 3: tryLock z cofaniem (bez zakleszczenia)
// =========================================================
class TryLockTable {
    static final int N = 5;
    private final ReentrantLock[] forks = new ReentrantLock[N];

    TryLockTable() {
        for (int i = 0; i < N; i++) forks[i] = new ReentrantLock();
    }

    boolean tryEat(int id) throws InterruptedException {
        int left  = id;
        int right = (id + 1) % N;

        if (forks[left].tryLock()) {
            try {
                if (forks[right].tryLock()) {
                    try {
                        System.out.printf("[✓] Filozof %d: je (tryLock)%n", id);
                        Thread.sleep(80);
                        return true;
                    } finally { forks[right].unlock(); }
                }
            } finally { forks[left].unlock(); }
        }
        return false;   // nie udało się — wróć i pomyśl
    }
}

// =========================================================
// Klasy wątków filozofów
// =========================================================
class PhilosopherAsymmetric implements Runnable {
    private final int id;
    private final AsymmetricTable table;
    private final int meals;

    PhilosopherAsymmetric(int id, AsymmetricTable table, int meals) {
        this.id = id; this.table = table; this.meals = meals;
    }

    @Override
    public void run() {
        try {
            for (int m = 0; m < meals; m++) {
                System.out.printf("    Filozof %d: myśli%n", id);
                Thread.sleep(50 + new Random().nextInt(50));
                table.eat(id);
            }
            System.out.printf("    Filozof %d: skończył (zjedzone %d posiłki)%n", id, meals);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class PhilosopherTryLock implements Runnable {
    private final int id;
    private final TryLockTable table;
    private final int meals;
    private int eaten = 0;

    PhilosopherTryLock(int id, TryLockTable table, int meals) {
        this.id = id; this.table = table; this.meals = meals;
    }

    @Override
    public void run() {
        try {
            int attempts = 0;
            while (eaten < meals) {
                Thread.sleep(30 + new Random().nextInt(40));   // myślenie
                if (table.tryEat(id)) {
                    eaten++;
                } else {
                    attempts++;
                    if (attempts % 5 == 0)
                        System.out.printf("    Filozof %d: czekam (%d nieudanych prób)%n", id, attempts);
                    Thread.sleep(10 + new Random().nextInt(20));  // odczekaj przed retryem
                }
            }
            System.out.printf("    Filozof %d: skończył (%d posiłki)%n", id, meals);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// =========================================================
// Main demo
// =========================================================
public class DiningPhilosophers {

    static void runAsymmetric(int meals) throws InterruptedException {
        System.out.println("=== Rozwiązanie 1: Asymetryczna kolejność ===");
        AsymmetricTable table = new AsymmetricTable();
        Thread[] threads = new Thread[AsymmetricTable.N];

        for (int i = 0; i < AsymmetricTable.N; i++) {
            threads[i] = new Thread(new PhilosopherAsymmetric(i, table, meals),
                    "Filozof-" + i);
        }
        long t0 = System.currentTimeMillis();
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        System.out.printf("  Ukończone w %dms%n%n", System.currentTimeMillis() - t0);
    }

    static void runTryLock(int meals) throws InterruptedException {
        System.out.println("=== Rozwiązanie 2: tryLock z cofaniem ===");
        TryLockTable table = new TryLockTable();
        Thread[] threads = new Thread[TryLockTable.N];

        for (int i = 0; i < TryLockTable.N; i++) {
            threads[i] = new Thread(new PhilosopherTryLock(i, table, meals),
                    "Filozof-" + i);
        }
        long t0 = System.currentTimeMillis();
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        System.out.printf("  Ukończone w %dms%n%n", System.currentTimeMillis() - t0);
    }

    static void demonstrateDeadlock() throws InterruptedException {
        System.out.println("=== Demonstracja zakleszczenia (timeout 2s) ===");
        DeadlockTable table = new DeadlockTable();
        Thread[] threads = new Thread[DeadlockTable.N];

        for (int i = 0; i < DeadlockTable.N; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                try { table.eatDeadlock(id); }
                catch (InterruptedException e) {
                    System.out.printf("  Filozof %d: przerwany (zakleszczenie)%n", id);
                    Thread.currentThread().interrupt();
                }
            }, "F-" + i);
        }

        for (Thread t : threads) t.start();
        Thread.sleep(2000);   // czekamy max 2s

        boolean anyAlive = false;
        for (Thread t : threads) if (t.isAlive()) anyAlive = true;

        if (anyAlive) {
            System.out.println("  → ZAKLESZCZENIE — przerywamy wątki");
            for (Thread t : threads) t.interrupt();
        }
        for (Thread t : threads) t.join(500);
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        demonstrateDeadlock();
        runAsymmetric(3);
        runTryLock(3);
        System.out.println("Wszystkie filozofy najedzone.");
    }
}

