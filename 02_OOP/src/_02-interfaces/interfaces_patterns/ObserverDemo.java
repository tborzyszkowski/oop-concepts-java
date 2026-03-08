package interfaces_patterns;

import java.util.ArrayList;
import java.util.List;

/** Observer — interfejs obserwatora (jest @FunctionalInterface — lambda jako listener!) */
@FunctionalInterface
interface EventListener<T> {
    void onEvent(T event);
}

/** Prosty EventBus — Subject w wzorcu Observer */
class EventBus<T> {
    private final List<EventListener<T>> listeners = new ArrayList<>();
    private final String name;

    EventBus(String name) { this.name = name; }

    public void subscribe(EventListener<T> listener)   { listeners.add(listener); }
    public void unsubscribe(EventListener<T> listener) { listeners.remove(listener); }

    public void publish(T event) {
        System.out.println("[" + name + "] Event: " + event);
        listeners.forEach(l -> l.onEvent(event));
    }
}

/** Klasa domenowa — generuje zdarzenia */
class TemperatureSensor {
    private final EventBus<Double> bus = new EventBus<>("TemperatureSensor");
    private double temperature;

    public void addListener(EventListener<Double> l) { bus.subscribe(l); }

    public void setTemperature(double t) {
        this.temperature = t;
        bus.publish(t);
    }
}

/**
 * WZORZEC OBSERVER
 * Wiele obiektów reaguje na zmiany stanu innego obiektu.
 * EventListener<T> jako @FunctionalInterface — można używać lambd!
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_patterns/ObserverDemo.java
 *   java -cp out interfaces_patterns.ObserverDemo
 */
public class ObserverDemo {
    public static void main(String[] args) {
        TemperatureSensor sensor = new TemperatureSensor();

        // Listener 1 — klasa anonimowa (stary styl)
        sensor.addListener(new EventListener<Double>() {
            @Override public void onEvent(Double temp) {
                System.out.println("  Wyswietlacz LCD: " + temp + "C");
            }
        });

        // Listener 2 — lambda (mozliwe bo @FunctionalInterface)
        sensor.addListener(temp -> {
            if (temp > 35) System.out.println("  ALARM: Temperatura krytyczna! " + temp + "C");
        });

        // Listener 3 — method reference
        sensor.addListener(ObserverDemo::logToFile);

        System.out.println("=== Symulacja odczytow ===");
        sensor.setTemperature(22.5);
        sensor.setTemperature(36.1);
        sensor.setTemperature(18.0);
    }

    private static void logToFile(double temp) {
        System.out.printf("  [LOG] %s temperature=%.1f%n",
            java.time.LocalTime.now(), temp);
    }
}

