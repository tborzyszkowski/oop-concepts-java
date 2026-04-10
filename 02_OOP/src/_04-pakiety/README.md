# Moduł 04 — Pakiety i przestrzenie nazw w Java

Ten moduł pokazuje, jak pakiety porządkują kod, zapobiegają kolizjom nazw i wspierają hermetyzację API.
Struktura i styl są spójne z modułami `02_OOP/src/_02-interfaces` oraz `02_OOP/src/_03-dziedziczenie`.

## Spis treści

| # | Temat | Katalog |
|---|-------|---------|
| 1 | [Przestrzenie nazw i pakiety: geneza i znaczenie](01-przestrzenie-nazw-i-pakiety/README.md) | `01-przestrzenie-nazw-i-pakiety/` |
| 2 | [Definiowanie pakietu i struktura katalogów](02-definicja-pakietu-i-struktura/README.md) | `02-definicja-pakietu-i-struktura/` |
| 3 | [CLASSPATH, kompilacja i uruchamianie](03-classpath-i-kompilacja/README.md) | `03-classpath-i-kompilacja/` |
| 4 | [Kontrola dostępu w obrębie pakietów](04-kontrola-dostepu/README.md) | `04-kontrola-dostepu/` |
| 5 | [Importy i kolizje nazw](05-importy-i-kolizje/README.md) | `05-importy-i-kolizje/` |
| 6 | [Zadania do samodzielnego rozwiązania + rozwiązania](06-zadania/README.md) | `06-zadania/` |

## Uruchomienie całości

```powershell
# Z katalogu 02_OOP/src/_04-pakiety
.\run-all-examples.ps1

# Testy / weryfikacja zadań
.\run-tests.ps1

# Generowanie diagramów PNG z PlantUML
.\generate-diagrams.ps1
```

## Cele dydaktyczne

- zrozumienie związku: `package` <-> fizyczna struktura katalogów,
- praktyczna obsługa importów i nazw kwalifikowanych,
- świadome używanie modyfikatorów dostępu (`private`, brak modyfikatora, `protected`, `public`),
- praca z `CLASSPATH` i parametrami `javac`/`java`,
- przygotowanie do pracy wielomodułowej i bibliotecznej.

## Literatura i źródła

- Oracle Java Language Specification, Chapter 7: Packages: <https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html>
- Oracle Tutorial: Packages: <https://docs.oracle.com/javase/tutorial/java/package/index.html>
- Effective Java (3rd ed.), Joshua Bloch — projektowanie API, hermetyzacja.
- OpenJDK JEP Index: <https://openjdk.org/jeps/0>

