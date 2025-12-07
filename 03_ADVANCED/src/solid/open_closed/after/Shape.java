package solid.open_closed.after;

/**
 * Interfejs Shape - zgodnie z OCP.
 *
 * Abstrakcja zamknięta na modyfikację,
 * ale otwarta na rozszerzanie przez nowe implementacje.
 */
public interface Shape {
    double calculateArea();
    double calculatePerimeter();
    String getName();
}

