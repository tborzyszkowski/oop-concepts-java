# Modul 3.5: Dynamiczne wiazanie metod

## Wprowadzenie

W Javie metody instancyjne sa wiazane dynamicznie (late binding). Kompilator sprawdza typ referencji, ale konkretna implementacja jest wybierana przez JVM na podstawie rzeczywistego typu obiektu.

### Czego nauczysz sie w tym module?
- jak dziala dynamic dispatch,
- czym rozni sie late binding od early binding,
- jakie to ma konsekwencje dla projektowania API.

---

## Diagram koncepcji

![Diagram dynamicznego wiazania](diagrams/dynamic_binding.png)

Diagram PlantUML: [`diagrams/dynamic_binding.puml`](diagrams/dynamic_binding.puml)

---

## Kod i omowienie

Plik z przykladem:
- [`src/inheritance/t05/DynamicBindingDemo.java`](src/inheritance/t05/DynamicBindingDemo.java)

W przykladzie ta sama referencja bazowa wywoluje rozne implementacje, zalezne od typu obiektu.

---

## Najczestsze bledy

1. Oczekiwanie, ze pola beda dzialaly jak metody (pola nie sa polimorficzne).
2. Mylenie przeciazania z dynamicznym wiazaniem.
3. Zakladanie, ze `private` i `static` metody sa wiazane dynamicznie.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
