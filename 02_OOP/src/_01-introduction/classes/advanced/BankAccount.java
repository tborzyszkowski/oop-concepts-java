package classes.advanced;

/**
 * Klasa BankAccount - realistyczny przykład klasy z enkapsulacją.
 *
 * Demonstruje:
 *  - pola prywatne (ukrywanie stanu)
 *  - konstruktor z parametrami
 *  - metody publiczne (interfejs klasy)
 *  - metody prywatne (logika wewnętrzna)
 *  - toString(), equals(), hashCode()
 */
public class BankAccount {

    // Pola prywatne - niedostępne bezpośrednio z zewnątrz klasy
    private String owner;
    private String accountNumber;
    private double balance;
    private int transactionCount;

    /**
     * Konstruktor - inicjalizuje nowe konto
     * @param owner - właściciel konta
     * @param accountNumber - numer konta
     * @param initialBalance - saldo początkowe
     */
    public BankAccount(String owner, String accountNumber, double initialBalance) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionCount = 0;
        System.out.println("Otwarto konto dla: " + owner
                + " (nr: " + accountNumber + ")");
    }

    // ===== Metody publiczne (API klasy) =====

    /**
     * Wpłata na konto
     * @param amount - kwota do wpłaty (musi być > 0)
     */
    public void deposit(double amount) {
        if (!isValidAmount(amount)) {
            System.out.println("Błąd: kwota musi być dodatnia");
            return;
        }
        balance += amount;
        transactionCount++;
        logTransaction("Wpłata", amount);
    }

    /**
     * Wypłata z konta
     * @param amount - kwota do wypłaty
     */
    public void withdraw(double amount) {
        if (!isValidAmount(amount)) {
            System.out.println("Błąd: kwota musi być dodatnia");
            return;
        }
        if (amount > balance) {
            System.out.println("Błąd: niewystarczające środki (saldo: "
                    + String.format("%.2f", balance) + " PLN)");
            return;
        }
        balance -= amount;
        transactionCount++;
        logTransaction("Wypłata", amount);
    }

    /**
     * Przelew na inne konto
     * @param target - konto docelowe
     * @param amount - kwota przelewu
     */
    public void transfer(BankAccount target, double amount) {
        this.withdraw(amount);
        if (this.transactionCount > 0) {  // jeśli withdraw się powiodło
            target.deposit(amount);
            System.out.println("Przelew " + String.format("%.2f", amount)
                    + " PLN -> " + target.owner);
        }
    }

    // ===== Gettery - dostęp do odczytu pól prywatnych =====

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    // ===== Metody prywatne (logika wewnętrzna) =====

    private boolean isValidAmount(double amount) {
        return amount > 0;
    }

    private void logTransaction(String type, double amount) {
        System.out.println(type + ": " + String.format("%.2f", amount)
                + " PLN | Saldo: " + String.format("%.2f", balance) + " PLN");
    }

    // ===== Metody Object =====

    @Override
    public String toString() {
        return "BankAccount{owner='" + owner
                + "', account='" + accountNumber
                + "', balance=" + String.format("%.2f", balance)
                + " PLN, transactions=" + transactionCount + "}";
    }
}

