# Moduł 3.9: `final` dla klas, metod i pól

## Wprowadzenie

`final` ogranicza możliwość modyfikacji API i wspiera niemutowalność. Może dotyczyć klasy, metody lub pola i pomaga budować bardziej przewidywalny kod.

### Czego nauczysz się w tym module?
- kiedy stosować `final class`,
- jak `final` na metodzie chroni kontrakt,
- jak `final` na polu wspiera obiekty niemutowalne.
- czym różni się **Immutable Object** od **Value Object**,
- jak modelować Value Object w Javie i dlaczego `equals/hashCode` są kluczowe.
- czym różni się **stała (constant)** od podejścia **read-only**,
- jak realizować read-only w Java przez `record`, niemutowalne kolekcje i defensywne kopie.

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

Dodatkowo moduł pokazuje:
- `AppConstants` (`static final`) jako przykład stałych aplikacyjnych,
- różnicę między `final` referencją a read-only obiektem,
- `Collections.unmodifiableList(...)` vs `List.copyOf(...)`,
- `ReadOnlyConfig` (`record`) jako nośnik danych read-only.

---

## Stała (constant) vs read-only - najważniejsze różnice

W projektowaniu obiektowym te pojęcia bywają mieszane, ale oznaczają różne poziomy gwarancji.

### Stała (constant)

Stała to wartość, której nie da się zmienić po inicjalizacji i która zwykle jest globalnie dostępna.
W Javie najczęściej modelujemy ją jako `public static final` (czasem z modyfikatorem o mniejszym zasięgu).

Przykład z kodu (`AppConstants`):

```java
final class AppConstants {
	static final String APP_NAME = "OOP-LAB";
	static final int MAX_RETRIES = 3;
	static final BigDecimal VAT_RATE = new BigDecimal("0.23");

	private AppConstants() {}
}
```

Uwagi dydaktyczne:
1. `static final` blokuje podmianę referencji.
2. Dla typów prostych i `String` to praktycznie "twarda" stała.
3. Dla obiektów (`BigDecimal`, `List`, własne klasy) trzeba nadal dbać o niemutowalność typu.

### Read-only

Read-only oznacza, że **klient API** może odczytywać dane, ale nie może ich modyfikować przez dany interfejs.
To nie zawsze znaczy, że dane pod spodem są niemutowalne.

W kodzie (`FinalKeywordDemo`) masz dwa warianty:
1. `Collections.unmodifiableList(source)` - read-only widok, który odzwierciedla zmiany w `source`.
2. `List.copyOf(source)` - niemutowalny snapshot (kopia stanu z chwili utworzenia).

---

## Dlaczego `final` to nie to samo co read-only?

To jedno z kluczowych pytań na pierwszych kursach OOP w Javie.

Fragment z demonstracji:

```java
final List<String> tags = new ArrayList<>();
tags.add("java");
tags.add("oop");
```

Wniosek:
- `final` blokuje **zmianę referencji** (`tags = innaLista` jest zabronione),
- ale nie blokuje **zmiany stanu obiektu**, jeśli obiekt jest mutowalny.

Dlatego "final field" != "immutable object".

---

## Read-only i niemutowalność w nowoczesnej Javie

### `record` jako gotowy nośnik danych read-only

W module użyto:

```java
record ReadOnlyConfig(String environment, int timeoutSeconds) { ... }
```

`record` daje:
- prywatne, finalne pola,
- brak setterów,
- domyślne `equals/hashCode/toString` oparte na stanie,
- czytelny model danych do warstw DTO/konfiguracji.

To dobry sposób na modelowanie małych obiektów wartościowych i read-only API.

### Defensywne kopie

W klasach niemutowalnych przekazujących kolekcje lub daty stosuj defensywne kopie przy:
- wejściu (w konstruktorze),
- wyjściu (w getterze),

aby klient nie mógł zmienić wewnętrznego stanu obiektu przez alias referencji.

---

## Jak to łączy się z Value Object?

Value Object zwykle spełnia jednocześnie:
1. jest niemutowalny,
2. ma semantykę równości po stanie (`equals/hashCode`),
3. enkapsuluje reguły domenowe.

W tym module `Money` jest przykładem Value Object, a `SessionToken` pokazuje, że sam brak setterów nie wystarczy, by obiekt był wartościowy.

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
6. Traktowanie `Collections.unmodifiableList(...)` jak głębokiej niemutowalności danych.
7. Uznanie `final` referencji za gwarancję, że obiekt jest read-only.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```
