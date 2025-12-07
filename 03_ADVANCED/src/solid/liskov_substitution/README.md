# Liskov Substitution Principle (LSP)

## Zasada Podstawiania Liskov

### Definicja

**Liskov Substitution Principle** stwierdza, że obiekty klasy pochodnej powinny móc zastąpić obiekty klasy bazowej bez zmiany poprawności programu.

> "Objects of a superclass should be replaceable with objects of a subclass without breaking the application." - Barbara Liskov

### Wyjaśnienie koncepcji

Zasada LSP oznacza, że:
- **Podklasy muszą być podstawialne** za swoje klasy bazowe
- **Kontrakty nie mogą być osłabiane** - podklasa musi spełniać kontrakt klasy bazowej
- **Zachowanie musi być zachowane** - klient nie powinien zauważyć różnicy

Formalnie, jeśli S jest podtypem T, to obiekty typu T mogą być zastąpione obiektami typu S bez zmiany właściwości programu.

```java
// Jeśli to działa:
BaseClass obj = new BaseClass();
obj.method();

// To musi działać także:
BaseClass obj = new DerivedClass();
obj.method();
```

### Kluczowe wymagania LSP

1. **Warunki wstępne (Preconditions)** nie mogą być wzmocnione w podklasie
2. **Warunki końcowe (Postconditions)** nie mogą być osłabione w podklasie
3. **Niezmienniki (Invariants)** muszą być zachowane
4. **Historia** - podklasa nie może modyfikować stanu w sposób niedozwolony przez klasę bazową

### Problem - naruszenie LSP

#### Przykład 1: Klasyczny problem Rectangle-Square

```java
public class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}

public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // ❌ Zmienia oba wymiary!
    }

    @Override
    public void setHeight(int height) {
        this.width = height;  // ❌ Zmienia oba wymiary!
        this.height = height;
    }
}

// Problem:
void testRectangle(Rectangle r) {
    r.setWidth(5);
    r.setHeight(4);
    assert r.getArea() == 20;  // ❌ FAIL dla Square!
}
```

**Problem**: Square narusza kontrakt Rectangle - klient oczekuje niezależnych wymiarów.

![Problem - Rectangle/Square](diagrams/lsp_rectangle_problem.png)

#### Przykład 2: Ptaki i latanie

```java
public class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

public class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
        // ❌ Naruszenie LSP - rzuca wyjątek!
    }
}

// Problem:
void makeBirdFly(Bird bird) {
    bird.fly();  // ❌ Działa dla Bird, ale nie dla Penguin
}
```

![Problem - Bird/Penguin](diagrams/lsp_bird_problem.png)

### Rozwiązanie - zastosowanie LSP

#### Rozwiązanie 1: Kompozycja zamiast dziedziczenia

```java
public interface Shape {
    double getArea();
}

public class Rectangle implements Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }
}

public class Square implements Shape {
    private final int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }
}
```

✅ **Korzyści**: Każda klasa ma własny kontrakt, nie ma problemu podstawiania.

#### Rozwiązanie 2: Poprawna hierarchia

```java
public abstract class Bird {
    public abstract void move();
}

public interface Flyable {
    void fly();
}

public class Sparrow extends Bird implements Flyable {
    @Override
    public void move() {
        fly();
    }

    @Override
    public void fly() {
        System.out.println("Sparrow is flying");
    }
}

public class Penguin extends Bird {
    @Override
    public void move() {
        swim();
    }

    public void swim() {
        System.out.println("Penguin is swimming");
    }
}
```

✅ **Korzyści**: Hierarchia odzwierciedla rzeczywiste możliwości.

![Rozwiązanie - Poprawna hierarchia](diagrams/lsp_solution.png)

### Przykłady implementacji

Zobacz kod w plikach:
- [before/Rectangle.java](before/Rectangle.java) - Problem Rectangle/Square
- [before/Bird.java](before/Bird.java) - Problem Bird/Penguin
- [after/Shape.java](after/Shape.java) - Rozwiązanie przez interfejs
- [after/Bird.java](after/Bird.java) - Poprawna hierarchia ptaków
- [after/BirdDemo.java](after/BirdDemo.java) - Demonstracja

### Diagram klas

![Diagram klas - LSP](diagrams/lsp_class_diagram.png)

### Zasady projektowania zgodnego z LSP

#### 1. Nie wzmacniaj warunków wstępnych
```java
// Klasa bazowa
public void process(int value) {
    if (value < 0) throw new IllegalArgumentException();
    // ...
}

// ❌ ZŁE - podklasa wzmacnia warunek
public void process(int value) {
    if (value < 0 || value > 100) throw new IllegalArgumentException();
    // ...
}

// ✅ DOBRE - podklasa może osłabiać
public void process(int value) {
    // Akceptuje wszystkie wartości
    // ...
}
```

