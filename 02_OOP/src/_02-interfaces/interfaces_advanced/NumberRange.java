package interfaces_advanced;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Własna implementacja Iterable<Integer> — zakres liczb całkowitych.
 * Pozwala używać pętli for-each: for (int n : new NumberRange(1, 10)) { ... }
 */
public class NumberRange implements Iterable<Integer> {
    private final int start;
    private final int end;   // włącznie
    private final int step;

    public NumberRange(int start, int end, int step) {
        if (step <= 0) throw new IllegalArgumentException("step musi być > 0");
        this.start = start;
        this.end   = end;
        this.step  = step;
    }
    public NumberRange(int start, int end) { this(start, end, 1); }

    @Override
    public Iterator<Integer> iterator() {
        return new RangeIterator();
    }

    /** Wewnętrzna klasa implementująca Iterator<Integer>. */
    private class RangeIterator implements Iterator<Integer> {
        private int current = start;

        @Override
        public boolean hasNext() { return current <= end; }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            int value = current;
            current += step;
            return value;
        }
    }
}

