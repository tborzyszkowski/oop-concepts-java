package _07_watki._05_tworzenie_watku.code;

import java.util.concurrent.*;

/**
 * ThreadCreationDemo — trzy sposoby tworzenia wątków:
 *   1. extends Thread
 *   2. implements Runnable
 *   3. Lambda / ExecutorService (Java 5+)
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_05_tworzenie_watku/code/ThreadCreationDemo.java
 *   java  -cp out _07_watki._05_tworzenie_watku.code.ThreadCreationDemo
 */

// =========================================================
// Sposób 1: extends Thread
// =========================================================
class CounterThread extends Thread {
    private final int from;
    private final int to;
    private long result;

    CounterThread(String name, int from, int to) {
        super(name);   // ustawia nazwę wątku
        this.from = from;
        this.to   = to;
    }

    @Override
    public void run() {
        result = 0;
        for (int i = from; i < to; i++) result += i;
        System.out.printf("  [%s] suma[%d,%d) = %d%n", getName(), from, to, result);
    }

    public long getResult() { return result; }
}

// =========================================================
// Sposób 2: implements Runnable
// =========================================================
class CounterRunnable implements Runnable {
    private final int from;
    private final int to;
    private long result;
    private final String name;

    CounterRunnable(String name, int from, int to) {
        this.name = name;
        this.from = from;
        this.to   = to;
    }

    @Override
    public void run() {
        result = 0;
        for (int i = from; i < to; i++) result += i;
        System.out.printf("  [%s] suma[%d,%d) = %d%n",
                Thread.currentThread().getName(), from, to, result);
    }

    public long getResult() { return result; }
}

// =========================================================
// Demo klas rozszerzalnych
// =========================================================
class ExtendableCounter extends CounterThread {
    private final String prefix;

    ExtendableCounter(String name, String prefix, int from, int to) {
        super(name, from, to);
        this.prefix = prefix;
    }

    @Override
    public void run() {
        System.out.println("  [ExtendableCounter] " + prefix + " — start");
        super.run();
        System.out.println("  [ExtendableCounter] " + prefix + " — koniec");
    }
}

// Runnable + dziedziczenie od czegoś innego — niemożliwe z extends Thread
class BusinessObject {
    protected String id;
    BusinessObject(String id) { this.id = id; }
    String getInfo() { return "BusinessObject[" + id + "]"; }
}

class RunnableBusinessObject extends BusinessObject implements Runnable {
    RunnableBusinessObject(String id) { super(id); }

    @Override
    public void run() {
        System.out.println("  RunnableBO: " + getInfo()
                + " na wątku " + Thread.currentThread().getName());
    }
}

// =========================================================
// Main demo
// =========================================================
public class ThreadCreationDemo {

    static void part1_ExtendThread() throws InterruptedException {
        System.out.println("=== Sposób 1: extends Thread ===");
        CounterThread t1 = new CounterThread("T1-extends", 0, 1_000_000);
        CounterThread t2 = new CounterThread("T2-extends", 1_000_000, 2_000_000);
        t1.start(); t2.start();
        t1.join();  t2.join();
        System.out.println("  Łącznie: " + (t1.getResult() + t2.getResult()));

        System.out.println("  Rozszerzenie klasy Thread (dziedziczenie):");
        ExtendableCounter ec = new ExtendableCounter("EC", "PREF", 0, 100);
        ec.start();
        ec.join();
    }

    static void part2_ImplementRunnable() throws InterruptedException {
        System.out.println("\n=== Sposób 2: implements Runnable ===");
        CounterRunnable r1 = new CounterRunnable("R1", 0, 1_000_000);
        CounterRunnable r2 = new CounterRunnable("R2", 1_000_000, 2_000_000);

        Thread t1 = new Thread(r1, "Thread-R1");
        Thread t2 = new Thread(r2, "Thread-R2");
        t1.start(); t2.start();
        t1.join();  t2.join();
        System.out.println("  Łącznie: " + (r1.getResult() + r2.getResult()));

        System.out.println("  Runnable + dziedziczenie po klasie domeny:");
        RunnableBusinessObject rbo = new RunnableBusinessObject("BO-1");
        Thread t = new Thread(rbo, "BO-Thread");
        t.start();
        t.join();
    }

    static void part3_LambdaAndExecutor() throws Exception {
        System.out.println("\n=== Sposób 3: Lambda / ExecutorService ===");

        // Lambda jako Runnable
        Thread t = new Thread(() -> System.out.println("  Lambda: " + Thread.currentThread().getName()),
                "LambdaThread");
        t.start();
        t.join();

        // ExecutorService — pula wątków
        System.out.println("  ExecutorService (fixed pool 2):");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            final int id = i;
            pool.execute(() -> System.out.printf("    Zadanie %d: %s%n",
                    id, Thread.currentThread().getName()));
        }
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);

        // Callable + Future
        System.out.println("  Callable + Future:");
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<String> future = exec.submit(() -> {
            Thread.sleep(100);
            return "Wynik z Callable na " + Thread.currentThread().getName();
        });
        System.out.println("  " + future.get());
        exec.shutdown();
    }

    static void part4_Comparison() {
        System.out.println("\n=== Porównanie podejść ===");
        System.out.println("  extends Thread:");
        System.out.println("    + prosty — mniej kodu");
        System.out.println("    - blokuje dziedziczenie (Java single-inheritance)");
        System.out.println("    - logika i zarządzanie wątkiem w jednej klasie");
        System.out.println("  implements Runnable:");
        System.out.println("    + wolna dziedziczenie od dowolnej klasy");
        System.out.println("    + separacja logiki od wątku");
        System.out.println("    + ten sam Runnable można uruchomić wielokrotnie");
        System.out.println("    + kompatybilny z ExecutorService");
        System.out.println("  Lambda:");
        System.out.println("    + najzwięźlejszy zapis");
        System.out.println("    + idealny dla małych zadań");
        System.out.println("  ExecutorService:");
        System.out.println("    + produkcyjny — pule wątków, zarządzanie zasobami");
        System.out.println("    + Callable + Future — wyniki z wątków");
    }

    public static void main(String[] args) throws Exception {
        part1_ExtendThread();
        part2_ImplementRunnable();
        part3_LambdaAndExecutor();
        part4_Comparison();
    }
}

