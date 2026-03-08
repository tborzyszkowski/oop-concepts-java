package object_lifecycle.advanced;

import java.lang.ref.WeakReference;

/**
 * Demonstracja działania Garbage Collector (GC) w Javie.
 *
 * GC automatycznie usuwa obiekty, do których nie istnieje już żadna
 * silna referencja (strong reference).
 *
 * Rodzaje referencji:
 *  - StrongReference — zwykłe przypisanie (obj = new Foo())
 *  - WeakReference   — nie zapobiega usunięciu przez GC
 *  - SoftReference   — usuwana gdy brakuje pamięci
 *  - PhantomReference — po finalizacji, przed zwolnieniem pamięci
 */
public class GcDemo {

    /** Obiekt do obserwacji przez GC */
    static class TrackableObject {
        private final int id;
        private final byte[] data; // symuluje zużycie pamięci

        TrackableObject(int id, int sizeKb) {
            this.id = id;
            this.data = new byte[sizeKb * 1024];
            System.out.println("  [CREATE] TrackableObject #" + id
                    + " (" + sizeKb + " KB)");
        }

        // @Override finalize() — PRZESTARZAŁE od Java 9, usunięte w Java 18
        // Nie używaj finalize() w nowym kodzie!
        // Zamiast tego używaj: try-with-resources, Cleaner, AutoCloseable
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Demonstracja Garbage Collector ===");
        System.out.println();

        // --- 1. Silna referencja (Strong Reference) ---
        System.out.println("--- 1. Silna referencja (Strong Reference) ---");
        TrackableObject strong = new TrackableObject(1, 1);
        System.out.println("strong != null: " + (strong != null));
        System.out.println("Obiekt ŻYJE — istnieje referencja 'strong'");

        System.out.println();
        System.out.println("--- 2. Utrata referencji → kandydat do GC ---");
        strong = null; // brak referencji → obiekt może być usunięty
        System.out.println("strong = null — obiekt jest kandydatem do GC");
        Runtime.getRuntime().gc(); // sugestia do JVM (nie gwarancja!)
        Thread.sleep(100);
        System.out.println("GC mógł już usunąć obiekt #1");

        System.out.println();
        System.out.println("--- 3. WeakReference — GC może usunąć w każdej chwili ---");
        TrackableObject obj2 = new TrackableObject(2, 1);
        WeakReference<TrackableObject> weakRef = new WeakReference<>(obj2);

        System.out.println("Przed usunięciem silnej ref: weakRef.get() != null? "
                + (weakRef.get() != null));

        obj2 = null; // usuwamy silną referencję
        Runtime.getRuntime().gc();
        Thread.sleep(100);

        System.out.println("Po GC: weakRef.get() = " + weakRef.get());
        // WeakReference zwraca null gdy obiekt został zebrany

        System.out.println();
        System.out.println("--- 4. Alokacja wielu obiektów — obserwacja pamięci ---");
        Runtime rt = Runtime.getRuntime();
        long before = rt.totalMemory() - rt.freeMemory();
        System.out.println("Pamięć przed: " + (before / 1024) + " KB");

        // Tworzy 100 obiektów po 100 KB — łącznie ~10 MB
        TrackableObject[] objects = new TrackableObject[100];
        for (int i = 0; i < 100; i++) {
            objects[i] = new TrackableObject(100 + i, 100);
        }

        long afterAlloc = rt.totalMemory() - rt.freeMemory();
        System.out.println("Pamięć po alokacji: " + (afterAlloc / 1024) + " KB");

        // Usunięcie wszystkich referencji
        for (int i = 0; i < objects.length; i++) objects[i] = null;
        objects = null;

        rt.gc();
        Thread.sleep(200);

        long afterGc = rt.totalMemory() - rt.freeMemory();
        System.out.println("Pamięć po GC: " + (afterGc / 1024) + " KB");
        System.out.println("Odzyskano: ~" + ((afterAlloc - afterGc) / 1024) + " KB");

        System.out.println();
        System.out.println("--- 5. Wyciek pamięci (memory leak) — anty-wzorzec ---");
        System.out.println("Wyciek = sytuacja gdy obiekty są zbędne,");
        System.out.println("ale ciągle istnieje do nich referencja (np. w statycznej liście).");
        System.out.println("Przykład: static List<Object> cache = new ArrayList<>() — bez czyszczenia");
    }
}

