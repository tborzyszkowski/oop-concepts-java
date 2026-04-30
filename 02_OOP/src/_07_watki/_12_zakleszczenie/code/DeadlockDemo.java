package _07_watki._12_zakleszczenie.code;

/**
 * DeadlockDemo — demonstracja zakleszczenia i techniki unikania.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_12_zakleszczenie/code/DeadlockDemo.java
 *   java  -cp out _07_watki._12_zakleszczenie.code.DeadlockDemo [tryfix]
 */
public class DeadlockDemo {

    // =========================================================
    // Klasyczny wzorzec zakleszczenia: dwa zasoby, dwie kolejności
    // =========================================================
    static class Resource {
        final String name;
        Resource(String name) { this.name = name; }
    }

    static final Resource RESOURCE_A = new Resource("A");
    static final Resource RESOURCE_B = new Resource("B");

    // -------------------------------------------------------
    // Zakleszczenie — wątek 1: A → B, wątek 2: B → A
    // -------------------------------------------------------
    static void deadlockThread1() {
        String tname = Thread.currentThread().getName();
        synchronized (RESOURCE_A) {
            System.out.println("  [" + tname + "] trzyma A, czeka na B...");
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            synchronized (RESOURCE_B) {
                System.out.println("  [" + tname + "] trzyma A i B");
            }
        }
    }

    static void deadlockThread2() {
        String tname = Thread.currentThread().getName();
        synchronized (RESOURCE_B) {
            System.out.println("  [" + tname + "] trzyma B, czeka na A...");
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            synchronized (RESOURCE_A) {
                System.out.println("  [" + tname + "] trzyma B i A");
            }
        }
    }

    // -------------------------------------------------------
    // Naprawione — jednolita kolejność blokowania
    // -------------------------------------------------------
    static void fixedThread(Resource first, Resource second, String label) {
        String tname = Thread.currentThread().getName();
        // Zawsze blokujemy w tej SAMEJ kolejności (A przed B)
        synchronized (first) {
            System.out.println("  [" + label + "] trzyma " + first.name + ", czeka na " + second.name);
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            synchronized (second) {
                System.out.println("  [" + label + "] trzyma " + first.name + " i " + second.name);
            }
        }
    }

    // =========================================================
    // Część 1: Demonstracja zakleszczenia (z timeoutem)
    // =========================================================
    static void part1_Deadlock() throws InterruptedException {
        System.out.println("=== Część 1: zakleszczenie (timeout 2s) ===");

        Thread t1 = new Thread(DeadlockDemo::deadlockThread1, "Wątek-1");
        Thread t2 = new Thread(DeadlockDemo::deadlockThread2, "Wątek-2");

        t1.start(); t2.start();

        // timeout — nie czekamy wiecznie
        t1.join(2000);
        t2.join(2000);

        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("  → ZAKLESZCZENIE — wątki nigdy się nie skończyły!");
            System.out.println("  → Przerywam wątki...");
            t1.interrupt(); t2.interrupt();
            t1.join(500);   t2.join(500);
        } else {
            System.out.println("  → Brak zakleszczenia (rzadkie przy tym kodzie).");
        }
    }

    // =========================================================
    // Część 2: Naprawione — stała kolejność blokowania
    // =========================================================
    static void part2_FixedOrdering() throws InterruptedException {
        System.out.println("\n=== Część 2: naprawione — jednolita kolejność ===");

        // Oba wątki: zawsze RESOURCE_A przed RESOURCE_B
        Thread t1 = new Thread(() -> fixedThread(RESOURCE_A, RESOURCE_B, "Fix-1"), "FIX-1");
        Thread t2 = new Thread(() -> fixedThread(RESOURCE_A, RESOURCE_B, "Fix-2"), "FIX-2");

        t1.start(); t2.start();
        t1.join(2000); t2.join(2000);

        System.out.println("  → Brak zakleszczenia! Oba wątki zakończyły się.");
    }

    // =========================================================
    // Część 3: tryLock (java.util.concurrent.locks) — bez blokowania
    // =========================================================
    static void part3_TryLock() throws InterruptedException {
        System.out.println("\n=== Część 3: tryLock — nieblokujące przejęcie zamka ===");

        java.util.concurrent.locks.ReentrantLock lockA = new java.util.concurrent.locks.ReentrantLock();
        java.util.concurrent.locks.ReentrantLock lockB = new java.util.concurrent.locks.ReentrantLock();

        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            for (int attempt = 1; attempt <= 10; attempt++) {
                boolean gotA = lockA.tryLock();
                if (gotA) {
                    try {
                        boolean gotB = lockB.tryLock();
                        if (gotB) {
                            try {
                                System.out.println("  [" + name + "] próba " + attempt + ": oba zamki — sekcja krytyczna");
                                Thread.sleep(30);
                                return;   // sukces — wychodzimy
                            } finally { lockB.unlock(); }
                        } else {
                            System.out.println("  [" + name + "] próba " + attempt + ": brak B — cofam A");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally { lockA.unlock(); }
                } else {
                    System.out.println("  [" + name + "] próba " + attempt + ": brak A");
                }
                try { Thread.sleep(20); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            System.out.println("  [" + name + "] nie udało się zdobyć obu zamków.");
        };

        Thread t1 = new Thread(task, "TL-1");
        Thread t2 = new Thread(task, "TL-2");
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("  → tryLock nigdy nie powoduje zakleszczenia.");
    }

    public static void main(String[] args) throws InterruptedException {
        part1_Deadlock();
        part2_FixedOrdering();
        part3_TryLock();
    }
}

