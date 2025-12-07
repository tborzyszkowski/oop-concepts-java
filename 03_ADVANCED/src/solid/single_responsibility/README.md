# Single Responsibility Principle (SRP)
- [SOLID Principles (Wikipedia)](https://en.wikipedia.org/wiki/SOLID)
- Robert C. Martin, "Agile Software Development: Principles, Patterns, and Practices"
- Robert C. Martin, "Clean Code: A Handbook of Agile Software Craftsmanship"

### Referencje

**Wskazówka**: Zastanów się, ile odpowiedzialności ma ta klasa i jak można je rozdzielić.

```
}
    public String getBookInfo() { /* informacje */ }
    public void saveToFile() { /* zapis */ }
    public void printBook() { /* drukowanie */ }
    
    private String author;
    private String title;
public class Book {
```java
Zrefaktoruj klasę `Book`, która naruszuje SRP:

### Ćwiczenie

4. **Balans** - unikaj nadmiernego rozdrobnienia
3. **Separacja** - różne aspekty funkcjonalności w różnych klasach
2. **Spójność** - wszystkie elementy klasy służą tej samej odpowiedzialności
1. **Jedna odpowiedzialność = jeden powód do zmiany**

### Kluczowe zasady

| Ryzyko regresji | Wysokie | Niskie |
| Elastyczność zmian | Niska | Wysoka |
| Ponowne wykorzystanie | Trudne | Łatwe |
| Łatwość testowania | Niska | Wysoka |
| Liczba odpowiedzialności | 3+ | 1 |
|--------|-----------|---------|
| Aspekt | Przed SRP | Po SRP |

### Podsumowanie

✅ **Właściwe podejście** - balans między spójnością a rozdrobnieniem

```
}
    void saveToDatabase() { }
    void calculatePay() { }
    void fire() { }
    void hire() { }
public class EmployeeManager {
// ZŁE - dalej zbyt wiele odpowiedzialności
```java
❌ **Zbyt szerokie granice** - klasa dalej ma wiele odpowiedzialności

```
public class EmployeeNameSetter { }
public class EmployeeNameGetter { }
// ZŁE - przesadne rozdrobnienie
```java
❌ **Nadmierne rozdrobnienie** - tworzenie osobnych klas dla każdej metody

### Pułapki i błędy

5. Czy klasa ma więcej niż 5-7 metod publicznych?
4. Czy opis klasy zawiera słowa "i" lub "lub"?
3. Czy klasa ma zależności od wielu różnych modułów?
2. Czy metody w klasie operują na różnych zbiorach danych?
1. Czy klasa ma więcej niż jeden powód do zmiany?
Pytania pomocnicze:

### Jak rozpoznać naruszenie SRP?

![Diagram klas - SRP](diagrams/srp_class_diagram.png)

### Diagram klas

- [after/EmployeeDemo.java](after/EmployeeDemo.java) - Demonstracja rozwiązania
- [after/EmployeeReportGenerator.java](after/EmployeeReportGenerator.java) - Raportowanie
- [after/EmployeeRepository.java](after/EmployeeRepository.java) - Persystencja
- [after/PayrollCalculator.java](after/PayrollCalculator.java) - Logika biznesowa
- [after/Employee.java](after/Employee.java) - Model danych
- [before/EmployeeDemo.java](before/EmployeeDemo.java) - Demonstracja problemu
- [before/Employee.java](before/Employee.java) - Naruszenie SRP
Zobacz implementację w plikach:

### Przykład praktyczny

- ✅ Ponowne wykorzystanie - `PayrollCalculator` może być użyty w innych kontekstach
- ✅ Elastyczność - można zmienić sposób zapisu bez wpływu na logikę biznesową
- ✅ Łatwe testowanie - każda klasa może być testowana niezależnie
- ✅ Każda klasa ma jedną, jasno zdefiniowaną odpowiedzialność
**Korzyści:**

![Rozwiązanie - zastosowanie SRP](diagrams/srp_solution.png)

```
}
    }
               ", Position: " + employee.getPosition();
        return "Employee Report: " + employee.getName() + 
        // Logika generowania raportu
    public String generateReport(Employee employee) {
public class EmployeeReportGenerator {
// Odpowiedzialność: generowanie raportów

}
    }
        System.out.println("Saving employee: " + employee.getName());
        // Logika zapisu do bazy danych
    public void save(Employee employee) {
public class EmployeeRepository {
// Odpowiedzialność: persystencja danych

}
    }
        return employee.getSalary() * 1.2;
        // Logika obliczania wynagrodzenia
    public double calculatePay(Employee employee) {
public class PayrollCalculator {
// Odpowiedzialność: obliczanie wynagrodzenia

}
    public double getSalary() { return salary; }
    public String getPosition() { return position; }
    public String getName() { return name; }
    // Gettery
    
    }
        this.salary = salary;
        this.position = position;
        this.name = name;
    public Employee(String name, String position, double salary) {
    
    private double salary;
    private String position;
    private String name;
public class Employee {
// Odpowiedzialność: reprezentacja danych pracownika
```java

Rozdzielamy odpowiedzialności na osobne klasy:

### Rozwiązanie - zastosowanie SRP

![Problem - naruszenie SRP](diagrams/srp_violation.png)

4. **Niskie ponowne wykorzystanie** - nie można użyć samej logiki obliczania płacy
3. **Trudne testowanie** - wymaga mockowania bazy danych i innych zależności
   - Zmiana formatu raportów
   - Zmiana sposobu zapisu danych
   - Zmiana zasad obliczania wynagrodzenia
2. **Wiele powodów do zmiany**:
1. **Wiele odpowiedzialności**: logika biznesowa, persystencja, raportowanie
**Problemy:**

```
}
    }
        return "Employee Report: " + name;
        // Logika generowania raportu
    public String generateReport() {
    
    }
        System.out.println("Saving employee to database...");
        // Logika zapisu do bazy danych
    public void save() {
    
    }
        return salary * 1.2;
        // Logika obliczania wynagrodzenia
    public double calculatePay() {
    
    private double salary;
    private String position;
    private String name;
public class Employee {
```java

Rozważmy klasę `Employee`, która łączy w sobie wiele odpowiedzialności:

### Problem - naruszenie SRP

- **Lepsze separowanie zmian** - różne powody zmian nie kolidują ze sobą
- **Mniejsze ryzyko regresji** - zmiana w jednej odpowiedzialności nie wpływa na inne
- **Lepsze ponowne wykorzystanie** - klasy z jedną odpowiedzialnością są bardziej uniwersalne
- **Wyższa testowalność** - łatwiej przetestować klasę z jedną odpowiedzialnością
- **Łatwiejsze utrzymanie** - mniejsze klasy są prostsze do zrozumienia
#### Korzyści stosowania SRP:

- Mieć **tylko jeden powód do zmiany** - zmiana wymagań dotycząca danej odpowiedzialności
- Być **spójna** - wszystkie metody powinny służyć tej samej odpowiedzialności
- Mieć **jedną, dobrze zdefiniowaną odpowiedzialność**
Zasada SRP jest fundamentalną zasadą projektowania obiektowego. Oznacza ona, że każda klasa powinna:

### Wyjaśnienie koncepcji

> "A class should have one, and only one, reason to change." - Robert C. Martin

**Single Responsibility Principle** stwierdza, że klasa powinna mieć tylko jeden powód do zmiany, czyli powinna być odpowiedzialna tylko za jeden aspekt funkcjonalności systemu.

### Definicja

## Zasada Pojedynczej Odpowiedzialności


