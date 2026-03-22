# Moduł 3.11: Javadoc i dokumentowanie API

## Wprowadzenie

Javadoc tworzy dokumentację API bezpośrednio z komentarzy `/** ... */`. Dzięki temu dokumentacja jest blisko kodu i może być utrzymywana razem z implementacją.

### Czego nauczysz się w tym module?
- jak pisać komentarze Javadoc dla klas i metod,
- jak generować dokumentację HTML,
- jak utrzymać spójny styl opisu API.

---

## Diagram koncepcji

![Diagram javadoc](diagrams/javadoc_flow.png)

Diagram PlantUML: [`diagrams/javadoc_flow.puml`](diagrams/javadoc_flow.puml)

---

## Kod i omówienie

Plik z przykładem:
- [`src/inheritance/t11/JavadocDemo.java`](src/inheritance/t11/JavadocDemo.java)

W przykładzie zobaczysz opis klasy, metod publicznych i parametrów.

---

## Przykładowe polecenia

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03-dziedziczenie"
.\run-all-examples.ps1
```

```powershell
javadoc -d out-docs src\inheritance\t11\JavadocDemo.java
```

---

## Najczęstsze błędy

1. Komentarze niezgodne z zachowaniem metody po zmianach kodu.
2. Brak `@param` i `@return` dla metod publicznych.
3. Traktowanie Javadoc jako formalności, a nie części API.
