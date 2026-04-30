package _07_watki._10_wait_notify.code;

/**
 * WaitNotifyDemo — komunikacja wątków: wait(), notify(), notifyAll().
 *
 * Uruchomienie:
 *   javac -d out _07_watki/_10_wait_notify/code/WaitNotifyDemo.java
 *   java  -cp out _07_watki._10_wait_notify.code.WaitNotifyDemo
 */
public class WaitNotifyDemo {

    // =========================================================
    // Część 1: Prosty sygnał gotowości
    // =========================================================
    static class ReadyFlag {
        private boolean ready = false;

        synchronized void setReady() {
            System.out.println("  [setter] ustawiam gotowość");
            ready = true;
            notify();   // budzi jeden czekający wątek
        }

        synchronized void awaitReady() throws InterruptedException {
            System.out.println("  [waiter] czekam na gotowość...");
            while (!ready) {      // ZAWSZE while, nie if! (spurious wakeups)
                wait();
            }
            System.out.println("  [waiter] gotowość osiągnięta!");
        }
    }

    static void part1_SimpleSignal() throws InterruptedException {
        System.out.println("=== Część 1: prosty sygnał (wait/notify) ===");
        ReadyFlag flag = new ReadyFlag();

        Thread waiter = new Thread(() -> {
            try { flag.awaitReady(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Waiter");

        waiter.start();
        Thread.sleep(300);   // waiter wejdzie w wait()
        flag.setReady();
        waiter.join();
    }

    // =========================================================
    // Część 2: notify() vs notifyAll()
    // =========================================================
    static class Latch {
        private boolean open = false;

        synchronized void open() {
            open = true;
            notifyAll();   // budzi WSZYSTKICH czekających
        }

        synchronized void await(String name) throws InterruptedException {
            while (!open) wait();
            System.out.println("  [" + name + "] przeszedł przez bramkę");
        }
    }

    static void part2_NotifyAll() throws InterruptedException {
        System.out.println("\n=== Część 2: notifyAll() — budzi wszystkich ===");
        Latch latch = new Latch();

        Thread[] waiters = new Thread[4];
        for (int i = 0; i < waiters.length; i++) {
            final String name = "W" + i;
            waiters[i] = new Thread(() -> {
                try { latch.await(name); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }, name);
            waiters[i].start();
        }

        Thread.sleep(200);
        System.out.println("  [main] otwieram bramkę (notifyAll)");
        latch.open();
        for (Thread t : waiters) t.join();
    }

    // =========================================================
    // Część 3: Kolejka z wait/notify
    // =========================================================
    static class BoundedQueue<T> {
        private final Object[] buf;
        private int head = 0, tail = 0, size = 0;
        private final int capacity;

        BoundedQueue(int capacity) {
            this.capacity = capacity;
            this.buf = new Object[capacity];
        }

        synchronized void put(T item) throws InterruptedException {
            while (size == capacity) {
                System.out.println("  [put] kolejka pełna, czekam...");
                wait();
            }
            buf[tail] = item;
            tail = (tail + 1) % capacity;
            size++;
            System.out.println("  [put] dodano " + item + ", rozmiar=" + size);
            notifyAll();
        }

        @SuppressWarnings("unchecked")
        synchronized T take() throws InterruptedException {
            while (size == 0) {
                System.out.println("  [take] kolejka pusta, czekam...");
                wait();
            }
            T item = (T) buf[head];
            head = (head + 1) % capacity;
            size--;
            System.out.println("  [take] pobrano " + item + ", rozmiar=" + size);
            notifyAll();
            return item;
        }
    }

    static void part3_BoundedQueue() throws InterruptedException {
        System.out.println("\n=== Część 3: kolejka ograniczona (wait/notifyAll) ===");
        BoundedQueue<Integer> queue = new BoundedQueue<>(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Producent");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    queue.take();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Konsument");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    public static void main(String[] args) throws InterruptedException {
        part1_SimpleSignal();
        part2_NotifyAll();
        part3_BoundedQueue();
        System.out.println("\nGotowe.");
    }
}

