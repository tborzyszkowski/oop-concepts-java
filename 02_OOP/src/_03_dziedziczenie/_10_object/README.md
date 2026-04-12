# Moduł 3.10: Klasa `Object` oraz kontrakt `equals` i `hashCode`

## Wprowadzenie

Każda klasa w Javie dziedziczy po `Object`, dlatego każda instancja ma metody `equals`, `hashCode`, `toString` i `getClass`.
W praktyce biznesowej najwięcej błędów pojawia się na styku `equals` i `hashCode`, szczególnie przy pracy z `HashSet`, `HashMap` i cache.

### Czego nauczysz się w tym module?
- kiedy i jak nadpisywać `equals` oraz `hashCode`,
- dlaczego równość logiczna obiektu musi być spójna z haszowaniem,
- jak rozpoznawać i eliminować typowe antywzorce,
- dlaczego mutowalny klucz psuje kolekcje haszujące.

---

## Diagram koncepcji

![Diagram Object](diagrams/object_hierarchy.png)

Diagram PlantUML: [`diagrams/object_hierarchy.puml`](diagrams/object_hierarchy.puml)

---

## Kontrakt `equals` i `hashCode` - najważniejsze reguły

### Reguły `equals`
Metoda `equals` powinna spełniać:
1. refleksyjność: `x.equals(x) == true`,
2. symetryczność: `x.equals(y)` i `y.equals(x)` dają ten sam wynik,
3. przechodniość: jeśli `x.equals(y)` i `y.equals(z)`, to `x.equals(z)`,
4. spójność: wielokrotne wywołania dają ten sam wynik, jeśli stan się nie zmienił,
5. `x.equals(null) == false`.

### Reguły relacji `equals` <-> `hashCode`
1. Jeżeli `x.equals(y) == true`, to **musi** być `x.hashCode() == y.hashCode()`.
2. Jeżeli `x.hashCode() == y.hashCode()`, to obiekty **nie muszą** być równe (`equals` może zwrócić `false`).
3. W jednej aplikacji wynik `hashCode` dla niezmiennego obiektu powinien być stabilny.

---

## Kod i scenariusze

Główny plik z przykładami:
- [`code/ObjectClassDemo.java`](code/ObjectClassDemo.java)

W pliku znajdują się cztery scenariusze dydaktyczne:

1. `validContractDemo()` - poprawna implementacja (`Student`),
2. `brokenContractDemo()` - błąd: `equals` bez `hashCode` (`BrokenStudent`),
3. `collisionDemo()` - kolizje `hashCode` i poprawne działanie `HashMap`,
4. `mutableKeyDemo()` - mutowalny klucz i utrata możliwości wyszukania wpisu.

### 1) Poprawna implementacja

```java
@Override
public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof Student student)) return false;
	return Objects.equals(id, student.id);
}

@Override
public int hashCode() {
	return Objects.hash(id);
}
```

Wniosek: dwa obiekty z tym samym `id` są logicznie równe i trafiają do tego samego kubełka `HashSet`/`HashMap`.

### 2) Złamany kontrakt: `equals` bez `hashCode`

`BrokenStudent` porównuje po `id`, ale dziedziczy domyślny `hashCode` z `Object` (powiązany z tożsamością instancji).
Efekt: dwa obiekty są "równe" według `equals`, ale mogą mieć różne hashe, więc `HashSet` przechowuje duble.

### 3) Kolizje nie łamią poprawności

W `CollisionKey` celowo użyto słabego hasha (`value.length()`).
Różne klucze mogą mieć ten sam hash, ale `HashMap` i tak rozróżnia je przez `equals`.
Efekt: spada wydajność, ale poprawność logiczna pozostaje.

### 4) Mutowalny klucz to antywzorzec

`MutableKey` zmienia `id` po wstawieniu do mapy.
To zmienia `hashCode`, więc obiekt "znika" z oczekiwanego kubełka i `map.get(key)` może zwrócić `null`.

---

## Kiedy nadpisywać `equals` i `hashCode`?

- gdy modelujesz **tożsamość logiczną** (np. `isbn`, `pesel`, `id` domenowe),
- gdy obiekt ma być kluczem w mapie lub elementem zbioru,
- gdy tworzysz Value Object (obiekty porównywane po stanie, nie po referencji).

Nie nadpisuj "na siłę" - jeśli porównanie referencyjne (`==`) jest zamierzone, domyślne `Object.equals` bywa poprawne.

---

## Najczęstsze błędy

1. Nadpisanie `equals` bez `hashCode`.
2. Używanie pól mutowalnych do wyliczania `hashCode`.
3. Porównywanie innego zestawu pól w `equals` i innego w `hashCode`.
4. Używanie `instanceof`/`getClass` bez przemyślenia hierarchii i symetrii.
5. Brak testów kontraktu dla klas używanych w kolekcjach haszujących.

---

## Uruchomienie

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03_dziedziczenie"
.\run-all-examples.ps1
```

Aby uruchomić tylko ten moduł ręcznie (kompilacja + uruchomienie jednego `main`):

```powershell
Set-Location "C:\home\gitHub\oop-concepts-java\02_OOP\src\_03_dziedziczenie"
New-Item -ItemType Directory -Force -Path out | Out-Null
javac --release 21 -d out .\_10_object\code\ObjectClassDemo.java
java -cp out _03_dziedziczenie._10_object.code.ObjectClassDemo
```

