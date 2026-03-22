# Moduł 3.5: Dynamiczne wiązanie metod

## Wprowadzenie

W Javie metody instancyjne są wiązane dynamicznie (late binding). Kompilator sprawdza typ referencji, ale konkretna implementacja jest wybierana przez JVM na podstawie rzeczywistego typu obiektu.

### Czego nauczysz się w tym module?
- jak działa dynamic dispatch,
- czym różni się late binding od early binding,
- jakie to ma konsekwencje dla projektowania API.

---

## Diagram koncepcji

![Diagram dynamicznego wiązania](diagrams/dynamic_binding.png)

Diagram PlantUML: [`diagrams/dynamic_binding.puml`](diagrams/dynamic_binding.puml)

---

## Kod i omówienie

Plik z przykładem:
- [`src/inheritance/t05/DynamicBindingDemo.java`](src/inheritance/t05/DynamicBindingDemo.java)

W przykładzie ta sama referencja bazowa wywołuje różne implementacje, zależne od typu obiektu.

---

## Najczęstsze błędy

1. Oczekiwanie, że pola będą działały jak metody (pola nie są polimorficzne).
2. Mylenie przeciążania z dynamicznym wiązaniem.
3. Zakładanie, że `private` i `static` metody są wiązane dynamicznie.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
