package _05_kolekcje._02_typy_kolekcji.code;

import java.util.*;

/**
 * CollectionTypesDemo — porównanie dostępnych typów kolekcji w Javie.
 *
 * <p>Pokazuje:
 * <ul>
 *   <li>ArrayList vs LinkedList — kiedy który wybrać</li>
 *   <li>HashSet vs LinkedHashSet vs TreeSet</li>
 *   <li>PriorityQueue — kolejka priorytetowa</li>
 *   <li>Większy przykład: dobór kolekcji do konkretnych wymagań</li>
 * </ul>
 */
public class CollectionTypesDemo {

    // -------------------------------------------------------
    // 1. ArrayList vs LinkedList
    // -------------------------------------------------------
    static void listComparison() {
        System.out.println("=== 1. ArrayList vs LinkedList ===");

        // ArrayList — wydajne losowe czytanie O(1), wstawianie na końcu O(1) amortyzowane
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) arrayList.add("a" + i);
        System.out.println("ArrayList: " + arrayList);
        System.out.println("  get(2) = " + arrayList.get(2));   // O(1)
        arrayList.add(1, "INSERT");                               // O(n) — przesuwa elementy
        System.out.println("  po add(1,'INSERT'): " + arrayList);

        // LinkedList — szybkie wstawianie na początku/końcu O(1), wolne indeksowanie O(n)
        LinkedList<String> linked = new LinkedList<>();
        for (int i = 0; i < 5; i++) linked.add("l" + i);
        linked.addFirst("FIRST");   // O(1)
        linked.addLast("LAST");     // O(1)
        System.out.println("LinkedList: " + linked);
        System.out.println("  peekFirst: " + linked.peekFirst());
        System.out.println("  peekLast: " + linked.peekLast());
    }

    // -------------------------------------------------------
    // 2. Set — HashSet / LinkedHashSet / TreeSet
    // -------------------------------------------------------
    static void setComparison() {
        System.out.println("\n=== 2. HashSet / LinkedHashSet / TreeSet ===");
        List<String> input = List.of("banan", "jabłko", "banan", "gruszka", "jabłko", "wiśnia");
        System.out.println("Wejście: " + input);

        // HashSet — O(1) add/contains, ale kolejność losowa
        Set<String> hash = new HashSet<>(input);
        System.out.println("HashSet    (bez duplikatów, losowa kolejność): " + hash);

        // LinkedHashSet — zachowuje kolejność wstawiania
        Set<String> linked = new LinkedHashSet<>(input);
        System.out.println("LinkedHashSet (kolejność wstawiania):         " + linked);

        // TreeSet — posortowany (naturalny porządek lub Comparator)
        Set<String> tree = new TreeSet<>(input);
        System.out.println("TreeSet    (posortowany alfabetycznie):       " + tree);
    }

    // -------------------------------------------------------
    // 3. PriorityQueue — kolejka priorytetowa
    // -------------------------------------------------------
    static void priorityQueueDemo() {
        System.out.println("\n=== 3. PriorityQueue (min-heap) ===");

        // Domyślnie: minimum na szczycie (naturalny porządek)
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(30); pq.offer(10); pq.offer(20); pq.offer(5);

        System.out.print("Poll kolejno (rosnąco): ");
        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " ");
        }
        System.out.println();

        // Max-heap z odwróconą kolejnością
        PriorityQueue<Integer> maxPq = new PriorityQueue<>(Comparator.reverseOrder());
        maxPq.offer(30); maxPq.offer(10); maxPq.offer(20); maxPq.offer(5);
        System.out.print("Poll kolejno (malejąco): ");
        while (!maxPq.isEmpty()) {
            System.out.print(maxPq.poll() + " ");
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // 4. ArrayDeque — stos i kolejka w jednym
    // -------------------------------------------------------
    static void dequeDemo() {
        System.out.println("\n=== 4. ArrayDeque jako stos (LIFO) ===");
        Deque<String> stack = new ArrayDeque<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.print("Pop (LIFO): ");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();

        System.out.println("\n=== ArrayDeque jako kolejka (FIFO) ===");
        Deque<String> queue = new ArrayDeque<>();
        queue.offer("żądanie-1");
        queue.offer("żądanie-2");
        queue.offer("żądanie-3");
        System.out.print("Poll (FIFO): ");
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // 5. Większy przykład: system ticketów wsparcia
    // -------------------------------------------------------
    record Ticket(int id, int priority, String description) implements Comparable<Ticket> {
        @Override
        public int compareTo(Ticket other) {
            return Integer.compare(this.priority, other.priority);
        }
        @Override public String toString() {
            return "Ticket[id=%d, prio=%d, '%s']".formatted(id, priority, description);
        }
    }

    static void ticketSystemDemo() {
        System.out.println("\n=== 5. System ticketów — dobór kolekcji ===");

        // Kolejka priorytetowa: niski numer = wyższy priorytet
        PriorityQueue<Ticket> tickets = new PriorityQueue<>();
        tickets.offer(new Ticket(1, 3, "Resetowanie hasła"));
        tickets.offer(new Ticket(2, 1, "Awaria produkcyjna!"));
        tickets.offer(new Ticket(3, 2, "Powolna strona"));
        tickets.offer(new Ticket(4, 1, "Błąd w zamówieniu"));

        // Historia przetworzonych ticketów (LinkedHashSet = unikalność + kolejność przetwarzania)
        Set<Integer> processed = new LinkedHashSet<>();

        System.out.println("Przetwarzanie ticketów (posortowane wg priorytetu):");
        while (!tickets.isEmpty()) {
            Ticket t = tickets.poll();
            processed.add(t.id());
            System.out.println("  → " + t);
        }
        System.out.println("Historia przetworzenia (kolejność): " + processed);
    }

    public static void main(String[] args) {
        listComparison();
        setComparison();
        priorityQueueDemo();
        dequeDemo();
        ticketSystemDemo();
    }
}

