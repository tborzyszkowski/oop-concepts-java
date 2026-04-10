# Moduł 4.1: Przestrzenie nazw i pakiety

## Wprowadzenie

### 🎯 Czego się nauczysz w tym module?

- Zrozumiesz, **dlaczego przestrzenie nazw są niezbędne** i jak problem ten rozwiązywano w C/C++ vs w Javie.
- Poznasz mechanizm **pakietów** jako jednostki przestrzeni nazw, hermetyzacji i dystrybucji.
- Nauczysz się stosować **konwencję odwróconej domeny** w nazwach pakietów.
- Zrozumiesz pojęcie **pełnej nazwy kwalifikowanej** (FQN — *Fully Qualified Name*).

---

## Problem: kolizje nazw bez przestrzeni nazw

W dużym projekcie wiele klas może mieć identyczne nazwy. Przykład z biblioteki standardowej Javy:

```java
java.util.Date   // data ogólnego użytku (czas + data)
java.sql.Date    // data na potrzeby baz danych (tylko data)
```

Bez przestrzeni nazw nie mogłyby współistnieć w jednym projekcie.
W języku C problem ten rozwiązywano prefixami nazw funkcji (np. `pthread_create`, `gtk_widget_show`), co było kłopotliwe i niespójne.

---

## Czym jest pakiet w Javie?

Pakiet to **nazwana przestrzeń nazw**, która:

1. **Zapobiega kolizjom nazw** — `account.User` i `admin.User` to dwie różne klasy,
2. **Grupuje powiązane klasy** — `java.util`, `java.io`, `java.net`,
3. **Tworzy granicę hermetyzacji** — klasy i metody bez modyfikatora dostępu są widoczne tylko wewnątrz pakietu (*package-private*),
4. **Jest jednostką dystrybucji** — JAR zawiera drzewo pakietów, Maven/Gradle adresuje zależności przez przestrzenie nazw (groupId / artifactId).

---

## Konwencja odwróconej domeny

Java przyjęła konwencję, w której nazwa pakietu zaczyna się od **odwróconej nazwy domeny** organizacji, a dalsze segmenty opisują moduł / warstwę:

```
com.oracle.jdbc.driver
pl.edu.agh.oop.pakiety
org.springframework.web.servlet
```

Dzięki temu:
- każda organizacja ma unikalną przestrzeń nazw globalnie,
- artefakty w Maven / Gradle są adresowane przez `groupId` = odwrócona domena.

Przykłady wbudowane w JDK:

| Pakiet | Zawartość |
|--------|-----------|
| `java.lang` | Klasy podstawowe (`String`, `Object`, `System`) — importowane automatycznie |
| `java.util` | Kolekcje, daty, `Scanner` |
| `java.io` / `java.nio` | Wejście/wyjście |
| `java.net` | Sieci, URL, gniazda |
| `javax.*` | Rozszerzenia standardowe (JDBC, Servlet, ...) |

---

## Pełna nazwa kwalifikowana (FQN)

Gdy dwa pakiety zawierają klasy o tej samej nazwie, używamy **FQN** (*Fully Qualified Name*):

```java
java.util.Date utilDate   = new java.util.Date();
java.sql.Date  sqlDate    = java.sql.Date.valueOf("2026-04-10");

pakiety.t01.account.User accountUser = new pakiety.t01.account.User("anna");
pakiety.t01.admin.User   adminUser   = new pakiety.t01.admin.User("root");
```

Pełny kod: [`src/pakiety/t01/NamespaceCollisionDemo.java`](src/pakiety/t01/NamespaceCollisionDemo.java)

---

## Konwencja a rzeczywisty projekt

Demo [`src/pakiety/t01/ReverseDomainsDemo.java`](src/pakiety/t01/ReverseDomainsDemo.java) pokazuje, jak wyglądałoby logowanie pakietu w stylu produkcyjnym (`pl.edu.demo.*`) vs uproszczonym (`pakiety.*`).

---

## Diagram

![Kolizja nazw i rozwiązanie przez FQN](diagrams/namespace_collision.png)

Źródło: `diagrams/namespace_collision.puml`

---

## ⚠️ Najczęstsze błędy

1. **Stosowanie domyślnego (pustego) pakietu w produkcyjnym kodzie** — klasy bez deklaracji `package` są trudne do importowania i nieprzenośne.
2. **Płaskie pakiety `utils`, `helpers`, `common`** — skupiaj pakiety wokół funkcjonalności biznesowej, nie technicznej.
3. **Wielkie litery w nazwie pakietu** — tylko małe litery; `com.firma.MojPaKiet` jest błędem stylistycznym.

---

## 📚 Literatura i materiały dodatkowe

- **Oracle JLS §7 — Packages:** <https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html>
- **Oracle Tutorial — Creating and Using Packages:** <https://docs.oracle.com/javase/tutorial/java/package/createpkgs.html>
- **Google Java Style Guide — Package Names:** <https://google.github.io/styleguide/javaguide.html#s5.2.1-package-names>
- **Effective Java (3rd ed.)**, Joshua Bloch — Item 9: Always override hashCode when you override equals *(kontekst: znaczenie FQN przy hashCode)*

---

## Uruchomienie przykładów

```powershell
.\run-examples.ps1
```


