# Moduł 3.9: `final` dla klas, metod i pól

## Wprowadzenie

`final` ogranicza możliwość modyfikacji API i wspiera niemutowalność. Może dotyczyć klasy, metody lub pola i pomaga budować bardziej przewidywalny kod.

### Czego nauczysz się w tym module?
- kiedy stosować `final class`,
- jak `final` na metodzie chroni kontrakt,
- jak `final` na polu wspiera obiekty niemutowalne.
- czym różni się **Immutable Object** od **Value Object**,
- jak modelować Value Object w Javie i dlaczego `equals/hashCode` są kluczowe.

---

## Diagram koncepcji

![Diagram final](diagrams/final_usage.png)

Diagram PlantUML: [`diagrams/final_usage.puml`](diagrams/final_usage.puml)

---

## Kod i omówienie

Plik z przykładem:
- [`src/inheritance/t09/FinalKeywordDemo.java`](src/inheritance/t09/FinalKeywordDemo.java)

W kodzie zobaczysz trzy poziomy użycia `final`:
- `ImmutablePoint` jako prosty niemutowalny obiekt,
- `BaseService.audit()` jako metoda, której nie można nadpisać,
- `Money` jako pełny **Value Object**.

### Immutable Object a Value Object

To pojęcia powiązane, ale nie tożsame.

**Immutable Object (obiekt niemutowalny)**
- Po utworzeniu nie zmienia stanu.
- W Javie zwykle osiągamy to przez: `final class`, `private final` pola, brak setterów i walidację w konstruktorze.

**Value Object (obiekt wartościowy)**
- Opisuje wartość w domenie (np. `Money`, `Email`, `Range`).
- Nie ma „tożsamości biznesowej” jak encja.
- Dwa obiekty o tej samej wartości powinny być równe logicznie (`equals/hashCode`).
- W praktyce Value Object **powinien być niemutowalny**.

Wniosek: każdy dobrze zaprojektowany Value Object jest niemutowalny, ale nie każdy obiekt niemutowalny jest Value Object.

### Przykład Value Object: `Money`

Fragment z kodu:

```java
final class Money {
	private final BigDecimal amount;
	private final String currency;

	Money(BigDecimal amount, String currency) {
		this.amount = amount.setScale(2, RoundingMode.HALF_UP);
		this.currency = currency;
	}

	Money add(Money other) {
		requireSameCurrency(other);
		return new Money(amount.add(other.amount), currency);
	}

	@Override
	public boolean equals(Object obj) { ... }

	@Override
	public int hashCode() { ... }
}
```

Najważniejsze elementy dydaktyczne:
1. Metoda `add` nie modyfikuje obiektu, tylko zwraca nowy.
2. Równość oparta jest na wartości (`amount`, `currency`), nie na referencji.
3. Reguły domenowe (zgodność waluty) są zamknięte wewnątrz klasy.

### Kontrprzykład: niemutowalny obiekt, który nie jest Value Object

W tym samym pliku jest `SessionToken`:
- pola są `final`, więc obiekt jest niemutowalny,
- ale nie nadpisuje `equals/hashCode`,
- przez to `new SessionToken("tok-1", "ala")` i drugi taki sam obiekt nie są równe logicznie.

To dobry punkt do rozmowy o encjach i tożsamości.

### Kiedy stosować Value Object?

- Gdy chcesz zamknąć reguły domenowe w jednym miejscu.
- Gdy zależy Ci na bezpiecznym współdzieleniu obiektów bez ryzyka zmiany stanu.
- Gdy obiekt ma sens „wartościowy” (kwota, zakres, adres e-mail), a nie „tożsamościowy”.

---

## Najczęstsze błędy

1. Nadmierne oznaczanie wszystkiego jako `final` bez uzasadnienia projektowego.
2. Mylenie `final` referencji z niemutowalnością obiektu.
3. Blokowanie rozszerzalności klasy, która powinna być punktem rozszerzeń.
4. Tworzenie „pseudo Value Object” bez poprawnego `equals/hashCode`.
5. Używanie `double` dla pieniędzy zamiast `BigDecimal`.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
