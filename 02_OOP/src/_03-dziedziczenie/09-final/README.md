# Modul 3.9: `final` dla klas, metod i pol

## Wprowadzenie

`final` ogranicza mozliwosc modyfikacji API i wspiera niemutowalnosc. Moze dotyczyc klasy, metody lub pola i pomaga budowac bardziej przewidywalny kod.

### Czego nauczysz sie w tym module?
- kiedy stosowac `final class`,
- jak `final` na metodzie chroni kontrakt,
- jak `final` na polu wspiera obiekty niemutowalne.

---

## Diagram koncepcji

![Diagram final](diagrams/final_usage.png)

Diagram PlantUML: [`diagrams/final_usage.puml`](diagrams/final_usage.puml)

---

## Kod i omowienie

Plik z przykladem:
- [`src/inheritance/t09/FinalKeywordDemo.java`](src/inheritance/t09/FinalKeywordDemo.java)

W kodzie zobaczysz, co blokuje `final` i jakie bledy kompilacji pojawiaja sie przy probie naruszenia kontraktu.

---

## Najczestsze bledy

1. Nadmierne oznaczanie wszystkiego jako `final` bez uzasadnienia projektowego.
2. Mylenie `final` referencji z niemutowalnoscia obiektu.
3. Blokowanie rozszerzalnosci klasy, ktora powinna byc punktem rozszerzen.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
