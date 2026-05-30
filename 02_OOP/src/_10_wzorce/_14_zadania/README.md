# 14 — Zadania do samodzielnego rozwiązania

## Przegląd

Zadania obejmują wszystkie kategorie wzorców GoF omawiane w module.
Każde zadanie zawiera opis problemu, wskazówki i szkielet do uzupełnienia.

---

## Zadanie 1 — Singleton: Rejestr konfiguracji (Kreacyjny)

**Poziom:** ⭐⭐

### Problem
Napisz klasę `AppRegistry`, która:
- Jest Singletonem (thread-safe, Holder Idiom)
- Przechowuje właściwości `Map<String, String>`
- Umożliwia `get(key)`, `set(key, value)`, `getOrDefault(key, defaultVal)`
- Wczytuje domyślne wartości w konstruktorze (3-5 parametrów aplikacji)

### Szkielet

```java
public class AppRegistry {
    // TODO: Holder Idiom + prywatny konstruktor

    private final Map<String, String> properties = new HashMap<>();

    public String get(String key) { /* TODO */ }
    public void set(String key, String value) { /* TODO */ }
    public String getOrDefault(String key, String def) { /* TODO */ }
}
```

### Weryfikacja
- `AppRegistry.getInstance() == AppRegistry.getInstance()` → `true`
- Można odczytać domyślne właściwości bez `set()`

---

## Zadanie 2 — Factory Method: Parser formatów (Kreacyjny)

**Poziom:** ⭐⭐

### Problem
Zaprojektuj hierarchię parserów:
- Interfejs `DataParser` z metodą `List<Map<String,String>> parse(String input)`
- Klasy `CsvParser`, `JsonSimpleParser`, `TsvParser`
- Klasa `ParserFactory` z metodą `static DataParser create(String format)`

### Dane testowe
```
format=csv:  "name,age\nAlice,30\nBob,25"
format=tsv:  "name\tage\nAlice\t30"
```

### Wskazówki
- `CsvParser`: `String.split(",")` na każdej linii
- `TsvParser`: `String.split("\t")` na każdej linii
- Klucze z pierwszej linii (nagłówki)

---

## Zadanie 3 — Builder: Formularz HTML (Kreacyjny)

**Poziom:** ⭐⭐⭐

### Problem
Zbuduj `HtmlFormBuilder` generujący formularz HTML:

```java
String html = new HtmlFormBuilder()
    .action("/login").method("POST")
    .addTextField("username", "Nazwa użytkownika", true)
    .addPasswordField("password", "Hasło", true)
    .addCheckbox("remember", "Zapamiętaj mnie", false)
    .addSubmitButton("Zaloguj się")
    .build();
```

Oczekiwany wynik (uproszczony):
```html
<form action="/login" method="POST">
  <div><label>Nazwa użytkownika</label>
       <input type="text" name="username" required></div>
  <div><label>Hasło</label>
       <input type="password" name="password" required></div>
  <div><input type="checkbox" name="remember"> Zapamiętaj mnie</div>
  <button type="submit">Zaloguj się</button>
</form>
```

---

## Zadanie 4 — Decorator: Walidator pól (Strukturalny)

**Poziom:** ⭐⭐⭐

### Problem
Zbuduj system walidatorów z dekoratorami:

```java
// Interfejs
interface Validator<T> {
    ValidationResult validate(T value);
}

// Bazowy + dekoratory
Validator<String> emailValidator =
    new NotEmptyValidator<>(
        new MaxLengthValidator<>(
            new RegexValidator<>(
                new BaseValidator<>(),
                "^[^@]+@[^@]+\\.[^@]+$"),
            100));

// Wywołanie
emailValidator.validate("alice@example.com");  // OK
emailValidator.validate("");                    // błąd: nie może być puste
emailValidator.validate("not-an-email");       // błąd: zły format
```

### Wskazówki
- `ValidationResult` to klasa z `boolean valid` i `List<String> errors`
- Każdy dekorator dodaje własny komunikat błędu i deleguje do następnego

---

## Zadanie 5 — Observer: System powiadomień (Behawioralny)

**Poziom:** ⭐⭐⭐

### Problem
Zaimplementuj system eventów dla sklepu internetowego:
- Zdarzenia: `OrderPlaced`, `OrderShipped`, `OrderDelivered`
- Subskrybenci: `EmailNotifier`, `SmsNotifier`, `InventoryUpdater`, `AnalyticsCollector`
- `EventBus` — rejestracja handlerów per typ zdarzenia

```java
EventBus bus = new EventBus();
bus.subscribe(OrderPlaced.class,  new EmailNotifier());
bus.subscribe(OrderPlaced.class,  new InventoryUpdater());
bus.subscribe(OrderShipped.class, new SmsNotifier());

bus.publish(new OrderPlaced("ORD-001", "alice@example.com", 299.99));
// → EmailNotifier: wysłano potwierdzenie do alice@example.com
// → InventoryUpdater: aktualizacja stanów magazynowych
```

