package _07_watki._02_watek_vs_proces.code;

/**
 * ProcessVsThreadDemo — ilustracja różnic wątek vs proces.
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_02_watek_vs_proces/code/ProcessVsThreadDemo.java
 *   java  -cp out _07_watki._02_watek_vs_proces.code.ProcessVsThreadDemo
 */
public class ProcessVsThreadDemo {

    // Współdzielona pamięć — wątki widzą TEN SAM obiekt
    static int sharedCounter = 0;

    static void part1_SharedMemory() throws InterruptedException {
        System.out.println("=== Część 1: wątki współdzielą pamięć ===");
        sharedCounter = 0;

        // Oba wątki odczytują i modyfikują TĘ SAMĄ zmienną sharedCounter
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50_000; i++) sharedCounter++;
        }, "T1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50_000; i++) sharedCounter++;
        }, "T2");

        t1.start(); t2.start();
        t1.join();  t2.join();

        // Wynik < 100 000 — wyścigu danych (race condition) bez synchronizacji
        System.out.println("  Oczekiwane: 100 000, faktyczne: " + sharedCounter);
        System.out.println("  (różnica wskazuje na wyścig danych — brak synchronizacji)");
    }

    // -------------------------------------------------------
    // Część 2: Wielozadaniowość bazująca na procesach
    // -------------------------------------------------------
    static void part2_ProcessMultitasking() throws Exception {
        System.out.println("\n=== Część 2: wielozadaniowość procesów (wywołanie zewnętrznego procesu) ===");
        // Uruchamiamy zewnętrzny proces jako demonstrację izolacji
        ProcessBuilder pb = new ProcessBuilder("java", "-version");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        String output = new String(p.getInputStream().readAllBytes());
        int exit = p.waitFor();
        System.out.println("  Zewnętrzny proces (java -version), exit=" + exit);
        System.out.println("  Output: " + output.trim());
        System.out.println("  Procesy mają ODDZIELNĄ przestrzeń adresową.");
    }

    // -------------------------------------------------------
    // Część 3: Wielozadaniowość bazująca na wątkach
    // -------------------------------------------------------
    static void part3_ThreadMultitasking() throws InterruptedException {
        System.out.println("\n=== Część 3: wielozadaniowość wątków ===");
        System.out.printf("  Wątek główny: %s (id=%d, isDaemon=%b)%n",
                Thread.currentThread().getName(),
                Thread.currentThread().getId(),
                Thread.currentThread().isDaemon());

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("  Liczba rdzeni CPU: " + cores);

        // Stworzenie kilku wątków w tej samej JVM — współdzielony stos systemowy
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                System.out.printf("    Wątek %d: id=%d, grupawAtek=%s%n",
                        id,
                        Thread.currentThread().getId(),
                        Thread.currentThread().getThreadGroup().getName());
            }, "Worker-" + i);
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
    }

    // -------------------------------------------------------
    // Część 4: Demon vs zwykły wątek
    // -------------------------------------------------------
    static void part4_DaemonVsUser() throws InterruptedException {
        System.out.println("\n=== Część 4: wątek Daemon vs User ===");

        Thread daemon = new Thread(() -> {
            System.out.println("  [daemon] start");
            try {
                while (true) {
                    Thread.sleep(50);
                    System.out.print("d");
                }
            } catch (InterruptedException e) {
                System.out.println("\n  [daemon] przerwany");
            }
        });
        daemon.setDaemon(true);   // ← ustawić PRZED start()
        daemon.start();

        Thread.sleep(120);
        System.out.println("\n  [main] kończę — daemon zakończy się automatycznie");
        // Wątek daemon nie blokuje JVM — skończy się gdy main() zakończy działanie
    }

    public static void main(String[] args) throws Exception {
        part1_SharedMemory();
        part2_ProcessMultitasking();
        part3_ThreadMultitasking();
        part4_DaemonVsUser();
    }
}

