# Moduł 04 — Pakiety i przestrzenie nazw w Javie

Moduł pokazuje, jak pakiety porządkują kod, zapobiegają kolizjom nazw i tworzą granicę hermetyzacji API.
Styl i struktura są spójne z modułami `_02_interfaces` i `_03-dziedziczenie`.

---

## Spis treści

| # | Temat | Katalog |
|---|-------|---------|
| 1 | [Przestrzenie nazw i pakiety — geneza, znaczenie, konwencje](01-przestrzenie-nazw-i-pakiety/README.md) | `01-przestrzenie-nazw-i-pakiety/` |
| 2 | [Definiowanie pakietu i struktura fizyczna katalogów](02-definicja-pakietu-i-struktura/README.md) | `02-definicja-pakietu-i-struktura/` |
| 3 | [CLASSPATH, kompilacja, JARy i System Modułów](03-classpath-i-kompilacja/README.md) | `03-classpath-i-kompilacja/` |
| 4 | [Kontrola dostępu w obrębie pakietów](04-kontrola-dostepu/README.md) | `04-kontrola-dostepu/` |
| 5 | [Importy, static import i kolizje nazw](05-importy-i-kolizje/README.md) | `05-importy-i-kolizje/` |
| 6 | [Zadania do samodzielnego rozwiązania](06-zadania/README.md) | `06-zadania/` |

---

## 📅 Sugerowany plan zajęć (90 min)

1. **Wstęp — problem i historia (10 min):** Dlaczego C/C++ miało kłopoty z nazwami? Jak Java rozwiązuje to przez pakiety. Konwencja odwróconej domeny. *(moduł 01)*
2. **Definicja i struktura (15 min):** Deklaracja `package`, obowiązkowe dopasowanie do katalogu, `package-info.java`, domyślny pakiet jako antywzorzec. *(moduł 02)*
3. **Narzędzia: kompilacja i dystrybucja (20 min):** `javac -cp`, `java -cp`, tworzenie JARów. Krótkie demo projektu bez IDE od `*.java` do uruchomienia. Wzmianka o JPMS. *(moduł 03)*
4. **Hermetyzacja API (15 min):** Tabela modyfikatorów dostępu. Kiedy `package-private` to dobry wybór? Projektowanie publicznego API. *(moduł 04)*
5. **Importy i kolizje (10 min):** `import`, `import static`, wildcard `*` i ukrywanie nazw. *(moduł 05)*
6. **Zadania (20 min):** Studenci rozwiązują wybrane zadania z modułu 06. *(moduł 06)*

---

## Uruchamianie przykładów

```powershell
# Z katalogu 02_OOP/src/_04-pakiety
.\run-all-examples.ps1

# Tylko testy zadań
.\run-tests.ps1

# Generowanie diagramów PNG
.\generate-diagrams.ps1

# Weryfikacja linków w dokumentacji
.\verify-links.ps1
```

---

## Struktura katalogu

```text
_04-pakiety/
├── 01-przestrzenie-nazw-i-pakiety/
│   ├── src/pakiety/t01/
│   │   ├── NamespaceCollisionDemo.java
│   │   ├── ReverseDomainsDemo.java
│   │   ├── account/User.java
│   │   └── admin/User.java
│   ├── diagrams/namespace_collision.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── 02-definicja-pakietu-i-struktura/
│   ├── src/edu/university/registration/
│   │   ├── package-info.java
│   │   ├── model/Student.java + package-info.java
│   │   ├── service/RegistrationService.java
│   │   └── app/PackageStructureDemo.java
│   ├── diagrams/package_structure.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── 03-classpath-i-kompilacja/
│   ├── src/classpathdemo/
│   │   ├── api/GreetingService.java
│   │   ├── impl/PolishGreetingService.java
│   │   └── app/ClasspathDemo.java + JarDemo.java
│   ├── diagrams/classpath_flow.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── 04-kontrola-dostepu/
│   ├── src/access/
│   │   ├── core/SecureDocument.java + SamePackageDemo.java
│   │   ├── friends/FriendInspector.java
│   │   └── outsiders/AccessControlDemo.java
│   ├── diagrams/access_levels.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── 05-importy-i-kolizje/
│   ├── src/imports/
│   │   ├── simple/SimpleImportDemo.java
│   │   ├── staticdemo/StaticImportDemo.java
│   │   ├── complex/billing/Order.java
│   │   ├── complex/shipping/Order.java
│   │   └── complex/reporting/ImportCollisionDemo.java
│   ├── diagrams/imports_collisions.puml/.png
│   ├── run-examples.ps1
│   └── README.md
├── 06-zadania/
│   ├── tasks/
│   ├── solutions/
│   ├── tests/
│   ├── diagrams/
│   ├── run-examples.ps1
│   └── README.md
├── generate-diagrams.ps1
├── run-all-examples.ps1
├── run-tests.ps1
├── verify-links.ps1
└── README.md  ← ten plik
```

---

## Cele dydaktyczne

Po ukończeniu modułu student:

- rozumie, czym jest przestrzeń nazw i jak pakiety implementują ją w Javie,
- potrafi zorganizować projekt w pakiety zgodne z konwencją odwróconej domeny,
- samodzielnie kompiluje i uruchamia wielopakietowy projekt bez IDE,
- świadomie dobiera modyfikatory dostępu (`private`, package-private, `protected`, `public`),
- korzysta z `import` i `import static`, rozwiązuje kolizje nazw przez FQN,
- rozumie powiązanie pakiet → JAR → Maven/Gradle.

---

## Literatura i źródła

- **Oracle Java Language Specification, § 7 — Packages:** <https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html>
- **Oracle Tutorial — Packages:** <https://docs.oracle.com/javase/tutorial/java/package/index.html>
- **Effective Java (3rd ed.)**, Joshua Bloch — Item 15: Minimize accessibility of classes and members.
- **Google Java Style Guide — Naming:** <https://google.github.io/styleguide/javaguide.html#s5.2.1-package-names>
- **OpenJDK JEP 261 — Module System:** <https://openjdk.org/jeps/261>

