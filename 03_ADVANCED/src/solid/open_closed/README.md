# Open/Closed Principle (OCP)

## Zasada Otwarte-Zamknięte

### Definicja

**Open/Closed Principle** stwierdza, że klasy powinny być otwarte na rozszerzanie, ale zamknięte na modyfikację.

> "Software entities should be open for extension, but closed for modification." - Bertrand Meyer

### Wyjaśnienie koncepcji

Zasada OCP oznacza, że:
- **Otwarte na rozszerzanie** - można dodawać nowe funkcjonalności
- **Zamknięte na modyfikację** - istniejący kod nie powinien być zmieniany

#### Dlaczego to ważne?

Modyfikowanie istniejącego kodu:
- ⚠️ Może wprowadzić nowe błędy
- ⚠️ Wymaga ponownego testowania całego modułu
- ⚠️ Może złamać istniejącą funkcjonalność (regresja)
- ⚠️ Narusza zasadę stabilności kodu

Rozszerzanie kodu bez modyfikacji:
- ✅ Istniejący kod pozostaje stabilny
- ✅ Nowe funkcjonalności są izolowane
- ✅ Łatwiejsze testowanie (tylko nowe elementy)
- ✅ Mniejsze ryzyko regresji

### Kluczowe mechanizmy

OCP można osiągnąć poprzez:
1. **Abstrakcję** - interfejsy i klasy abstrakcyjne
2. **Polimorfizm** - różne implementacje tego samego interfejsu
3. **Dziedziczenie** - rozszerzanie klas bazowych
4. **Wzorce projektowe** - Strategy, Template Method, Factory

### Problem - naruszenie OCP

Rozważmy system obliczania powierzchni figur geometrycznych:

```java
public class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            return rectangle.getWidth() * rectangle.getHeight();
        } else if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            return Math.PI * circle.getRadius() * circle.getRadius();
        }
        // Dodanie nowego kształtu wymaga modyfikacji tej metody!
        return 0;
    }
}
```

**Problemy:**
1. **Modyfikacja przy dodawaniu nowych figur** - trzeba zmieniać metodę `calculateArea`
2. **Naruszenie SRP** - klasa wie o wszystkich figurach
3. **Trudne testowanie** - każda zmiana wymaga testowania całej metody
4. **Brak rozszerzalności** - nie można dodać nowej figury bez zmiany kodu

![Problem - naruszenie OCP](diagrams/ocp_violation.png)

### Rozwiązanie - zastosowanie OCP

Używamy abstrakcji i polimorfizmu:

```java
// Abstrakcja - interfejs dla wszystkich figur
public interface Shape {
    double calculateArea();
}

// Konkretne implementacje
public class Rectangle implements Shape {
    private double width;
    private double height;
    
    @Override
    public double calculateArea() {
        return width * height;
    }
}

public class Circle implements Shape {
    private double radius;
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// Nowa figura - bez modyfikacji istniejącego kodu!
public class Triangle implements Shape {
    private double base;
    private double height;
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

// Kalkulator używa abstrakcji
public class AreaCalculator {
    public double calculateTotalArea(List<Shape> shapes) {
        return shapes.stream()
                    .mapToDouble(Shape::calculateArea)
                    .sum();
    }
}
```

![Rozwiązanie - zastosowanie OCP](diagrams/ocp_solution.png)

**Korzyści:**
- ✅ Dodawanie nowych figur bez modyfikacji istniejącego kodu
- ✅ `AreaCalculator` nie zna konkretnych implementacji
- ✅ Łatwe testowanie - każda figura osobno
- ✅ Zgodność z SRP - każda figura odpowiada za własne obliczenia

### Przykład praktyczny - System rabatów

#### Przed zastosowaniem OCP:

```java
public class DiscountCalculator {
    public double calculateDiscount(String customerType, double amount) {
        if (customerType.equals("REGULAR")) {
            return amount * 0.05;
        } else if (customerType.equals("PREMIUM")) {
            return amount * 0.10;
        } else if (customerType.equals("VIP")) {
            return amount * 0.20;
        }
        return 0;
    }
}
```

Problemy:
- ❌ Nowy typ klienta = modyfikacja metody
- ❌ Wszystkie typy w jednym miejscu
- ❌ Trudne testowanie logiki rabatowej

#### Po zastosowaniu OCP:

```java
public interface DiscountStrategy {
    double calculateDiscount(double amount);
}

public class RegularCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.05;
    }
}

public class PremiumCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.10;
    }
}

// Nowy typ - bez modyfikacji istniejącego kodu!
public class VIPCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.20;
    }
}
```

![Diagram - Strategy Pattern](diagrams/ocp_strategy.png)

### Przykłady implementacji

