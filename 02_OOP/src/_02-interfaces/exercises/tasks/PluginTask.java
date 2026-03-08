package exercises.tasks;

/**
 * ZADANIE 3 (⭐⭐) — Wzorzec Strategy: TextTransformer
 *
 * Zaimplementuj interfejs TextTransformer i trzy strategie.
 * Skomponuj pipeline z kilku transformatorów.
 *
 * Oczekiwany output:
 *   UpperCase:   "HELLO WORLD"
 *   Trim:        "hello world"  (z "  hello world  ")
 *   Reverse:     "dlrow olleh"
 *   Pipeline (trim->upper->reverse): "DLROW OLLEH"
 */
public class PluginTask {

    // TODO: @FunctionalInterface interface TextTransformer
    //   - String transform(String input)
    //   - default TextTransformer andThen(TextTransformer next) — kompozycja

    // TODO: class UpperCaseTransformer implements TextTransformer
    // TODO: class TrimTransformer implements TextTransformer
    // TODO: class ReverseTransformer implements TextTransformer

    public static void main(String[] args) {
        // TODO: Utwórz instancje wszystkich trzech transformatorów
        // TODO: Pokaż działanie każdego osobno
        // TODO: Skomponuj pipeline: trim -> upperCase -> reverse
        // TODO: Zastosuj pipeline do "  hello world  "
        System.out.println("TODO: zaimplementuj zadanie");
    }
}

