# Moduł 3.3: Słowo kluczowe `super` w praktyce

## Wprowadzenie

`super` pozwala odwołać się do części klasy bazowej: konstruktora, metod i pól. To kluczowe narzędzie przy budowaniu klas potomnych, gdy chcesz rozszerzyć zachowanie zamiast je zastąpić.

### Czego nauczysz się w tym module?
- jak wywoływać konstruktor klasy bazowej,
- kiedy używać `super.method()`,
- jak unikać błędów inicjalizacji obiektu.

---

## Diagram koncepcji

![Diagram super](diagrams/super_usage.png)

Diagram PlantUML: [`diagrams/super_usage.puml`](diagrams/super_usage.puml)

---

## Kod i omówienie

Plik z przykładem:
- [`src/inheritance/t03/SuperKeywordDemo.java`](src/inheritance/t03/SuperKeywordDemo.java)

Fragment:

```java
Student(String name, String indexNumber) {
    super(name);
    this.indexNumber = indexNumber;
}
```

Najpierw inicjalizowana jest część klasy bazowej, dopiero później pola klasy potomnej.

---

## Najczęstsze błędy

1. Pomijanie wywołania odpowiedniego konstruktora bazowego.
2. Używanie `super` tam, gdzie wystarcza `this`.
3. Nadpisywanie metody i zapominanie o logice bazowej, gdy jest wymagana.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
