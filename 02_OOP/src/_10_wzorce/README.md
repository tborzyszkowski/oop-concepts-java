# Moduł 10 — Wzorce Projektowe (Design Patterns)

## Przegląd modułu

Klasyczne wzorce projektowe opisane przez **Gang of Four** (Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides) w książce *Design Patterns: Elements of Reusable Object-Oriented Software* (1994). Moduł omawia najważniejsze wzorce z każdej kategorii wraz z implementacją w Javie 21.

---

## Taksonomia wzorców GoF

| Kategoria | Wzorce w module |
|-----------|----------------|
| **Kreacyjne** | Singleton, Factory Method, Abstract Factory, Builder |
| **Strukturalne** | Adapter, Decorator, Facade, Proxy, Composite |
| **Behawioralne** | Observer, Strategy, Template Method, Command |

---

## Struktura modułu

| Katalog | Wzorzec | Kategoria |
|---------|---------|-----------|
| [`_01_wprowadzenie/`](_01_wprowadzenie/README.md) | Wprowadzenie do wzorców | — |
| [`_02_singleton/`](_02_singleton/README.md) | Singleton | Kreacyjny |
| [`_03_factory/`](_03_factory/README.md) | Factory Method + Abstract Factory | Kreacyjny |
| [`_04_builder/`](_04_builder/README.md) | Builder | Kreacyjny |
| [`_05_adapter/`](_05_adapter/README.md) | Adapter | Strukturalny |
| [`_06_decorator/`](_06_decorator/README.md) | Decorator | Strukturalny |
| [`_07_facade/`](_07_facade/README.md) | Facade | Strukturalny |
| [`_08_proxy/`](_08_proxy/README.md) | Proxy | Strukturalny |
| [`_09_observer/`](_09_observer/README.md) | Observer / Event | Behawioralny |
| [`_10_strategy/`](_10_strategy/README.md) | Strategy | Behawioralny |
| [`_11_template_method/`](_11_template_method/README.md) | Template Method | Behawioralny |
| [`_12_command/`](_12_command/README.md) | Command | Behawioralny |
| [`_13_composite/`](_13_composite/README.md) | Composite | Strukturalny |
| [`_14_zadania/`](_14_zadania/README.md) | Zadania do samodzielnego rozwiązania | — |

---

## Wymagania

- Java 21 lub nowsza
- PlantUML (do regeneracji diagramów)

---

## Jak uruchomić przykłady

### Sposób 1 — skrypt PowerShell

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\_10_wzorce
.\run-all-examples.ps1
```

### Sposób 2 — ręcznie

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src
javac -d _10_wzorce/out _10_wzorce/_02_singleton/code/SingletonDemo.java
java -cp _10_wzorce/out _10_wzorce._02_singleton.code.SingletonDemo
```

## Generowanie diagramów PNG

```powershell
cd C:\home\gitHub\oop-concepts-java\02_OOP\src\_10_wzorce
.\generate-diagrams.ps1
```

---

## Wzorce GoF w bibliotece standardowej Javy

| Wzorzec | Przykład w JDK |
|---------|---------------|
| Singleton | `Runtime.getRuntime()`, `System.console()` |
| Factory Method | `Calendar.getInstance()`, `NumberFormat.getInstance()` |
| Abstract Factory | `DocumentBuilderFactory`, `SAXParserFactory` |
| Builder | `StringBuilder`, `Stream.Builder`, `ProcessBuilder` |
| Adapter | `Arrays.asList()`, `InputStreamReader` |
| Decorator | `BufferedReader(new FileReader(...))`, `Collections.unmodifiableList()` |
| Facade | `javax.faces.context.FacesContext` |
| Proxy | `java.lang.reflect.Proxy`, Spring AOP |
| Observer | `java.util.Observer` (deprecated), `PropertyChangeListener` |
| Strategy | `Comparator`, `Runnable`, `Callable` |
| Template Method | `AbstractList`, `HttpServlet.doGet/doPost` |
| Command | `Runnable`, `java.swing.Action` |
| Composite | `java.awt.Component`, `javax.faces.component.UIComponent` |
| Iterator | `java.util.Iterator`, `for-each` |

---

## Literatura

- Erich Gamma et al., *Design Patterns: Elements of Reusable Object-Oriented Software*, Addison-Wesley, 1994
- Joshua Bloch, *Effective Java*, 3rd ed., Addison-Wesley, 2018
- [Refactoring.Guru — Design Patterns](https://refactoring.guru/design-patterns)
- [SourceMaking — Design Patterns](https://sourcemaking.com/design_patterns)

