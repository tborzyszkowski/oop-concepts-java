# 06 - Polimorfizm i wzorce

Polimorfizm umozliwia programowanie do abstrakcji i podmiane zachowania bez zmian w kodzie klienta.

![Diagram wzorca Strategy](diagrams/polymorphism_patterns.png)

## Kod

- `src/inheritance/t06/PolymorphismPatternsDemo.java`

```java
Checkout checkout = new Checkout(new BlikPayment());
System.out.println(checkout.finalizeOrder(199.99));
```

