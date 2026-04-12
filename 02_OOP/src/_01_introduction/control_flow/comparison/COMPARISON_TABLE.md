# Tabela porównawcza: Instrukcje sterujące — Java vs C vs Python

## Instrukcja warunkowa if/else

| Java | C | Python |
|------|---|--------|
| `if (x > 0) { ... }` | `if (x > 0) { ... }` | `if x > 0:` |
| `} else if (x < 0) {` | `} else if (x < 0) {` | `elif x < 0:` |
| `} else {` | `} else {` | `else:` |
| Nawiasy `()` wymagane | Nawiasy `()` wymagane | Nawiasy `()` opcjonalne |
| Klamry `{}` wymagane | Klamry `{}` wymagane | Wcięcia (indentacja) |

## Operator ternary

| Java | C | Python |
|------|---|--------|
| `cond ? a : b` | `cond ? a : b` | `a if cond else b` |
| Kolejność: warunek → tak → nie | Kolejność: warunek → tak → nie | Kolejność: tak → warunek → nie |

## switch/match

| Cecha | Java (stary styl) | Java 14+ (switch expr) | Java 21 (pattern) | C | Python (match, 3.10+) |
|-------|------------------|----------------------|-------------------|---|----------------------|
| Składnia | `switch(x) { case 1: ... break; }` | `switch(x) { case 1 -> ... }` | `switch(obj) { case Integer i -> ... }` | `switch(x) { case 1: ... break; }` | `match x:` `  case 1: ...` |
| Fall-through | ✅ (bez break) | ❌ (automatycznie) | ❌ | ✅ (bez break) | ❌ |
| Typy | `int`, `char`, `String`, `enum` | `int`, `char`, `String`, `enum` | Dowolny typ + patterns | `int`, `char`, `enum` | Dowolny typ |
| Pattern matching | ❌ | ❌ | ✅ | ❌ | ✅ (ograniczone) |
| Wyrażenie (wartość) | ❌ | ✅ | ✅ | ❌ | ✅ |
| goto | ❌ (labeled break) | ❌ | ❌ | ✅ (odradzane) | ❌ |

## Pętla for

| Java | C | Python |
|------|---|--------|
| `for (int i=0; i<n; i++)` | `for (int i=0; i<n; i++)` | `for i in range(n):` |
| `for (T x : collection)` | Brak for-each | `for x in collection:` |
| Deklaracja zmiennej w for | ✅ (od C99) | Nie dotyczy |
| Scope zmiennej ograniczony do for | ✅ | ✅ (od C99) | Nie (Python: wycieka) |

## Pętla while

| Java | C | Python |
|------|---|--------|
| `while (cond) { ... }` | `while (cond) { ... }` | `while cond:` |
| Identyczna składnia | Identyczna jak Java | Wcięcia zamiast `{}` |

## Pętla do-while

| Java | C | Python |
|------|---|--------|
| `do { ... } while (cond);` | `do { ... } while (cond);` | **Brak!** (emulacja: `while True: ... if not cond: break`) |
| Wykonanie co najmniej raz | Wykonanie co najmniej raz | — |

## break i continue

| Java | C | Python |
|------|---|--------|
| `break` — wyjście z pętli | `break` — wyjście z pętli | `break` — wyjście z pętli |
| `continue` — następna iteracja | `continue` — następna iteracja | `continue` — następna iteracja |
| `break etykieta` (labeled) | `goto` (odradzane) | Brak labelek |
| Brak `goto` | `goto` dostępne | Brak `goto` |

## Obsługa wyjątków

| Java | C | Python |
|------|---|--------|
| `try { } catch (Ex e) { } finally { }` | Brak (używa errno, longjmp) | `try: ... except Ex: ... finally: ...` |
| `throw new Exception()` | — | `raise Exception()` |
| Sprawdzane wyjątki (checked) | — | Brak |

## Unikalne cechy

| Cecha | Java | C | Python |
|-------|------|---|--------|
| `for-each` | ✅ | ❌ | ✅ |
| `try-with-resources` | ✅ | ❌ | `with` statement |
| `else` po pętli | ❌ | ❌ | ✅ |
| List comprehensions | ❌ (Stream API) | ❌ | ✅ |
| Pattern matching w switch | ✅ (Java 21) | ❌ | ✅ (match, Python 3.10) |
| Wskaźniki / arytmetyka adresów | ❌ | ✅ | ❌ |
| Automatyczne zarządzanie pamięcią | ✅ (GC) | ❌ (malloc/free) | ✅ (GC) |