### Wskazówki
- `EventBus` przechowuje `Map<Class<?>, List<EventHandler<?>>>`
- Generyczny `EventHandler<E>` z metodą `handle(E event)`

---

## Zadanie 6 — Strategy: Kalkulator rabatów (Behawioralny)

**Poziom:** ⭐⭐

### Problem
Zaimplementuj system rabatów z wymiennialnymi strategiami:

```java
interface DiscountStrategy {
    double apply(double price, int quantity);
    String describe();
}

// Strategie:
// - NoDiscount: brak rabatu
// - PercentDiscount(10): 10% od ceny
// - BulkDiscount(5, 15): przy 5+ sztukach 15% rabatu
// - FixedAmountDiscount(50): odejmuje stałą kwotę (min 0)
// - CompositeDiscount: łączy kilka strategii (suma rabatów, max do 30%)

PricingEngine engine = new PricingEngine(new BulkDiscount(5, 15));
engine.calculatePrice(200.0, 10);  // 200 * 10 * 0.85 = 1700 PLN
```

---

## Zadanie 7 — Command + Composite: Edytor graficzny (Behawioralny)

**Poziom:** ⭐⭐⭐⭐

### Problem
Zaimplementuj uproszczony edytor graficzny z komendami:

```java
// Komendy
interface DrawCommand {
    void execute(Canvas canvas);
    void undo(Canvas canvas);
}

class AddShapeCommand  implements DrawCommand { }
class MoveShapeCommand implements DrawCommand { }
class DeleteShapeCommand implements DrawCommand { }
class GroupCommand implements DrawCommand { }  // Composite Command!

// Canvas przechowuje listę kształtów
// CommandHistory — pełna historia Undo/Redo
```

### Kształty (Composite!)
```java
interface Shape {
    void draw(String indent);
    Rectangle getBounds();
    void move(int dx, int dy);
}
class Circle implements Shape { ... }
class Rect implements Shape { ... }
class ShapeGroup implements Shape { ... }  // Composite!
```

---

## Zadanie 8 — Wzorce + SOLID: Biblioteka mediów (Projekt)

**Poziom:** ⭐⭐⭐⭐⭐

### Problem
Zaprojektuj system zarządzania biblioteką mediów (książki, filmy, muzyka):

**Wymagania:**
1. Wzorzec `Composite` — hierarchia kolekcji (`Library → Collection → Item`)
2. Wzorzec `Observer` — powiadomienia przy wypożyczeniu/zwrocie
3. Wzorzec `Strategy` — różne algorytmy wyszukiwania (tytuł, autor, gatunek, rok)
4. Wzorzec `Command` — historia wypożyczeń z możliwością cofnięcia
5. Wzorzec `Factory` — tworzenie różnych typów mediów

**Szkielet:**
```java
Library lib = new Library("Miejska Biblioteka Publiczna");

Collection fiction = lib.addCollection("Fikcja");
fiction.add(MediaFactory.create("book", "Wiedźmin", "Sapkowski", 1993));
fiction.add(MediaFactory.create("ebook", "Metro 2033", "Głuchowski", 2002));

lib.subscribe(LoanEvent.class, new EmailReminderService());

SearchStrategy byAuthor = new AuthorSearchStrategy();
List<MediaItem> results = lib.search("Sapkowski", byAuthor);
```

---

## Rozwiązania

Szkielety rozwiązań: [`code/`](code/)

| Zadanie | Plik |
|---------|------|
| 1 — Singleton | [`code/Task01_AppRegistry.java`](code/Task01_AppRegistry.java) |
| 2 — Factory | [`code/Task02_ParserFactory.java`](code/Task02_ParserFactory.java) |
| 3 — Builder | [`code/Task03_HtmlFormBuilder.java`](code/Task03_HtmlFormBuilder.java) |
| 4 — Decorator | [`code/Task04_ValidatorDecorator.java`](code/Task04_ValidatorDecorator.java) |
| 5 — Observer | [`code/Task05_EventBus.java`](code/Task05_EventBus.java) |
| 6 — Strategy | [`code/Task06_DiscountStrategy.java`](code/Task06_DiscountStrategy.java) |
| 7 — Command+Composite | [`code/Task07_GraphicEditor.java`](code/Task07_GraphicEditor.java) |
| 8 — Projekt | [`code/Task08_MediaLibrary.java`](code/Task08_MediaLibrary.java) |

---

## 📚 Pomocne linki

- [Refactoring.Guru — Design Patterns](https://refactoring.guru/design-patterns)
- [SourceMaking — Design Patterns](https://sourcemaking.com/design_patterns)
- [Baeldung — Design Patterns in Java](https://www.baeldung.com/design-patterns-series)