Zobacz kod w plikach:
- [before/AreaCalculator.java](before/AreaCalculator.java) - Naruszenie OCP
- [before/DiscountCalculator.java](before/DiscountCalculator.java) - Problem z rabatami
- [after/Shape.java](after/Shape.java) - Interfejs figur
- [after/Rectangle.java](after/Rectangle.java) - Implementacja prostokąta
- [after/Circle.java](after/Circle.java) - Implementacja koła
- [after/Triangle.java](after/Triangle.java) - Nowa figura bez modyfikacji
- [after/AreaCalculator.java](after/AreaCalculator.java) - Kalkulator zgodny z OCP
- [after/DiscountStrategy.java](after/DiscountStrategy.java) - Strategia rabatów
- [after/ShapeDemo.java](after/ShapeDemo.java) - Demonstracja rozwiązania

### Diagram klas

![Diagram klas - OCP](diagrams/ocp_class_diagram.png)

### Wzorce projektowe wspierające OCP

#### 1. Strategy Pattern
Pozwala na wymianę algorytmów w czasie wykonania bez modyfikacji kodu.

#### 2. Template Method Pattern
Definiuje szkielet algorytmu, pozwalając podklasom nadpisać konkretne kroki.

#### 3. Factory Pattern
Tworzy obiekty bez określania ich konkretnych klas.

#### 4. Decorator Pattern
Dynamicznie dodaje nowe zachowania do obiektów.

### Jak rozpoznać naruszenie OCP?

Sygnały ostrzegawcze:
1. ❌ Seria instrukcji `if-else` lub `switch` sprawdzających typ
2. ❌ Częste modyfikacje tej samej klasy przy dodawaniu funkcji
3. ❌ Operator `instanceof` w wielu miejscach
4. ❌ Komentarze typu "// TODO: add support for..."
5. ❌ Klasa zna wszystkie możliwe warianty

### Techniki stosowania OCP

#### 1. Użyj interfejsów i abstrakcji
```java
// Zamiast konkretnych klas
public void process(EmailSender sender) { }

// Użyj abstrakcji
public void process(MessageSender sender) { }
```

#### 2. Dependency Injection
```java
public class OrderService {
    private final PaymentProcessor processor;
    
    // Wstrzykiwanie zależności
    public OrderService(PaymentProcessor processor) {
        this.processor = processor;
    }
}
```

#### 3. Kompozycja zamiast dziedziczenia
```java
public class Car {
    private final Engine engine;  // Kompozycja
    
    public void start() {
        engine.start();  // Delegacja
    }
}
```

### Pułapki i błędy

❌ **Przedwczesna abstrakcja**
```java
// ZŁE - abstrakcja bez powodu
public interface UserNameGetter {
    String getName();
}
```

❌ **Zbyt wiele warstw abstrakcji**
```java
// ZŁE - nadmierna złożoność
Interface -> AbstractClass -> BaseClass -> ConcreteClass
```

✅ **Właściwe podejście**
- Abstrahuj gdy widzisz powtarzający się wzorzec
- Stosuj YAGNI (You Aren't Gonna Need It)
- Balance między elastycznością a prostotą

### Przykład z rzeczywistego świata

System eksportu danych:

```java
// Interfejs - zamknięty na modyfikację
public interface DataExporter {
    void export(List<Data> data, OutputStream output);
}

// Implementacje - otwarte na rozszerzanie
public class CsvExporter implements DataExporter { }
public class JsonExporter implements DataExporter { }
public class XmlExporter implements DataExporter { }
public class PdfExporter implements DataExporter { }  // Nowa bez modyfikacji!
```

### Podsumowanie

| Aspekt | Przed OCP | Po OCP |
|--------|-----------|---------|
| Dodawanie funkcji | Modyfikacja kodu | Nowa klasa |
| Stabilność | Niska | Wysoka |
| Ryzyko regresji | Wysokie | Niskie |
| Testowalność | Trudna | Łatwa |
| Elastyczność | Niska | Wysoka |

### Kluczowe zasady

1. **Abstrakcja jest kluczem** - używaj interfejsów i klas abstrakcyjnych
2. **Polimorfizm** - pozwala na różne implementacje
3. **Dependency Injection** - wstrzykuj zależności zamiast tworzyć
4. **Wzorce projektowe** - stosuj Strategy, Template Method, Factory

### Relacja z innymi zasadami SOLID

- **SRP**: OCP wspiera SRP przez separację odpowiedzialności
- **LSP**: Podklasy muszą być podstawialne (Liskov Substitution)
- **ISP**: Małe, specyficzne interfejsy są łatwiej rozszerzalne
- **DIP**: Zależność od abstrakcji, nie konkretnych implementacji

### Ćwiczenie

Zrefaktoruj klasę `Logger`, która naruszuje OCP:

```java
public class Logger {
    public void log(String message, String type) {
        if (type.equals("FILE")) {
            // zapisz do pliku
        } else if (type.equals("CONSOLE")) {
            // wyświetl w konsoli
        } else if (type.equals("DATABASE")) {
            // zapisz do bazy
        }
    }
}
```

**Wskazówka**: Użyj interfejsu i różnych implementacji.

### Referencje

- Bertrand Meyer, "Object-Oriented Software Construction"
- Robert C. Martin, "Agile Software Development: Principles, Patterns, and Practices"
- Gang of Four, "Design Patterns: Elements of Reusable Object-Oriented Software"

