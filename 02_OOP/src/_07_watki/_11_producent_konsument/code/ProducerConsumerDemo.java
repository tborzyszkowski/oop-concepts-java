package _07_watki._11_producent_konsument.code;

/**
 * ProducerConsumerDemo — producent–konsument z buforem jednoelementowym.
 *
 * Wizualizacja działania z pełnym śledzeniem każdego kroku.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_11_producent_konsument/code/ProducerConsumerDemo.java
 *   java  -cp out _07_watki._11_producent_konsument.code.ProducerConsumerDemo
 */

// =========================================================
// Bufor jednoelementowy — rdzeń wzorca
// =========================================================
class SingleBuffer {
    private int value;
    private boolean hasValue = false;
    private int putCount = 0;
    private int getCount = 0;

    /** Producent — wstawia element (blokuje gdy bufor pełny). */
    synchronized void put(int v) throws InterruptedException {
        while (hasValue) {
            System.out.printf("  [P]PUT blokuje  (bufor pełny, wartość=%d)%n", value);
            wait();
        }
        value    = v;
        hasValue = true;
        putCount++;
        System.out.printf("  [P]Produkuję  → %3d  (łącznie=%d)%n", v, putCount);
        notify();   // budzi konsumenta czekającego w get()
    }

    /** Konsument — pobiera element (blokuje gdy bufor pusty). */
    synchronized int get() throws InterruptedException {
        while (!hasValue) {
            System.out.printf("  [K]GET blokuje  (bufor pusty)%n");
            wait();
        }
        int v    = value;
        hasValue = false;
        getCount++;
        System.out.printf("  [K]Konsumuję  ← %3d  (łącznie=%d)%n", v, getCount);
        notify();   // budzi producenta czekającego w put()
        return v;
    }

    int getPutCount() { return putCount; }
    int getGetCount() { return getCount; }
}

// =========================================================
// Wersja błędna — bez wait/notify (tylko synchronized)
// =========================================================
class BufferNoWait {
    private int value;
    private boolean hasValue = false;

    synchronized void put(int v) {
        // Bez wait — pomijamy stare wartości gdy bufor "pełny"
        value    = v;
        hasValue = true;
    }

    synchronized int get() {
        // Bez wait — możemy pobrać tę samą wartość wielokrotnie
        System.out.printf("  [K]Konsumuję: %d (hasValue=%b)%n", value, hasValue);
        hasValue = false;
        return value;
    }
}

// =========================================================
// Główna klasa demo
// =========================================================
public class ProducerConsumerDemo {

    // -------------------------------------------------------
    // Wersja błędna — brak wait/notify
    // -------------------------------------------------------
    static void part1_WithoutWaitNotify() throws InterruptedException {
        System.out.println("=== Część 1: BEZ wait/notify (tylko synchronized) ===");
        BufferNoWait buf = new BufferNoWait();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                buf.put(i);
                System.out.printf("  [P]Produkuję: %d%n", i);
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }, "Producent");

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                buf.get();
                try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }, "Konsument");

        producer.start(); consumer.start();
        producer.join();  consumer.join();
        System.out.println("  → Konsument może zużyć tę samą wartość kilka razy lub pominąć!");
    }

    // -------------------------------------------------------
    // Wersja poprawna — z wait/notify
    // -------------------------------------------------------
    static void part2_WithWaitNotify() throws InterruptedException {
        System.out.println("\n=== Część 2: Z wait/notify — poprawna synchronizacja ===");

        // Ponowna implementacja inline dla zwięzłości
        final int[] buf     = {0};
        final boolean[] has = {false};
        final Object monitor = new Object();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    synchronized (monitor) {
                        while (has[0]) monitor.wait();   // czekaj gdy pełny
                        buf[0]  = i;
                        has[0]  = true;
                        System.out.printf("  [P] +%2d%n", i);
                        monitor.notify();
                    }
                    Thread.sleep(80);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Producent");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 8; i++) {
                    synchronized (monitor) {
                        while (!has[0]) monitor.wait();  // czekaj gdy pusty
                        System.out.printf("  [K] -%2d%n", buf[0]);
                        has[0] = false;
                        monitor.notify();
                    }
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Konsument");

        producer.start(); consumer.start();
        producer.join();  consumer.join();
        System.out.println("  → Każda wartość wyprodukowana dokładnie raz i skonsumowana dokładnie raz.");
    }

    // -------------------------------------------------------
    // Wersja z dwoma konsumentami (wymaga notifyAll!)
    // -------------------------------------------------------
    static void part3_TwoConsumers() throws InterruptedException {
        System.out.println("\n=== Część 3: 1 producent + 2 konsumentów (notifyAll) ===");

        final Object mon = new Object();
        final int[] buf  = {0};
        final boolean[] has = {false};

        Runnable consumerTask = () -> {
            String name = Thread.currentThread().getName();
            try {
                for (int i = 0; i < 4; i++) {
                    synchronized (mon) {
                        while (!has[0]) mon.wait();
                        System.out.printf("  [%s] konsumuje %d%n", name, buf[0]);
                        has[0] = false;
                        mon.notifyAll();    // budzi producenta I drugiego konsumenta
                    }
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        };

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    synchronized (mon) {
                        while (has[0]) mon.wait();
                        buf[0] = i;
                        has[0] = true;
                        System.out.printf("  [P] produkuje  %d%n", i);
                        mon.notifyAll();
                    }
                    Thread.sleep(80);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Producent");

        Thread c1 = new Thread(consumerTask, "K1");
        Thread c2 = new Thread(consumerTask, "K2");

        producer.start(); c1.start(); c2.start();
        producer.join();  c1.join();  c2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        part1_WithoutWaitNotify();
        part2_WithWaitNotify();
        part3_TwoConsumers();
        System.out.println("\nGotowe.");
    }
}


