# Modul 3.6: Polimorfizm i wzorce projektowe

## Wprowadzenie

Polimorfizm pozwala programowac do abstrakcji, a nie do konkretnej klasy. To podstawa wielu wzorcow projektowych, np. Strategy, gdzie zachowanie mozna podmieniac bez zmiany kodu klienta.

### Czego nauczysz sie w tym module?
- jak wykorzystac polimorfizm do ograniczenia instrukcji warunkowych,
- jak dziala wzorzec Strategy w praktyce,
- jak oddzielac kod klienta od implementacji szczegolow.

---

## Diagram koncepcji

![Diagram wzorca Strategy](diagrams/polymorphism_patterns.png)

Diagram PlantUML: [`diagrams/polymorphism_patterns.puml`](diagrams/polymorphism_patterns.puml)

---

## Kod i omowienie

Plik z przykladem:
- [`src/inheritance/t06/PolymorphismPatternsDemo.java`](src/inheritance/t06/PolymorphismPatternsDemo.java)

Fragment:

```java
Checkout checkout = new Checkout(new BlikPayment());
System.out.println(checkout.finalizeOrder(199.99));
```

Klasa `Checkout` nie zna szczegolow platnosci. Zalezy od abstrakcji, dzieki czemu latwo dodac nowa metode platnosci.

---

## Najczestsze bledy

1. Tworzenie duzych blokow `if/switch` zamiast polimorfizmu.
2. Laczenie logiki biznesowej i infrastrukturalnej w jednej klasie.
3. Brak testow kontraktowych dla wspolnego interfejsu.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
