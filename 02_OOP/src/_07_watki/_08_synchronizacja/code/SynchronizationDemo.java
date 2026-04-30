package _07_watki._08_synchronizacja.code;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SynchronizationDemo — sekcja krytyczna i synchronizacja.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_08_synchronizacja/code/SynchronizationDemo.java
 *   java  -cp out _07_watki._08_synchronizacja.code.SynchronizationDemo
 */
public class SynchronizationDemo {

    // =========================================================
    // Licznik bez synchronizacji — race condition
    // =========================================================
    static class UnsafeCounter {
        int count = 0;
        void increment() { count++; }   // read-increment-write — 3 kroki, nie atomowe
    }

    // =========================================================
    // Licznik z synchronized — bezpieczny
    // =========================================================
    static class SafeCounter {
        int count = 0;
        synchronized void increment() { count++; }
        synchronized int get()        { return count; }
    }

    // =========================================================
    // Licznik z AtomicInteger — najbardziej wydajny
    // =========================================================
    static class AtomicCounter {
        AtomicInteger count = new AtomicInteger(0);
        void increment()  { count.incrementAndGet(); }
        int  get()        { return count.get(); }
    }

    static final int ITERATIONS = 500_000;
    static final int THREADS    = 4;

    // -------------------------------------------------------
    // Część 1: demonstracja race condition
    // -------------------------------------------------------
    static void part1_RaceCondition() throws InterruptedException {
        System.out.println("=== Część 1: race condition (brak synchronizacji) ===");

        UnsafeCounter counter = new UnsafeCounter();
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) counter.increment();
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.printf("  Oczekiwane: %,d%n", (long) THREADS * ITERATIONS);
        System.out.printf("  Faktyczne:  %,d%n", counter.count);
        System.out.println("  Różnica wskazuje na utracone aktualizacje (lost updates).");
    }

    // -------------------------------------------------------
    // Część 2: naprawione synchronized
    // -------------------------------------------------------
    static void part2_Synchronized() throws InterruptedException {
        System.out.println("\n=== Część 2: synchronized — poprawne ===");

        SafeCounter counter = new SafeCounter();
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) counter.increment();
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.printf("  Oczekiwane: %,d%n", (long) THREADS * ITERATIONS);
        System.out.printf("  Faktyczne:  %,d%n", counter.get());
        System.out.println("  Wynik zawsze poprawny dzięki synchronized.");
    }

    // -------------------------------------------------------
    // Część 3: zmieszane operacje — read-modify-write
    // -------------------------------------------------------
    static class BankAccount {
        private double balance;

        BankAccount(double initial) { this.balance = initial; }

        // Bez synchronizacji — niebezpieczne
        void depositUnsafe(double amount) { balance += amount; }

        // Z synchronizacją — bezpieczne
        synchronized void deposit(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
            balance += amount;
        }

        synchronized void withdraw(double amount) {
            if (amount > balance) throw new IllegalStateException("Insufficient funds");
            balance -= amount;
        }

        synchronized double getBalance() { return balance; }
    }

    static void part3_BankAccount() throws InterruptedException {
        System.out.println("\n=== Część 3: konto bankowe — synchronized ===");

        BankAccount account = new BankAccount(0.0);
        Thread depositor = new Thread(() -> {
            for (int i = 0; i < 1000; i++) account.deposit(1.0);
        }, "Depositor");

        Thread withdrawer = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try { account.withdraw(0.5); }
                catch (IllegalStateException e) { /* może się zdarzyć przy współbieżnym dostępie */ }
            }
        }, "Withdrawer");

        depositor.start(); withdrawer.start();
        depositor.join();  withdrawer.join();

        System.out.printf("  Saldo końcowe: %.1f%n", account.getBalance());
        System.out.println("  (wynik deterministyczny dzięki synchronized)");
    }

    // -------------------------------------------------------
    // Część 4: AtomicInteger — lepsza wydajność
    // -------------------------------------------------------
    static void part4_Atomic() throws InterruptedException {
        System.out.println("\n=== Część 4: AtomicInteger — bez locków ===");

        AtomicCounter counter = new AtomicCounter();
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) counter.increment();
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.printf("  Oczekiwane: %,d%n", (long) THREADS * ITERATIONS);
        System.out.printf("  Faktyczne:  %,d%n", counter.get());
    }

    public static void main(String[] args) throws InterruptedException {
        part1_RaceCondition();
        part2_Synchronized();
        part3_BankAccount();
        part4_Atomic();
    }
}

