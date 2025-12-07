# Interface Segregation Principle (ISP)
- Robert C. Martin, "Agile Software Development: Principles, Patterns, and Practices"

### Referencje

4. **Używaj kompozycji interfejsów**
3. **Unikaj pustych implementacji**
2. **Segreguj według potrzeb klientów**
1. **Preferuj wiele małych interfejsów**

### Kluczowe zasady

| Elastyczność | Niska | Wysoka |
| Sprzężenie | Wysokie | Niskie |
| Implementacje | Częściowe | Pełne |
| Rozmiar interfejsu | Duży | Mały |
|--------|--------------|-----|
| Aspekt | Fat Interface | ISP |

### Podsumowanie

```
}
    void delete(T entity);
    void save(T entity);
interface WriteRepository<T> {

}
    List<T> findAll();
    T findById(Long id);
interface ReadRepository<T> {
// ✅ Zgodne z ISP

}
    void update(T entity);
    void delete(T entity);
    void save(T entity);
    List<T> findAll();
    T findById(Long id);
interface Repository<T> {
// ❌ Naruszenie ISP
```java

#### Przykład 2: Repozytoria

```
class SimplePrinter implements Printer { }
class AllInOnePrinter implements Printer, Scanner, Fax { }

interface Fax { void fax(); }
interface Scanner { void scan(); }
interface Printer { void print(); }
// ✅ Zgodne z ISP

}
    void fax();
    void scan();
    void print();
interface MultiFunctionDevice {
// ❌ Naruszenie ISP
```java

#### Przykład 1: Urządzenia wielofunkcyjne

### Praktyczne przykłady

- ✅ Niższe sprzężenie
- ✅ Lepsza separacja odpowiedzialności
- ✅ Łatwiejsze testowanie
- ✅ Większa elastyczność
- ✅ Mniejsze interfejsy = łatwiejsza implementacja

### Korzyści ISP

![Diagram klas - ISP](diagrams/isp_class_diagram.png)

### Diagram klas

- [after/WorkerDemo.java](after/WorkerDemo.java) - Demonstracja
- [after/Workable.java](after/Workable.java) - Segregowane interfejsy
- [before/Worker.java](before/Worker.java) - Fat Interface
Zobacz kod w plikach:

### Przykłady implementacji

![Rozwiązanie - ISP](diagrams/isp_solution.png)

```
}
    @Override public void work() { /* ... */ }
public class Robot implements Workable {
// Robot implementuje tylko potrzebne

}
    @Override public void getSalary() { /* ... */ }
    @Override public void sleep() { /* ... */ }
    @Override public void eat() { /* ... */ }
    @Override public void work() { /* ... */ }
public class Human implements Workable, Eatable, Sleepable, Payable {
// Człowiek implementuje wszystkie

}
    void getSalary();
public interface Payable {

}
    void sleep();
public interface Sleepable {

}
    void eat();
public interface Eatable {

}
    void work();
public interface Workable {
// ✅ Małe, specyficzne interfejsy
```java

### Rozwiązanie - zastosowanie ISP

![Problem - Fat Interface](diagrams/isp_violation.png)

```
}
    }
        throw new UnsupportedOperationException(); // ❌
    public void getSalary() { 
    @Override
    
    }
        throw new UnsupportedOperationException(); // ❌
    public void sleep() { 
    @Override
    
    }
        throw new UnsupportedOperationException(); // ❌
    public void eat() { 
    @Override
    
    public void work() { /* OK */ }
    @Override
public class Robot implements Worker {
// Robot nie je ani nie śpi!

}
    void getSalary();
    void sleep();
    void eat();
    void work();
public interface Worker {
// ❌ "Fat Interface" - zbyt szeroki interfejs
```java

### Problem - naruszenie ISP

- **Interfejsy powinny być segregowane** według potrzeb klientów
- **Klienci powinni wiedzieć tylko o metodach, których używają**
- **Małe, specyficzne interfejsy** są lepsze niż duże, ogólne
Zasada ISP oznacza, że:

### Wyjaśnienie koncepcji

> "No client should be forced to depend on methods it does not use." - Robert C. Martin

**Interface Segregation Principle** stwierdza, że klienci nie powinni być zmuszani do zależności od interfejsów, których nie używają.

### Definicja

## Zasada Segregacji Interfejsów


