# Modul 3.8: Abstrakcje, DI i wstrzykiwanie zaleznosci

## Wprowadzenie

Wstrzykiwanie zaleznosci dziala najlepiej, gdy modul wysokiego poziomu zalezy od abstrakcji, a nie od konkretnej implementacji. Dziedziczenie i interfejsy sa tu narzedziem do budowy luzno powiazanych komponentow.

### Czego nauczysz sie w tym module?
- jak abstrakcja wspiera testowalnosc kodu,
- jak dziala reczne wstrzykiwanie zaleznosci,
- jak przygotowac kod pod kontenery DI.

---

## Diagram koncepcji

![Diagram DI](diagrams/di_abstraction.png)

Diagram PlantUML: [`diagrams/di_abstraction.puml`](diagrams/di_abstraction.puml)

---

## Kod i omowienie

Plik z przykladem:
- [`src/inheritance/t08/DiAbstractionDemo.java`](src/inheritance/t08/DiAbstractionDemo.java)

Przyklad pokazuje, jak ten sam komponent klienta moze pracowac z rozna implementacja dostarczona z zewnatrz.

---

## Najczestsze bledy

1. Tworzenie zaleznosci (`new`) wewnatrz logiki biznesowej.
2. Wstrzykiwanie konkretnych klas zamiast interfejsow.
3. Brak testow jednostkowych dla komponentow korzystajacych z DI.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
