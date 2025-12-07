# Szybki przewodnik po przykładach SOLID

## Przegląd wszystkich przykładów

### 1️⃣ Single Responsibility Principle (SRP)

**Idea**: Jedna klasa = jedna odpowiedzialność

#### Przykłady do uruchomienia:

```powershell
# Prosty przykład (bez pakietów)
cd 03_ADVANCED/src/solid
javac SimpleSrpDemo.java && java SimpleSrpDemo

# Pełny przykład PRZED (naruszenie)
cd 03_ADVANCED/src
javac solid/single_responsibility/before/*.java
java solid.single_responsibility.before.EmployeeDemo

# Pełny przykład PO (zgodność)
javac solid/single_responsibility/after/*.java
java solid.single_responsibility.after.EmployeeDemo
```

**Kluczowe pliki**:
- `Employee.java` (before) - klasa z wieloma odpowiedzialnościami ❌
- `Employee.java` (after) - tylko dane ✅
- `PayrollCalculator.java` - logika biznesowa ✅
- `EmployeeRepository.java` - persystencja ✅
- `EmployeeReportGenerator.java` - raportowanie ✅

---

### 2️⃣ Open/Closed Principle (OCP)

**Idea**: Otwarte na rozszerzanie, zamknięte na modyfikację

#### Przykłady do uruchomienia:

```powershell
# Prosty przykład
cd 03_ADVANCED/src/solid
javac SimpleOcpDemo.java && java SimpleOcpDemo

# Pełny przykład PRZED
cd 03_ADVANCED/src
javac solid/open_closed/before/*.java

# Pełny przykład PO
javac solid/open_closed/after/*.java
java solid.open_closed.after.ShapeDemo
```

**Kluczowe pliki**:
- `AreaCalculator.java` (before) - używa instanceof ❌
- `Shape.java` (after) - interfejs, stabilna abstrakcja ✅
- `Rectangle.java`, `Circle.java`, `Triangle.java` - implementacje ✅
- `AreaCalculator.java` (after) - działa z abstrakcją ✅

**Demonstracja**: Dodaj `Triangle` bez modyfikacji istniejącego kodu!

---

### 3️⃣ Liskov Substitution Principle (LSP)

**Idea**: Podklasy muszą być podstawialne za klasy bazowe

#### Przykłady do uruchomienia:

```powershell
cd 03_ADVANCED/src

# Problem Rectangle-Square (naruszenie)
javac solid/liskov_substitution/before/*.java
java solid.liskov_substitution.before.LspDemo

# Rozwiązanie (zgodność)
javac solid/liskov_substitution/after/*.java
java solid.liskov_substitution.after.LspDemo
```

**Kluczowe pliki**:
- `Rectangle.java` + `Square.java` (before) - Square narusza kontrakt ❌
- `Shape.java` (after) - interfejs dla obu ✅
- `Rectangle.java`, `Square.java` (after) - niezależne implementacje ✅

**Demonstracja**: Square jako podklasa Rectangle psuje test powierzchni!

---

### 4️⃣ Interface Segregation Principle (ISP)

**Idea**: Wiele małych interfejsów > jeden duży

#### Przykłady do uruchomienia:

```powershell
cd 03_ADVANCED/src

# Problem (before)
javac solid/interface_segregation/before/*.java

# Rozwiązanie (after)
javac solid/interface_segregation/after/*.java
java solid.interface_segregation.after.WorkerDemo
```

**Kluczowe pliki**:
- `Worker.java` (before) - "fat interface" ❌
- `Robot.java` (before) - zmuszony do pustych implementacji ❌
- `Workable.java`, `Eatable.java`, `Sleepable.java`, `Payable.java` (after) - małe interfejsy ✅
- `Robot.java` (after) - implementuje tylko Workable ✅
- `Human.java` (after) - implementuje wszystkie potrzebne ✅

**Demonstracja**: Robot nie musi implementować metod eat() i sleep()!

---

### 5️⃣ Dependency Inversion Principle (DIP)

**Idea**: Zależymy od abstrakcji, nie konkretnych klas

#### Przykłady do uruchomienia:

```powershell
cd 03_ADVANCED/src

# Problem (before)
javac solid/dependency_inversion/before/*.java

# Rozwiązanie (after)
javac solid/dependency_inversion/after/*.java
java solid.dependency_inversion.after.UserServiceDemo
```

**Kluczowe pliki**:
- `UserService.java` (before) - tworzy MySQLDatabase bezpośrednio ❌
- `Database.java` (after) - interfejs (abstrakcja) ✅
- `MySQLDatabase.java`, `MongoDatabase.java` (after) - implementacje ✅
- `UserService.java` (after) - przyjmuje Database przez konstruktor (DI) ✅

**Demonstracja**: Łatwa zmiana z MySQL na MongoDB bez modyfikacji UserService!

---

## Uruchamianie wszystkich przykładów na raz

### Skrypt PowerShell do testowania wszystkich:

