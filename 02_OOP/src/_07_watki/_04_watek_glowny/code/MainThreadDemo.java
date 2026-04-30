package _07_watki._04_watek_glowny.code;

/**
 * MainThreadDemo — wątek główny i metoda currentThread().
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_04_watek_glowny/code/MainThreadDemo.java
 *   java  -cp out _07_watki._04_watek_glowny.code.MainThreadDemo
 */
public class MainThreadDemo {

    static void part1_CurrentThread() throws InterruptedException {
        System.out.println("=== Część 1: Thread.currentThread() ===");

        Thread main = Thread.currentThread();
        System.out.println("  Nazwa         : " + main.getName());
        System.out.println("  ID            : " + main.getId());
        System.out.println("  Priorytet     : " + main.getPriority());
        System.out.println("  Daemon        : " + main.isDaemon());
        System.out.println("  Stan          : " + main.getState());
        System.out.println("  Grupa wątków  : " + main.getThreadGroup().getName());
        System.out.println("  toString()    : " + main);

        // Zmiana nazwy
        main.setName("WątekGłówny");
        System.out.println("  Po setName()  : " + main.getName());

        // Uśpienie wątku głównego
        System.out.println("  Śpię 300ms...");
        Thread.sleep(300);
        System.out.println("  Obudzono.");
    }

    // -------------------------------------------------------
    // Część 2: currentThread() z różnych metod i wątków
    // -------------------------------------------------------
    static void whoAmI(String label) {
        Thread t = Thread.currentThread();
        System.out.printf("  %-20s → wątek: %-20s (id=%d)%n",
                label, t.getName(), t.getId());
    }

    static void methodA() { whoAmI("methodA"); }
    static void methodB() { whoAmI("methodB"); methodA(); }

    static void part2_CalledFromDifferentThreads() throws InterruptedException {
        System.out.println("\n=== Część 2: currentThread() w metodach ===");

        // Wywołanie z wątku głównego
        methodB();

        // Wywołanie z innego wątku
        Thread worker = new Thread(() -> methodB(), "WorkerThread");
        worker.start();
        worker.join();
    }

    // -------------------------------------------------------
    // Część 3: Obsługa InterruptedException — przerwanie wątku głównego
    // -------------------------------------------------------
    static void part3_Interrupt() throws InterruptedException {
        System.out.println("\n=== Część 3: przerwanie wątku głównego ===");

        Thread main = Thread.currentThread();

        // Wątek pomocniczy przerwie wątek główny po 200ms
        Thread interruptor = new Thread(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { return; }
            main.interrupt();
            System.out.println("  [interruptor] wysłano interrupt() do " + main.getName());
        });
        interruptor.start();

        try {
            System.out.println("  Próba sleep(5000ms)...");
            Thread.sleep(5000);
            System.out.println("  Obudzono normalnie (nie powinno się pojawić)");
        } catch (InterruptedException e) {
            System.out.println("  InterruptedException — wątek został przerwany!");
            System.out.println("  isInterrupted() po catch: " + Thread.currentThread().isInterrupted());
        }

        interruptor.join();
    }

    // -------------------------------------------------------
    // Część 4: Grupy wątków
    // -------------------------------------------------------
    static void part4_ThreadGroups() throws InterruptedException {
        System.out.println("\n=== Część 4: grupy wątków ===");

        ThreadGroup ioGroup  = new ThreadGroup("IO-Group");
        ThreadGroup cpuGroup = new ThreadGroup("CPU-Group");

        Thread t1 = new Thread(ioGroup,  () -> {}, "io-1");
        Thread t2 = new Thread(ioGroup,  () -> {}, "io-2");
        Thread t3 = new Thread(cpuGroup, () -> {}, "cpu-1");

        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();

        System.out.println("  IO-Group  aktywne wątki: " + ioGroup.activeCount());
        System.out.println("  CPU-Group aktywne wątki: " + cpuGroup.activeCount());
        System.out.println("  Grupy pozwalają zbiorowo zarządzać (interrupt, enumerate, ...)");
    }

    public static void main(String[] args) throws InterruptedException {
        part1_CurrentThread();
        part2_CalledFromDifferentThreads();
        part3_Interrupt();
        part4_ThreadGroups();
    }
}

