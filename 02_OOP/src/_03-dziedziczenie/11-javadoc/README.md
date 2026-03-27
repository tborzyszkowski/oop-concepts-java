# Moduł 3.11: Javadoc i dokumentowanie API

## Wprowadzenie

Javadoc tworzy dokumentację API bezpośrednio z komentarzy `/** ... */`. Dzięki temu dokumentacja jest blisko kodu i może być utrzymywana razem z implementacją.

### Czego nauczysz się w tym module?
- jak pisać komentarze Javadoc dla klas, interfejsów, rekordów i metod,
- jak używać tagów podstawowych i zaawansowanych,
- jak generować dokumentację w różnych trybach publikacji,
- jak utrzymać spójny styl opisu API i unikać typowych błędów.

---

## Diagram koncepcji

![Diagram javadoc](diagrams/javadoc_flow.png)

Diagram PlantUML: [`diagrams/javadoc_flow.puml`](diagrams/javadoc_flow.puml)

---

## Kod i omówienie

Pliki z przykładem:
- [`src/inheritance/t11/JavadocDemo.java`](src/inheritance/t11/JavadocDemo.java)
- [`src/inheritance/t11/package-info.java`](src/inheritance/t11/package-info.java)

W przykładzie znajdziesz większy scenariusz API:
- `Billable` (interfejs kontraktu),
- `ServiceItem` (rekord z walidacją),
- `TaxPolicy`, `AbstractTaxPolicy`, `VatTaxPolicy` (strategia i implementacja),
- `InvoiceCalculator` (API usługowe z metodą oznaczoną jako `@deprecated`).

---

## Tagi Javadoc - jak działają

### Tagi podstawowe

- `@param` - opisuje znaczenie i ograniczenia parametrów metody/konstruktora,
- `@return` - opisuje wartość zwracaną i jej semantykę,
- `@throws` - dokumentuje, kiedy i dlaczego metoda rzuca wyjątek.

Przykład (`InvoiceCalculator.netTotal`):

```java
/**
 * @param items lista pozycji
 * @return suma netto
 * @throws IllegalArgumentException gdy lista jest null lub pusta
 */
public BigDecimal netTotal(List<? extends Billable> items) { ... }
```

### Tagi nawigacyjne i semantyczne

- `@see` - odsyła do metod/typów powiązanych,
- `@since` - wskazuje wersję, od kiedy element istnieje,
- `@deprecated` - sygnalizuje API przestarzałe i wskazuje zamiennik.

Przykład (`grossTotalWithFixedVat`):

```java
/**
 * @deprecated Uzyj {@link #grossTotal(List, TaxPolicy)} i przekaz {@link VatTaxPolicy}.
 */
@Deprecated(since = "2.0", forRemoval = false)
public BigDecimal grossTotalWithFixedVat(List<? extends Billable> items) { ... }
```

### Tagi i konstrukcje bardziej zaawansowane

- `{@link ...}` - klikalne odwołanie do typu/metody,
- `{@inheritDoc}` - przejęcie opisu kontraktu z typu nadrzędnego,
- `{@code ...}` - bezpieczne formatowanie fragmentu kodu,
- `@author` i `@version` - metadane klasy/pakietu.

W module:
- `{@link ...}` jest użyte w opisie deprecacji metody `grossTotalWithFixedVat`,
- `{@inheritDoc}` jest użyte w metodzie `VatTaxPolicy.rate()`.

---

## Generowanie Javadoc w różnych formatach publikacji

Javadoc generuje dokumentację HTML. W praktyce "różne formaty" to najczęściej różny **zakres widoczności** i **poziom rygoru walidacji**.

### Szybkie uruchomienie gotowego skryptu

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie\11-javadoc"
.\generate-javadoc.ps1
```

Skrypt generuje katalogi:
- `out-docs/public`
- `out-docs/protected`
- `out-docs/private`
- `out-docs/linksource`
- `out-docs/draft`

### 1) Dokumentacja publicznego API

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
javadoc -d out-docs\t11-public -sourcepath .\11-javadoc\src -subpackages inheritance.t11 -public -encoding UTF-8 -charset UTF-8 -docencoding UTF-8
```

### 2) Dokumentacja dla zespołu (public + protected)

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
javadoc -d out-docs\t11-protected -sourcepath .\11-javadoc\src -subpackages inheritance.t11 -protected -encoding UTF-8 -charset UTF-8 -docencoding UTF-8
```

### 3) Pełna dokumentacja techniczna (także private)

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
javadoc -d out-docs\t11-private -sourcepath .\11-javadoc\src -subpackages inheritance.t11 -private -encoding UTF-8 -charset UTF-8 -docencoding UTF-8
```

### 4) Wersja ze źródłami i surową walidacją

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
javadoc -d out-docs\t11-linksource -sourcepath .\11-javadoc\src -subpackages inheritance.t11 -public -linksource -Xdoclint:all -encoding UTF-8 -charset UTF-8 -docencoding UTF-8
```

### 5) Wersja robocza bez doclint (na szybki draft)

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
javadoc -d out-docs\t11-draft -sourcepath .\11-javadoc\src -subpackages inheritance.t11 -public -Xdoclint:none -encoding UTF-8 -charset UTF-8 -docencoding UTF-8
```

---

## Uruchamianie kodu przykładu

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```

Uruchomienie tylko modułu `11-javadoc`:

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
New-Item -ItemType Directory -Force -Path out | Out-Null
javac --release 21 -d out .\11-javadoc\src\inheritance\t11\*.java
java -cp out inheritance.t11.JavadocDemo
```

---

## Najczęstsze błędy

1. Komentarze niezgodne z zachowaniem metody po zmianach kodu.
2. Brak `@param` i `@return` dla metod publicznych.
3. Brak `@throws`, mimo że metoda jawnie waliduje argumenty.
4. Oznaczenie API jako `@deprecated` bez wskazania zamiennika.
5. Traktowanie Javadoc jako formalności, a nie części kontraktu API.
