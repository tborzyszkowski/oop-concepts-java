# Modul 3.1: Pojecie dziedziczenia i implementacja w Javie

## Wprowadzenie

Dziedziczenie pozwala budowac nowy typ na bazie istniejacego typu. W Javie realizujemy to przez `extends`, a glowna korzyscia jest wspolny kontrakt `is-a` oraz mozliwosc ponownego uzycia kodu.

### Czego nauczysz sie w tym module?
- odrozniania relacji `is-a` od relacji `has-a`,
- projektowania prostych hierarchii klas,
- swiadomego uzycia `override` w klasie potomnej.

---

## Diagram koncepcji

![Diagram dziedziczenia](diagrams/inheritance_intro.png)

Diagram PlantUML: [`diagrams/inheritance_intro.puml`](diagrams/inheritance_intro.puml)

---

## Kod i omowienie

Plik z przykladem:
- [`src/inheritance/t01/InheritanceIntroDemo.java`](src/inheritance/t01/InheritanceIntroDemo.java)

Fragment:

```java
Animal animal = new Dog();
System.out.println(animal.speak());
```

Wynik pokazuje, ze referencja typu bazowego (`Animal`) moze wskazywac na obiekt klasy potomnej (`Dog`).

---

## Najczestsze bledy

1. Projektowanie dziedziczenia tylko po to, by wspoldzielic kod (zamiast przemyslanej relacji `is-a`).
2. Brak `@Override` przy nadpisywaniu metod.
3. Nadmiernie glebokie hierarchie klas, utrudniajace utrzymanie.

---

## Uruchomienie

Z katalogu `02_OOP/src/_03-dziedziczenie`:

```powershell
.\run-all-examples.ps1
```

---

## Materialy dodatkowe

- Oracle Tutorials: <https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html>
- Effective Java, rozdzial o projektowaniu klas i interfejsow
