# Moduł 2.4: Szczególne Własności i Innowacje w Interfejsach

## Wprowadzenie

### 🎯 Czego się nauczysz w tym module?
*   Jak unikać psucia kodu klienta dzięki metodom **domyślnym** (`default`).
*   Jak **Sealed Interfaces** pomagają kontrolować hierarchię dziedziczenia.
*   Jak używać interfejsów jako fabryk (metody `static`).

Wraz z ewolucją Javy, interfejsy zyskały nowe możliwości, zmieniając się z prostych definicji metod abstrakcyjnych w potężne narzędzie do projektowania API.

W tym module zobaczysz:
1.  **Metody domyślne (`default`)** – pozwalają dodawać nową funkcjonalność do istniejących interfejsów bez łamania kodu.
2.  **Metody statyczne (`static`)** – umożliwiają tworzenie "fabryk" lub pomocniczych metod w interfejsie.
3.  **Metody prywatne (`private`)** (Java 9+) – służą do refaktoryzacji metod domyślnych.
4.  **Sealed Interfaces (`sealed`)** (Java 17+) – pozwalają zablokować hierarchię dziedziczenia.

---

## Metody Domyślne (`default`)

W Java 8 wprowadzono metody z ciałem (`default`) w interfejsie. Ma to dwie główne zalety:
*   Pozwala rozwijać interfejsy bez psucia istniejących implementacji (kompatybilność wsteczna).
*   Pozwala stworzyć "opcjonalne metody" z domyślnym zachowaniem.

![Metody domyślne](diagrams/interface_inheritance.png)

```java
public interface Vehicle {
    void startEngine(); // abstrakcyjna - wymagana

    // Metoda z domyślną implementacją
    default void stopEngine() {
        System.out.println("Zatrzymywanie silnika...");
    }
}
```

Klasa, która implementuje `Vehicle`, **może** skorzystać z domyślnego `stopEngine()`, ale **musi** zaimplementować `startEngine()`.

Zobacz przykład w [Vehicle.java](Vehicle.java) i [ElectricVehicle.java](ElectricVehicle.java), oraz demonstrację w [DefaultMethodDemo.java](DefaultMethodDemo.java).

---

## Metody Statyczne i Prywatne

### Metody statyczne (`static`)
Definiują funkcjonalność, która należy do samego interfejsu (np. metoda fabryczna), a nie do instancji klasy implementującej.

```java
// Vehicle.java
static Vehicle create(String brand) {
    return new Vehicle() {
        @Override public String getBrand() { return brand; }
        @Override public int getCurrentSpeed() { return 0; }
    };
}
```

Przykład użycia znajdziesz w [DefaultMethodDemo.java](DefaultMethodDemo.java):

```java
Vehicle generic = Vehicle.create("GenericBrand");
System.out.println(generic.statusReport());
// ElectricVehicle.create(...) // blad: metody static nie sa dziedziczone
```

**Wniosek:** metody statyczne wywołujemy przez nazwę interfejsu (`Vehicle.create(...)`),
nie przez obiekt i nie przez interfejs potomny.

### Metody prywatne (`private`)
Jeśli kilka metod domyślnych (lub statycznych) potrzebuje tej samej logiki, możemy ją wydzielić do prywatnej metody wewnątrz interfejsu. Jest ona niewidoczna na zewnątrz i służy tylko porządkowaniu implementacji.

W [Vehicle.java](Vehicle.java) metoda prywatna `logEvent(...)` jest współdzielona przez dwie metody domyślne:

```java
default void startEngine() {
    System.out.println(getBrand() + ": Silnik uruchomiony (paliwo: " + DEFAULT_FUEL + ")");
    logEvent("START");
}

default void stopEngine() {
    System.out.println(getBrand() + ": Silnik zatrzymany");
    logEvent("STOP");
}

private void logEvent(String event) {
    System.out.println("  [LOG] " + event + " @ " + System.currentTimeMillis());
}
```

To podejście eliminuje duplikację kodu i upraszcza utrzymanie interfejsu podczas dalszej rozbudowy API.

---

## Sealed Interfaces (`sealed`) + Records

Od Java 17 możemy kontrolować hierarchię dziedziczenia za pomocą słowa kluczowego `sealed`. Interfejs `sealed` (zapieczętowany) jawnie wymienia klasy, które mogą go implementować (`permits`).

Dzięki temu kompilator ma pewność co do wszystkich możliwych wariantów typu, co świetnie współpracuje z **Wzorcami w Switch** (Switch Expressions i Pattern Matching - od Java 21).

![Sealed Interfaces](diagrams/sealed_interface.png)

```java
// Tylko 3 klasy mogą być Shape: Circle, Rectangle, Triangle
public sealed interface Shape permits Circle, Rectangle, Triangle {}

// Switch expression (Java 21) nie wymaga "default", bo kompilator wie, że pokryliśmy wszystkie przypadki!
return switch(s) {
    case Circle c    -> Math.PI * c.radius() * c.radius();
    case Rectangle r -> r.width() * r.height();
    case Triangle t  -> ...
};
```

Demo "Modern Java" w [SealedDemo.java](SealedDemo.java).

---

## ⚠️ Najczęstsze błędy początkujących

1.  **Nadużywanie metod `default`:**
    Metody domyślne służą głównie do **zachowania kompatybilności wstecznej** (ewolucji API). Nie należy ich używać do implementowania logiki biznesowej, która powinna być w klasie. Traktuj je raczej jako "ratunek", a nie standardowy sposób pisania kodu.

2.  **Ignorowanie metod `private`:**
    Jeśli masz kilka metod `default` z powtarzającym się kodem, wydziel ten kod do metody `private` w interfejsie. To poprawia czytelność i ułatwia zmiany.

3.  **Brak sekcji `default` w Switchu:**
    W "starym" switchu zawsze trzeba było pisać `default:`.
    W przypadku **Sealed Types**, jeśli pokryjesz wszystkie przypadki (`permits`), `default` jest zbędny. Ale uważaj: jeśli dodasz nową klasę do `permits`, a zapomnisz o switchu, kompilator zgłosi błąd (co jest dobrą rzeczą!).

---

## 📚 Literatura i materiały dodatkowe

*   [JEP 409: Sealed Classes](https://openjdk.org/jeps/409)
*   [Oracle Java Magazine: Java 17 Sealed Classes](https://blogs.oracle.com/javamagazine/post/java-17-sealed-classes-records-pattern-matching)

---

## Uruchomienie przykładów

```powershell
.\run-examples.ps1
```