```powershell
# Utwórz plik test-all-solid.ps1
$examples = @(
    @{Name="SRP Simple"; Path="solid"; File="SimpleSrpDemo"},
    @{Name="OCP Simple"; Path="solid"; File="SimpleOcpDemo"},
    @{Name="SRP After"; Path="solid/single_responsibility/after"; File="solid.single_responsibility.after.EmployeeDemo"},
    @{Name="OCP After"; Path="solid/open_closed/after"; File="solid.open_closed.after.ShapeDemo"},
    @{Name="LSP After"; Path="solid/liskov_substitution/after"; File="solid.liskov_substitution.after.LspDemo"},
    @{Name="ISP After"; Path="solid/interface_segregation/after"; File="solid.interface_segregation.after.WorkerDemo"},
    @{Name="DIP After"; Path="solid/dependency_inversion/after"; File="solid.dependency_inversion.after.UserServiceDemo"}
)

Set-Location "C:\home\gitHub\oop-concepts-java\03_ADVANCED\src"

foreach ($ex in $examples) {
    Write-Host "`n========== $($ex.Name) ==========" -ForegroundColor Cyan
    
    if ($ex.Path -eq "solid") {
        Set-Location "solid"
        javac "$($ex.File).java" 2>&1 | Out-Null
        java $ex.File
        Set-Location ".."
    } else {
        javac "$($ex.Path)/*.java" 2>&1 | Out-Null
        java $ex.File
    }
}
```

Uruchom: `.\test-all-solid.ps1`

---

## Testy jednostkowe

### Uruchomienie testów SRP:

```powershell
cd 03_ADVANCED/src

# Zakładając że masz JUnit 5 w classpath
javac -cp ".;junit-platform-console-standalone.jar" `
  solid/single_responsibility/tests/*.java `
  solid/single_responsibility/after/*.java

java -jar junit-platform-console-standalone.jar `
  --class-path . `
  --select-class solid.single_responsibility.after.PayrollCalculatorTest
```

---

## Generowanie diagramów

### Wszystkie diagramy naraz:

```powershell
# Zakładając że masz plantuml.jar
cd 03_ADVANCED/src/solid

# SRP
java -jar plantuml.jar single_responsibility/diagrams/*.puml

# OCP
java -jar plantuml.jar open_closed/diagrams/*.puml

# LSP
java -jar plantuml.jar liskov_substitution/diagrams/*.puml

# ISP
java -jar plantuml.jar interface_segregation/diagrams/*.puml

# DIP
java -jar plantuml.jar dependency_inversion/diagrams/*.puml

# Przegląd SOLID
java -jar plantuml.jar diagrams/solid_overview.puml
```

---

## Generowanie prezentacji

### Wszystkie prezentacje naraz:

```powershell
cd 03_ADVANCED/src/solid

# Pojedyncze zasady
pandoc single_responsibility/README.md -o SRP.pptx
pandoc open_closed/README.md -o OCP.pptx
pandoc liskov_substitution/README.md -o LSP.pptx
pandoc interface_segregation/README.md -o ISP.pptx
pandoc dependency_inversion/README.md -o DIP.pptx

# Przegląd wszystkich zasad
pandoc README.md -o SOLID_Overview.pptx
```

---

## Porównanie: Przed vs Po

### SRP - Employee

| Przed | Po |
|-------|-----|
| 1 klasa z 4 odpowiedzialnościami | 4 klasy, każda z 1 odpowiedzialnością |
| Trudne testowanie | Łatwe testowanie każdej osobno |
| Wysokie sprzężenie | Niskie sprzężenie |

### OCP - Shape Calculator

| Przed | Po |
|-------|-----|
| Modyfikacja przy każdym nowym kształcie | Dodanie klasy bez modyfikacji |
| instanceof/switch | Polimorfizm |
| Wysokie ryzyko regresji | Niskie ryzyko |

### LSP - Rectangle/Square

| Przed | Po |
|-------|-----|
| Square dziedziczy Rectangle | Oba implementują Shape |
| Naruszenie kontraktu | Własne kontrakty |
| Test failuje dla Square | Wszystkie podstawialne |

### ISP - Worker

| Przed | Po |
|-------|-----|
| 1 duży interfejs Worker | 4 małe interfejsy |
| Robot musi implementować eat/sleep | Robot tylko Workable |
| UnsupportedOperationException | Pełne implementacje |

### DIP - UserService

| Przed | Po |
|-------|-----|
| Zależność od MySQLDatabase | Zależność od Database (interface) |
| Nie można zmienić bazy | Łatwa zmiana przez DI |
| Niemożliwe mockowanie | Łatwe mockowanie |

---

## Wskazówki do nauki

### Kolejność nauki zasad:

1. **SRP** - najprostsza, fundamentalna
2. **OCP** - buduje na SRP
3. **DIP** - ważna dla OCP
4. **LSP** - wymaga zrozumienia dziedziczenia
5. **ISP** - uzupełnia pozostałe

### Kluczowe pytania przy projektowaniu:

- **SRP**: Czy klasa ma więcej niż jeden powód do zmiany?
- **OCP**: Czy mogę dodać funkcję bez modyfikacji?
- **LSP**: Czy podklasa może zastąpić klasę bazową?
- **ISP**: Czy klient jest zmuszony do zależności od nieużywanych metod?
- **DIP**: Czy zależę od abstrakcji czy konkretnej implementacji?

---

## Dodatkowe przykłady do samodzielnego ćwiczenia

1. **SRP**: Zrefaktoruj klasę `Book` z metodami print(), save(), format()
2. **OCP**: Dodaj nowy kształt `Pentagon` do systemu
3. **LSP**: Napraw hierarchię `Account` -> `SavingsAccount`
4. **ISP**: Rozbij interfejs `MultiFunctionPrinter`
5. **DIP**: Dodaj `LoggerService` z różnymi implementacjami

---

**Powodzenia w nauce zasad SOLID!** 🚀

