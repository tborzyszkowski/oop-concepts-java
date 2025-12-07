# Instrukcja uruchomienia przykładów SOLID

## Wymagania

- Java 21 lub nowsza
- (Opcjonalnie) PlantUML do generowania diagramów

## Struktura katalogów

```
solid/
├── README.md                          # Główny plik z opisem wszystkich zasad
├── SimpleSrpDemo.java                 # Proste demo SRP (bez pakietów)
├── SimpleOcpDemo.java                 # Proste demo OCP (bez pakietów)
├── single_responsibility/             # Zasada SRP
│   ├── README.md
│   ├── diagrams/                      # Diagramy PlantUML
│   ├── before/                        # Przykłady naruszenia zasady
│   ├── after/                         # Przykłady zgodne z zasadą
│   └── tests/                         # Testy jednostkowe
├── open_closed/                       # Zasada OCP
│   └── ...
├── liskov_substitution/               # Zasada LSP
│   └── ...
├── interface_segregation/             # Zasada ISP
│   └── ...
└── dependency_inversion/              # Zasada DIP
    └── ...
```

## Szybki start - Proste przykłady

### 1. Single Responsibility Principle (SRP)

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src\solid
javac SimpleSrpDemo.java
java SimpleSrpDemo
```

**Oczekiwany wynik:**
```
=== SRP Demo ===
Employee: Jan Kowalski
Pay: 6000.0
Saving: Jan Kowalski

✅ Each class has ONE responsibility!
```

### 2. Open/Closed Principle (OCP)

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src\solid
javac SimpleOcpDemo.java
java SimpleOcpDemo
```

**Oczekiwany wynik:**
```
=== OCP Demo ===
Total area: 227.30
✅ Added Triangle without modifying existing code!
```

## Uruchomienie pełnych przykładów z pakietami

### Przykład: SRP - Before (naruszenie zasady)

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
javac solid/single_responsibility/before/*.java
java solid.single_responsibility.before.EmployeeDemo
```

### Przykład: SRP - After (zgodność z zasadą)

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
javac solid/single_responsibility/after/*.java
java solid.single_responsibility.after.EmployeeDemo
```

### Przykład: OCP - After

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
javac solid/open_closed/after/*.java
java solid.open_closed.after.ShapeDemo
```

### Przykład: LSP - Before vs After

```powershell
# Naruszenie LSP
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
javac solid/liskov_substitution/before/*.java
java solid.liskov_substitution.before.LspDemo

# Zgodność z LSP
javac solid/liskov_substitution/after/*.java
java solid.liskov_substitution.after.LspDemo
```

### Przykład: ISP - After

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
javac solid/interface_segregation/after/*.java
java solid.interface_segregation.after.WorkerDemo
```

### Przykład: DIP - After

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
javac solid/dependency_inversion/after/*.java
java solid.dependency_inversion.after.UserServiceDemo
```

## Uruchomienie testów jednostkowych

Przykłady zawierają testy JUnit 5. Aby je uruchomić, potrzebujesz JUnit w classpath.

### Z użyciem JUnit (jeśli jest zainstalowany):

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src

# Kompilacja testów
javac -cp ".;junit-platform-console-standalone.jar" `
  solid/single_responsibility/tests/*.java

# Uruchomienie testów
java -jar junit-platform-console-standalone.jar `
  --class-path . `
  --scan-class-path
```

## Generowanie diagramów PlantUML

### Wymagania:
- PlantUML (plik .jar)
- Java

### Generowanie pojedynczego diagramu:

```powershell
java -jar plantuml.jar `
  solid/single_responsibility/diagrams/srp_violation.puml
```

### Generowanie wszystkich diagramów:

```powershell
# Wszystkie diagramy SRP
java -jar plantuml.jar `
  solid/single_responsibility/diagrams/*.puml

# Wszystkie diagramy we wszystkich zasadach
java -jar plantuml.jar `
  solid/**/diagrams/*.puml
```

Wygenerowane pliki PNG pojawią się w tych samych katalogach co pliki .puml.

## Kompilacja wszystkich przykładów naraz

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src

# Kompilacja wszystkich plików Java w katalogu solid
Get-ChildItem -Path solid -Filter *.java -Recurse | `
  ForEach-Object { javac $_.FullName }
```

lub prościej:

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
javac solid/**/*.java
```

**Uwaga**: Niektóre pliki mogą nie skompilować się razem ze względu na duplikaty nazw klas w pakietach `before` i `after`.

## Czyszczenie skompilowanych plików

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
Get-ChildItem -Path solid -Filter *.class -Recurse | Remove-Item
```

lub:

```powershell
cd C:\home\gitHub\oop-concepts-java\03_ADVANCED\src
rm solid/**/*.class
```

## Generowanie prezentacji z Pandoc

Pliki README.md są przygotowane do konwersji na slajdy PPTX.

### Wymagania:
- Pandoc

### Generowanie slajdów dla pojedynczej zasady:

```powershell
pandoc solid/single_responsibility/README.md `
  -o solid/single_responsibility/SRP_presentation.pptx
```

### Generowanie slajdów dla wszystkich zasad:

```powershell
# SRP
pandoc solid/single_responsibility/README.md `
  -o SRP_presentation.pptx

# OCP
pandoc solid/open_closed/README.md `
  -o OCP_presentation.pptx

# LSP
pandoc solid/liskov_substitution/README.md `
  -o LSP_presentation.pptx

# ISP
pandoc solid/interface_segregation/README.md `
  -o ISP_presentation.pptx

# DIP
pandoc solid/dependency_inversion/README.md `
  -o DIP_presentation.pptx
```

### Generowanie prezentacji zbiorczej:

```powershell
pandoc solid/README.md `
  -o SOLID_Overview.pptx
```

## Wskazówki

### Debugowanie

Jeśli kod nie kompiluje się:
1. Sprawdź wersję Java: `java -version` (wymagana 21+)
2. Upewnij się, że jesteś w odpowiednim katalogu
3. Sprawdź czy wszystkie zależności są dostępne

### Modyfikowanie przykładów

Możesz swobodnie modyfikować przykłady. Zalecane podejście:
1. Skopiuj plik do nowego katalogu
2. Usuń deklarację pakietu lub dostosuj ją
3. Modyfikuj kod
4. Kompiluj i testuj

### Dodawanie własnych przykładów

Możesz dodać własne przykłady w podobnej strukturze:
```
solid/my_example/
├── README.md
├── diagrams/
├── before/
└── after/
```

## Rozwiązywanie problemów

### Problem: `cannot find symbol`
- Upewnij się, że wszystkie pliki w pakiecie są skompilowane
- Sprawdź nazwę pakietu w deklaracji `package`

### Problem: `class not found`
- Uruchamiaj z odpowiedniego katalogu (zwykle `src/`)
- Używaj pełnej nazwy klasy z pakietem

### Problem: Błędy kompilacji w testach
- Upewnij się, że JUnit jest w classpath
- Niektóre testy mogą wymagać Java 21+

## Dodatkowe zasoby

- Dokumentacja w plikach README.md każdej zasady
- Diagramy PlantUML w katalogach `diagrams/`
- Komentarze w kodzie źródłowym

## Kontakt i wkład

To są materiały dydaktyczne. Możesz:
- Modyfikować dla własnych potrzeb
- Zgłaszać sugestie ulepszeń
- Używać w celach edukacyjnych (zgodnie z licencją CC BY-NC 4.0)

