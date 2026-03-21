# 01 - Dziedziczenie: geneza i implementacja

Dziedziczenie pozwala budowac nowy typ na bazie istniejącego typu. W Java realizujemy to przez `extends`.

![Diagram dziedziczenia](diagrams/inheritance_intro.png)

## Kod

- `src/inheritance/t01/InheritanceIntroDemo.java`

```java
Animal d = new Dog();
System.out.println(d.speak()); // Hau
```

## Co pokazuje ten temat

- relacje `is-a`
- ponowne uzycie zachowan przez nadpisanie metod
- podstawe do polimorfizmu

## Zrodla

- https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html

