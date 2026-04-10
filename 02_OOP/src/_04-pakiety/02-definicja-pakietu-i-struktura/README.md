# Moduł 4.2: Definiowanie pakietu i struktura fizyczna katalogów

## Wprowadzenie

### 🎯 Czego się nauczysz w tym module?

- Jak poprawnie deklarować pakiet w pliku Java.
- Dlaczego struktura katalogów **musi** odzwierciedlać hierarchię pakietów.
- Czym jest **domyślny (pusty) pakiet** i dlaczego należy go unikać.
- Jak dokumentować pakiet za pomocą pliku `package-info.java`.

---

## Deklaracja pakietu

```java
package edu.university.registration.service;
```

Zasady:
1. Musi być **pierwszą instrukcją** w pliku (przed importami i kodem klasy).
2. Tylko **jedna deklaracja** `package` na plik.
3. Nazwa pakietu — **wyłącznie małe litery**, segmenty oddzielone kropką.
4. Brak deklaracji = klasa należy do **pakietu domyślnego** (tzw. *unnamed package*) — patrz uwaga poniżej.

---

## Obowiązkowe dopasowanie katalogów

Kompilator (i JVM) **wymagają**, aby ścieżka katalogu odpowiadała nazwie pakietu.

Plik `RegistrationService.java` z deklaracją:
```java
package edu.university.registration.service;
```

**musi** znajdować się pod ścieżką:
```
src/
└── edu/
    └── university/
        └── registration/
            └── service/
                └── RegistrationService.java
```

Jeżeli katalog nie zgadza się z pakietem, kompilator zgłosi błąd.

---

## Przykład struktury projektu

![Struktura pakietów](diagrams/package_structure.png)

Kod źródłowy:

- [`src/edu/university/registration/model/Student.java`](src/edu/university/registration/model/Student.java) — model danych (rekord Java 16+),
- [`src/edu/university/registration/service/RegistrationService.java`](src/edu/university/registration/service/RegistrationService.java) — logika,
- [`src/edu/university/registration/app/PackageStructureDemo.java`](src/edu/university/registration/app/PackageStructureDemo.java) — punkt wejścia.

Fragment importu w warstwie `app`:

```java
import edu.university.registration.model.Student;
import edu.university.registration.service.RegistrationService;
```

---

## Domyślny pakiet — antywzorzec

Klasy bez deklaracji `package` należą do *unnamed package* (pakietu domyślnego).

```java
// ŹLE — brak deklaracji package
public class Helper {
    // ...
}
```

Problemy:
- **Nie można importować** takiej klasy z innego pakietu — `import Helper;` jest błędem składniowym.
- Narzędzia (Maven, Gradle, IDEs) mają kłopoty z wykryciem i uruchomieniem takich klas.
- Standardy Google i Oracle: *nigdy nie używaj domyślnego pakietu w produkcyjnym kodzie*.

---

## Konwencje nazewnicze

| Reguła | Przykład | Opis |
|--------|---------|------|
| Tylko małe litery | `pl.edu.demo` | ✅ poprawnie |
| Brak myślników | `pl.edu.my-app` | ❌ błąd składniowy |
| Odwrócona domena | `com.google.gson` | ✅ standard produkcyjny |
| Krótkie segmenty | `model`, `service`, `app` | ✅ czytelne |
| `utils` jako jedyny katalog | `utils` | ⚠️ antywzorzec — zbyt ogólne |

---

## package-info.java

Plik `package-info.java` służy do **dokumentowania pakietu** (Javadoc) i umieszczania adnotacji na poziomie pakietu:

```java
/**
 * Pakiet zawierający modele danych systemu rejestracji studentów.
 * Wszystkie klasy w tym pakiecie są rekordami (immutable value objects).
 *
 * @author Jan Kowalski
 * @since 1.0
 */
package edu.university.registration.model;
```

Pliki:
- [`src/edu/university/registration/package-info.java`](src/edu/university/registration/package-info.java)
- [`src/edu/university/registration/model/package-info.java`](src/edu/university/registration/model/package-info.java)

---

## ⚠️ Najczęstsze błędy

1. **Niezgodność ścieżki katalogu z deklaracją `package`** — kompilator odmówi kompilacji.
2. **Kompilacja tylko jednego pliku** bez podania ścieżki do katalogu źródłowego — zależności nie zostaną rozwiązane.
3. **Użycie domyślnego pakietu** — klasy są niedostępne poza tym samym „pseudopakietem".
4. **Wielkie litery** w nazwie pakietu — `edu.Univ.model` jest dopuszczalne składniowo, ale łamie powszechnie przyjęte konwencje.

---

## 📚 Literatura i materiały dodatkowe

- **Oracle JLS §7.4 — Package Declarations:** <https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html#jls-7.4>
- **Oracle Tutorial — Packages:** <https://docs.oracle.com/javase/tutorial/java/package/packages.html>
- **Oracle Javadoc Guide — package-info.java:** <https://docs.oracle.com/en/java/javase/21/javadoc/package-info-java.html>
- **Google Java Style Guide §2.1:** <https://google.github.io/styleguide/javaguide.html#s2.1-file-name>

---

## Uruchomienie przykładów

```powershell
.\run-examples.ps1
```


