package _01_introduction.tdd.src;

/**
 * Klasa Stack implementowana metodą TDD.
 *
 * Historia TDD (widoczna w commitach):
 *
 * ITERACJA 1 — RED: test pushAndSize -> Stack nie istnieje -> RED
 *              GREEN: minimalna implementacja -> push/size
 *              REFACTOR: dodanie pola prywatnego
 *
 * ITERACJA 2 — RED: test pop -> brak pop() -> RED
 *              GREEN: implementacja pop()
 *              REFACTOR: walidacja
 *
 * ITERACJA 3 — RED: test peek -> brak peek() -> RED
 *              GREEN: implementacja peek()
 *
 * ITERACJA 4 — RED: test isEmpty -> brak isEmpty() -> RED
 *              GREEN: implementacja isEmpty()
 *
 * ITERACJA 5 — RED: test popFromEmpty -> brak wyjatku -> RED
 *              GREEN: rzucanie NoSuchElementException
 */
public class Stack<T> {

    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    public Stack() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Dodaje element na wierzcholek stosu.
     */
    public void push(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    /**
     * Usuwa i zwraca element z wierzcholka stosu.
     * @throws java.util.NoSuchElementException gdy stos pusty
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Stos jest pusty");
        }
        T element = (T) elements[--size];
        elements[size] = null; // GC-friendly
        return element;
    }

    /**
     * Zwraca element z wierzcholka stosu bez usuwania.
     * @throws java.util.NoSuchElementException gdy stos pusty
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Stos jest pusty");
        }
        return (T) elements[size - 1];
    }

    /**
     * Sprawdza czy stos jest pusty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Zwraca liczbe elementow na stosie.
     */
    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            elements = java.util.Arrays.copyOf(elements, elements.length * 2);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(elements[i]);
        }
        sb.append("] (top=").append(size > 0 ? elements[size-1] : "empty").append(")");
        return sb.toString();
    }
}

