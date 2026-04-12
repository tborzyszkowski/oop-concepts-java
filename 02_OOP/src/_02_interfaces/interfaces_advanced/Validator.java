package _02_interfaces.interfaces_advanced;

/**
 * Własny interfejs funkcyjny — dokładnie jedna abstrakcyjna metoda.
 * @FunctionalInterface — adnotacja zabrania dodania drugiej metody abstrakcyjnej.
 */
@FunctionalInterface
public interface Validator<T> {
    /** Zwraca true, gdy wartość jest poprawna. */
    boolean validate(T value);

    /** default — można dodawać metody nieabstrakcyjne bez naruszania @FunctionalInterface */
    default Validator<T> and(Validator<T> other) {
        return value -> this.validate(value) && other.validate(value);
    }

    default Validator<T> or(Validator<T> other) {
        return value -> this.validate(value) || other.validate(value);
    }

    default Validator<T> negate() {
        return value -> !this.validate(value);
    }
}

