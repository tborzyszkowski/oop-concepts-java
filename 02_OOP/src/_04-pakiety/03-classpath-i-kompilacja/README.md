# 4.3 CLASSPATH, kompilacja i uruchamianie

## Co to jest CLASSPATH?

`CLASSPATH` to lista lokalizacji, w ktorych JVM i kompilator szukaja klas.
Najczesciej wskazujemy go jawnie parametrem `-cp` (lub `--class-path`).

## Diagram

![Classpath flow](diagrams/classpath_flow.png)

## Kod referencyjny

- `src/classpathdemo/api/GreetingService.java`
- `src/classpathdemo/impl/PolishGreetingService.java`
- `src/classpathdemo/app/ClasspathDemo.java`

## Przykladowa kompilacja reczna

```powershell
# kompilacja do katalogu out
javac --release 21 -d out src\classpathdemo\api\GreetingService.java src\classpathdemo\impl\PolishGreetingService.java src\classpathdemo\app\ClasspathDemo.java

# uruchomienie klasy glownej
java -cp out classpathdemo.app.ClasspathDemo
```

## Uwaga praktyczna

Dla projektow produkcyjnych preferuj Maven/Gradle, ale zrozumienie `javac` + `java -cp` jest kluczowe przy diagnozie problemow builda.

## Uruchomienie skryptu

```powershell
.\run-examples.ps1
```

## Literatura

- Java tools reference (`javac`): <https://docs.oracle.com/en/java/javase/21/docs/specs/man/javac.html>
- Java tools reference (`java`): <https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html>

