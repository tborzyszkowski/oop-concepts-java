# Dependency Inversion Principle (DIP)

## Zasada Odwrócenia Zależności

### Definicja

**Dependency Inversion Principle** stwierdza, że:
1. Moduły wysokiego poziomu nie powinny zależeć od modułów niskiego poziomu. Oba powinny zależeć od abstrakcji.
2. Abstrakcje nie powinny zależeć od szczegółów. Szczegóły powinny zależeć od abstrakcji.

> "Depend upon abstractions, not concretions." - Robert C. Martin

### Wyjaśnienie koncepcji

DIP oznacza:
- **Zależność od abstrakcji** (interfejsy, klasy abstrakcyjne), nie konkretnych klas
- **Odwrócenie kierunku zależności** - szczegóły zależą od abstrakcji
- **Dependency Injection** - wstrzykiwanie zależności z zewnątrz

### Problem - naruszenie DIP

```java
// ❌ Moduł wysokiego poziomu zależy od szczegółów
public class UserService {
    private MySQLDatabase database = new MySQLDatabase(); // ❌ Konkretna klasa
    
    public void saveUser(User user) {
        database.save(user);
    }
}

// Zmiana bazy danych wymaga modyfikacji UserService!
```

![Problem - naruszenie DIP](diagrams/dip_violation.png)

### Rozwiązanie - zastosowanie DIP

```java
// ✅ Abstrakcja
public interface Database {
    void save(User user);
}

// Szczegóły implementują abstrakcję
public class MySQLDatabase implements Database {
    @Override
    public void save(User user) { /* MySQL specific */ }
}

public class MongoDatabase implements Database {
    @Override
    public void save(User user) { /* MongoDB specific */ }
}

// Moduł wysokiego poziomu zależy od abstrakcji
public class UserService {
    private final Database database; // ✅ Abstrakcja
    
    public UserService(Database database) { // Dependency Injection
        this.database = database;
    }
    
    public void saveUser(User user) {
        database.save(user);
    }
}
```

![Rozwiązanie - DIP](diagrams/dip_solution.png)

### Przykłady implementacji

Zobacz kod w plikach:
- [before/UserService.java](before/UserService.java) - Naruszenie DIP
- [after/Database.java](after/Database.java) - Abstrakcja
- [after/UserService.java](after/UserService.java) - DIP + DI
- [after/UserServiceDemo.java](after/UserServiceDemo.java) - Demonstracja

### Diagram klas

![Diagram klas - DIP](diagrams/dip_class_diagram.png)

### Korzyści DIP

- ✅ Łatwa zmiana implementacji
- ✅ Lepsze testowanie (mockowanie)
- ✅ Niskie sprzężenie
- ✅ Wysoka elastyczność
- ✅ Zgodność z Open/Closed Principle

### Dependency Injection

Trzy główne sposoby:

#### 1. Constructor Injection (zalecane)
```java
public class Service {
    private final Dependency dep;
    
    public Service(Dependency dep) {
        this.dep = dep;
    }
}
```

#### 2. Setter Injection
```java
public class Service {
    private Dependency dep;
    
    public void setDependency(Dependency dep) {
        this.dep = dep;
    }
}
```

#### 3. Interface Injection
```java
public interface DependencyInjector {
    void injectDependency(Dependency dep);
}
```

### Praktyczne przykłady

#### Przykład 1: Logowanie

```java
// ✅ DIP
public interface Logger {
    void log(String message);
}

public class FileLogger implements Logger { }
public class ConsoleLogger implements Logger { }

public class Application {
    private final Logger logger;
    
    public Application(Logger logger) {
        this.logger = logger;
    }
}
```

#### Przykład 2: Notyfikacje

```java
// ✅ DIP
public interface NotificationService {
    void send(String message);
}

public class EmailService implements NotificationService { }
public class SMSService implements NotificationService { }
public class PushService implements NotificationService { }

public class OrderProcessor {
    private final NotificationService notifier;
    
    public OrderProcessor(NotificationService notifier) {
        this.notifier = notifier;
    }
}
```

### Podsumowanie

| Aspekt | Bez DIP | Z DIP |
|--------|---------|-------|
| Zależności | Konkretne klasy | Abstrakcje |
| Sprzężenie | Wysokie | Niskie |
| Testowanie | Trudne | Łatwe |
| Elastyczność | Niska | Wysoka |

### Kluczowe zasady

1. **Zależymy od abstrakcji**
2. **Używaj Dependency Injection**
3. **Preferuj constructor injection**
4. **Abstrakcje są stabilne**

### Referencje

- Robert C. Martin, "Agile Software Development: Principles, Patterns, and Practices"
- Martin Fowler, "Inversion of Control Containers and the Dependency Injection pattern"