#### 2. Nie osłabiaj warunków końcowych
```java
// Klasa bazowa - zwraca wartość > 0
public int calculate() {
    return positiveValue;
}

// ❌ ZŁE - może zwrócić wartość <= 0
public int calculate() {
    return anyValue;
}

// ✅ DOBRE - zachowuje kontrakt
public int calculate() {
    return positiveValue;
}
```

#### 3. Zachowaj niezmienniki
```java
// Niezmiennik: lista zawsze posortowana
public class SortedList {
    public void add(int value) {
        // Dodaje i sortuje
    }
}

// ❌ ZŁE - nie zachowuje niezmiennika
public class FastList extends SortedList {
    public void add(int value) {
        // Dodaje bez sortowania
    }
}
```

### Wykrywanie naruszeń LSP

Sygnały ostrzegawcze:
1. ❌ Rzucanie nieoczekiwanych wyjątków w podklasie
2. ❌ Sprawdzanie typu za pomocą `instanceof`
3. ❌ Nadpisywanie metod pustą implementacją lub wyjątkiem
4. ❌ Dodawanie dodatkowych warunków wstępnych
5. ❌ Komentarze typu "Don't use this method for SubClass"

### Testy zgodności z LSP

```java
@Test
public void testLiskovSubstitution() {
    List<Bird> birds = Arrays.asList(
        new Sparrow(),
        new Eagle(),
        new Penguin()  // ❌ Jeśli to się nie powiedzie, naruszenie LSP
    );

    for (Bird bird : birds) {
        bird.move();  // Musi działać dla wszystkich
    }
}
```

### LSP a inne zasady SOLID

- **SRP**: LSP wymaga spójnych kontraktów (jedna odpowiedzialność)
- **OCP**: LSP zapewnia, że rozszerzenia działają poprawnie
- **ISP**: Małe interfejsy ułatwiają spełnienie LSP
- **DIP**: Abstrakcje muszą być podstawialne

### Praktyczne przykłady

#### Przykład 1: Kolekcje tylko do odczytu

```java
// ❌ Naruszenie LSP
List<String> readOnlyList = Collections.unmodifiableList(list);
readOnlyList.add("test");  // Throws UnsupportedOperationException

// ✅ Zgodne z LSP
interface ReadOnlyList<E> {
    E get(int index);
    int size();
}

interface MutableList<E> extends ReadOnlyList<E> {
    void add(E element);
}
```

#### Przykład 2: Pojazdy

```java
// ✅ Zgodne z LSP
public abstract class Vehicle {
    public abstract void move();
}

public class Car extends Vehicle {
    @Override
    public void move() {
        drive();
    }
    
    private void drive() { /* ... */ }
}

public class Bicycle extends Vehicle {
    @Override
    public void move() {
        pedal();
    }
    
    private void pedal() { /* ... */ }
}
```

### Podsumowanie

| Aspekt | Naruszenie LSP | Zgodność z LSP |
|--------|---------------|----------------|
| Podstawialność | Nie działa | Działa zawsze |
| Wyjątki | Nieoczekiwane | Zgodne z kontraktem |
| Warunki | Wzmocnione/osłabione | Zachowane |
| Testowanie | Wymaga instanceof | Polimorficzne |
| Utrzymanie | Trudne | Łatwe |

### Kluczowe zasady

1. **Podklasa musi spełniać kontrakt klasy bazowej**
2. **Nie wzmacniaj warunków wstępnych**
3. **Nie osłabiaj warunków końcowych**
4. **Zachowuj niezmienniki**
5. **Preferuj kompozycję gdy dziedziczenie narusza LSP**

### Ćwiczenie

Zidentyfikuj i napraw naruszenie LSP:

```java
public class Account {
    protected double balance;
    
    public void withdraw(double amount) {
        balance -= amount;
    }
}

public class SavingsAccount extends Account {
    @Override
    public void withdraw(double amount) {
        if (amount > 1000) {
            throw new IllegalArgumentException("Limit exceeded!");
        }
        super.withdraw(amount);
    }
}
```

**Pytanie**: Dlaczego to narusza LSP? Jak to naprawić?

### Referencje

- Barbara Liskov, "Data Abstraction and Hierarchy" (1988)
- Robert C. Martin, "Agile Software Development: Principles, Patterns, and Practices"
- Bertrand Meyer, "Object-Oriented Software Construction"

