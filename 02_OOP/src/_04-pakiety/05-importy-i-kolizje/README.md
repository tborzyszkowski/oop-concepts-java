# 4.5 Importowanie pakietow i kolizje nazw

## Od prostego importu do konfliktu nazw

W najprostszym przypadku importujemy klasy standardowe:

```java
import java.util.ArrayList;
import java.util.List;
```

Problem pojawia sie, gdy dwie klasy maja te sama nazwe, np. `Order` w dwoch pakietach.
Wtedy trzeba uzyc pelnych nazw kwalifikowanych.

## Diagram

![Importy i kolizje](diagrams/imports_collisions.png)

## Kod referencyjny

- prosty przypadek: `src/imports/simple/SimpleImportDemo.java`
- kolizja nazw: `src/imports/complex/reporting/ImportCollisionDemo.java`
- klasy kolidujace: `src/imports/complex/billing/Order.java`, `src/imports/complex/shipping/Order.java`

Fragment kluczowy:

```java
imports.complex.billing.Order billingOrder =
    new imports.complex.billing.Order("B-01", 199.99);

imports.complex.shipping.Order shippingOrder =
    new imports.complex.shipping.Order("S-01", "Warszawa");
```

## Typowe problemy ze "sklejaniem" przestrzeni nazw

1. Uzycie wildcard `*` przy duzych projektach utrudnia czytelnosc.
2. Niejawne konflikty przy refaktoryzacji.
3. Rozmycie granic odpowiedzialnosci pakietow.

## Uruchomienie

```powershell
.\run-examples.ps1
```

## Literatura

- Oracle API docs (`java.util`): <https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/package-summary.html>
- Google Java Style - Imports: <https://google.github.io/styleguide/javaguide.html#s3.3-import-statements>

