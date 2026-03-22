# Modul 3.3: Slowo kluczowe `super` w praktyce

## Wprowadzenie

`super` pozwala odwolac sie do czesci klasy bazowej: konstruktora, metod i pol. To kluczowe narzedzie przy budowaniu klas potomnych, gdy chcesz rozszerzyc zachowanie zamiast je zastapic.

### Czego nauczysz sie w tym module?
- jak wywolywac konstruktor klasy bazowej,
- kiedy uzywac `super.method()`,
- jak unikac bledow inicjalizacji obiektu.

---

## Diagram koncepcji

![Diagram super](diagrams/super_usage.png)

Diagram PlantUML: [`diagrams/super_usage.puml`](diagrams/super_usage.puml)

---

## Kod i omowienie

Plik z przykladem:
- [`src/inheritance/t03/SuperKeywordDemo.java`](src/inheritance/t03/SuperKeywordDemo.java)

Fragment:

```java
Student(String name, String indexNumber) {
    super(name);
    this.indexNumber = indexNumber;
}
```

Najpierw inicjalizowana jest czesc klasy bazowej, dopiero pozniej pola klasy potomnej.

---

## Najczestsze bledy

1. Pomijanie wywolania odpowiedniego konstruktora bazowego.
2. Uzywanie `super` tam, gdzie wystarcza `this`.
3. Nadpisywanie metody i zapominanie o logice bazowej, gdy jest wymagana.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
