package exercises.tasks;

/**
 * Zadanie 2 (⭐⭐ średnie): Kolizja nazw Order — system e-commerce
 *
 * INSTRUKCJA:
 * 1) Utwórz shop.billing.Order(String id, double amount) — rekord
 * 2) Utwórz shop.shipping.Order(String id, String destination) — rekord
 * 3) W shop.reporting.OrderReport utwórz metodę:
 *
 *    public void printReport(
 *        shop.billing.Order billing,
 *        shop.shipping.Order shipping) {
 *
 *        System.out.println("=== Raport zamówień ===");
 *        System.out.printf("Płatność  %s: %.2f zł%n", billing.id(), billing.amount());
 *        System.out.printf("Wysyłka   %s: %s%n", shipping.id(), shipping.destination());
 *    }
 *
 *    Zauważ: nie możesz jednocześnie zaimportować obu klas Order,
 *    bo import imports.complex.billing.Order i import imports.complex.shipping.Order
 *    powodują kolizję — kompilator nie wie, którego Order użyć.
 *    Używaj FQN (pełnej nazwy kwalifikowanej).
 *
 * 4) Utwórz shop.app.ShopApp z main, który tworzy oba Order i wywołuje printReport.
 *
 * OCZEKIWANY WYNIK:
 *   === Raport zamówień ===
 *   Płatność  B-01: 299.99 zł
 *   Wysyłka   S-01: Kraków
 *
 * Patrz: moduł 4.5 (kolizje, FQN)
 */
public final class PackageTask2 {
    private PackageTask2() {}
}

