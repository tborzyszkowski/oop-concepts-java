# Moduł 02 — Interfejsy w Javie

## Spis treści

| # | Temat | Katalog |
|---|-------|---------|
| 1 | [Pojęcie interfejsu — geneza i znaczenie](#1-pojęcie-interfejsu) | `interfaces_intro/` |
| 2 | [Implementacja interfejsu w Java](#2-implementacja-interfejsu) | `interfaces_implementation/` |
| 3 | [Zaawansowane przykłady: Comparable, Functional, Iterable](#3-zaawansowane-przykłady) | `interfaces_advanced/` |
| 4 | [Szczególne właściwości: stałe, default, static, sealed](#4-szczególne-właściwości) | `interfaces_special/` |
| 5 | [Interfejsy a wzorce projektowe](#5-interfejsy-a-wzorce-projektowe) | `interfaces_patterns/` |
| 6 | [Zadania do samodzielnego rozwiązania](#6-zadania) | `exercises/` |

---

## Uruchomienie przykładów

```powershell
# Uruchom wszystkie przykłady naraz (z katalogu _02-interfaces):
.\run-all-examples.ps1

# Lub poszczególne moduły:
.\interfaces_intro\run-examples.ps1
.\interfaces_implementation\run-examples.ps1
.\interfaces_advanced\run-examples.ps1
.\interfaces_special\run-examples.ps1
.\interfaces_patterns\run-examples.ps1
```

---

## Struktura katalogu

```
_02-interfaces/
├── interfaces_intro/          # Pojęcie interfejsu, kontrakt, polimorfizm
│   ├── Printable.java
│   ├── Document.java
│   ├── Photo.java
│   ├── IntroDemo.java
│   ├── ContractDemo.java
│   ├── diagrams/
│   │   ├── interface_concept.puml / .png
│   │   └── interface_vs_abstract.puml / .png
│   ├── run-examples.ps1
│   └── README.md
├── interfaces_implementation/  # Implementacja, wielokrotne implements, rzutowanie
│   ├── Flyable.java
│   ├── Swimmable.java
│   ├── Duck.java
│   ├── Airplane.java
│   ├── MultiInterfaceDemo.java
│   ├── CastingDemo.java
│   ├── diagrams/
│   │   └── multi_implements.puml / .png
│   ├── run-examples.ps1
│   └── README.md
├── interfaces_advanced/        # Comparable, Comparator, FunctionalInterface, Iterable
│   ├── Product.java
│   ├── ComparableDemo.java
│   ├── Validator.java
│   ├── FunctionalDemo.java
│   ├── NumberRange.java
│   ├── IterableDemo.java
│   ├── diagrams/
│   │   ├── comparable_hierarchy.puml / .png
│   │   ├── functional_interfaces.puml / .png
│   │   └── iterable_iterator.puml / .png
│   ├── run-examples.ps1
│   └── README.md
├── interfaces_special/         # Stałe, default/static/private methods, dziedziczenie, sealed
│   ├── Vehicle.java
│   ├── ElectricVehicle.java
│   ├── Tesla.java
│   ├── DefaultMethodDemo.java
│   ├── SealedDemo.java
│   ├── diagrams/
│   │   ├── interface_inheritance.puml / .png
│   │   └── sealed_interface.puml / .png
│   ├── run-examples.ps1
│   └── README.md
├── interfaces_patterns/        # Strategy, Observer, Command, Factory Method
│   ├── StrategyDemo.java
│   ├── ObserverDemo.java
│   ├── CommandDemo.java
│   ├── FactoryDemo.java
│   ├── diagrams/
│   │   ├── strategy_pattern.puml / .png
│   │   ├── observer_pattern.puml / .png
│   │   ├── command_pattern.puml / .png
│   │   └── factory_pattern.puml / .png
│   ├── run-examples.ps1
│   └── README.md
├── exercises/
│   ├── tasks/                  # Zadania do samodzielnego rozwiązania
│   └── solutions/              # Wzorcowe rozwiązania
├── run-all-examples.ps1
└── README.md  ← ten plik
```

---

## Literatura i źródła

- **Joshua Bloch** — *Effective Java, 3rd ed.* (2018) — Item 20: Prefer interfaces to abstract classes
- **Bruce Eckel** — *Thinking in Java, 4th ed.* — rozdział "Interfaces"
- Oracle JDK docs: [Interface (Java SE 21)](https://docs.oracle.com/en/java/docs/api/java.base/java/lang/reflect/Interface.html)
- JEP 409 — Sealed Classes (Java 17): <https://openjdk.org/jeps/409>
- JEP 441 — Pattern Matching for switch (Java 21): <https://openjdk.org/jeps/441>

