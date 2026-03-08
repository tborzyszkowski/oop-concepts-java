# oop-concepts-java
Zajęcia z programowania obiektowego z przykładami w Java.

## Struktura projektu

```
oop-concepts-java/
├── 01_FUNDAMENTALS/          # Podstawy programowania obiektowego
├── 02_OOP/
│   └── src/
│       └── 01-introduction/  # ⭐ Moduł 01: Wprowadzenie do OOP w Javie
├── 03_ADVANCED/
│   └── src/
│       └── solid/            # ⭐ Zasady SOLID
├── DOCS/                     # Dokumentacja
├── EXERCISES/                # Ćwiczenia
├── PRESENTATIONS/            # Prezentacje
└── PROJECTS/                 # Projekty
```

---

## 📘 Moduł 01 — Wprowadzenie do OOP w Javie

W katalogu `02_OOP/src/01-introduction/` znajdziesz kompleksowe materiały dydaktyczne
wprowadzające do programowania obiektowego w Javie.

### Zawartość

| Podmoduł | Temat |
|----------|-------|
| `classes/` | Klasy i Obiekty — klasa, obiekt, pole, metoda, operator `new` |
| `fields_and_methods/` | Pola i Metody — modyfikatory dostępu, `static`, enkapsulacja |
| `object_lifecycle/` | Cykl życia obiektów — konstruktory, GC, kopie, Prototype |
| `control_flow/` | Instrukcje sterujące — Java vs C vs Python |
| `tdd/` | TDD — Red-Green-Refactor, JUnit 5, Stack jako przykład |
| `exercises/` | Zadania dla studentów |

### Szybki start

```powershell
cd 02_OOP\src\01-introduction
.\run-all-examples.ps1
```

📖 **Szczegółowa instrukcja**: [02_OOP/src/01-introduction/INSTRUKCJA.md](02_OOP/src/01-introduction/INSTRUKCJA.md)

---

## 🔷 Zasady SOLID

W katalogu `03_ADVANCED/src/solid/` znajdziesz kompleksowe materiały dydaktyczne o zasadach SOLID:

### Zawartość

**5 zasad SOLID** z pełną dokumentacją:
- **S** - Single Responsibility Principle (SRP)
- **O** - Open/Closed Principle (OCP)
- **L** - Liskov Substitution Principle (LSP)
- **I** - Interface Segregation Principle (ISP)
- **D** - Dependency Inversion Principle (DIP)

### Dla każdej zasady:

✅ **README.md** - wyczerpujące wyjaśnienie koncepcji  
✅ **Diagramy PlantUML** - wizualizacja problemów i rozwiązań  
✅ **Kod przed (before/)** - przykłady naruszenia zasady  
✅ **Kod po (after/)** - przykłady zgodne z zasadą  
✅ **Testy jednostkowe** - demonstracja testowalności  

### Statystyki materiałów:

- **📄 Pliki Markdown**: 7 (w tym główny README i instrukcja)
- **☕ Pliki Java**: 42+ przykładów kodu
- **📊 Diagramy PlantUML**: 16 diagramów
- **📝 Testy**: Testy jednostkowe JUnit 5

### Szybki start:

```powershell
# Przejdź do katalogu SOLID
cd 03_ADVANCED/src/solid

# Uruchom proste demo SRP
javac SimpleSrpDemo.java
java SimpleSrpDemo

# Uruchom proste demo OCP
javac SimpleOcpDemo.java
java SimpleOcpDemo
```

📖 **Szczegółowa instrukcja**: [03_ADVANCED/src/solid/INSTRUKCJA.md](03_ADVANCED/src/solid/INSTRUKCJA.md)

### Funkcje materiałów:

- ✅ Gotowe do uruchomienia przykłady w Java 21+
- ✅ Przygotowane do generowania slajdów PPTX (Pandoc)
- ✅ Diagramy w PlantUML (łatwe do modyfikacji)
- ✅ Obszerny opis każdej zasady z przykładami
- ✅ Referencje do kodu w dokumentacji
- ✅ Praktyczne wskazówki i pułapki

### Dla wykładowców:

Materiały są przygotowane do wykorzystania na wykładach:
- Pliki `.md` można przekonwertować na prezentacje PPTX za pomocą Pandoc
- Diagramy PlantUML można wygenerować jako obrazy PNG/SVG
- Kod jest dobrze skomentowany i gotowy do demonstracji
- Każda zasada ma przykłady "przed" i "po" dla porównania

## Wymagania

- **Java**: 21 lub nowsza
- **Pandoc**: (opcjonalnie) do generowania prezentacji
- **PlantUML**: (opcjonalnie) do generowania diagramów

## Licencja

Niniejsze materiały udostępniane są na licencji [Creative Commons Attribution-NonCommercial 4.0 International License (CC BY-NC 4.0)](https://creativecommons.org/licenses/by-nc/4.0/).

## Autor

Materiały dydaktyczne z programowania obiektowego w Java.

---

**Ostatnia aktualizacja**: Marzec 2026 — Moduł 01 przemianowany na `01-introduction`, dodano numerację modułów
