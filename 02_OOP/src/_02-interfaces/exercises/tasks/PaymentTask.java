package exercises.tasks;

/**
 * ZADANIE 1 (⭐) — Interfejs Payable
 *
 * Zaimplementuj interfejs Payable dla trzech sposobów płatności.
 * Następnie przetwórz listę płatności polimorficznie.
 *
 * Oczekiwany output:
 *   [KARTA] 4111-xxxx-xxxx-1111: 150,00 PLN
 *   [PAYPAL] jan@example.com: 299,99 PLN
 *   [PRZELEW] PL61 1090 1014: 1200,00 PLN
 *   Lacznie: 1649,99 PLN
 */
public class PaymentTask {

    // TODO: Zdefiniuj interfejs Payable z metodami:
    //   void pay(double amount);
    //   String getPaymentInfo();
    //   default String receipt(double amount) { ... }

    // TODO: Zaimplementuj CreditCard implements Payable
    //   - pole: String maskedNumber (np. "4111-xxxx-xxxx-1111")
    //   - pay() wypisuje "[KARTA] maskedNumber: amount PLN"

    // TODO: Zaimplementuj PayPalPayment implements Payable
    //   - pole: String email
    //   - pay() wypisuje "[PAYPAL] email: amount PLN"

    // TODO: Zaimplementuj BankTransfer implements Payable
    //   - pole: String iban (pierwsze 14 znaków)
    //   - pay() wypisuje "[PRZELEW] iban: amount PLN"

    public static void main(String[] args) {
        // TODO: Utwórz listę Payable, dodaj po jednym obiekcie każdego typu
        // TODO: Przetwórz listę pętlą for-each, wywołaj pay() na każdym
        // TODO: Policz i wypisz łączną kwotę
        System.out.println("TODO: zaimplementuj zadanie");
    }
}

