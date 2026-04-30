package _06_wyjatki._09_projekt.code;

import java.util.*;
import java.util.stream.Collectors;

/**
 * BankApp — większy przykład intensywnego wykorzystania wyjątków.
 *
 * Symuluje prostą aplikację bankową z walidacją, operacjami i historią.
 *
 * Uruchomienie:
 *   javac -d out _06_wyjatki/_09_projekt/code/BankApp.java
 *   java  -cp out _06_wyjatki._09_projekt.code.BankApp
 */

// =======================================================
// Hierarchia wyjątków bankowych
// =======================================================

/** Bazowy wyjątek dla całej aplikacji bankowej. */
class BankException extends Exception {
    private final String code;

    public BankException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BankException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() { return code; }

    @Override
    public String toString() {
        return "[" + code + "] " + getMessage();
    }
}

/** Niewystarczające środki na koncie. */
class InsufficientFundsException extends BankException {
    private final double balance;
    private final double requested;

    public InsufficientFundsException(double balance, double requested) {
        super("INSUF_FUNDS",
              String.format("Saldo %.2f PLN; żądano %.2f PLN", balance, requested));
        this.balance = balance;
        this.requested = requested;
    }

    public double getBalance()   { return balance; }
    public double getRequested() { return requested; }
}

/** Konto o podanym numerze nie istnieje. */
class AccountNotFoundException extends BankException {
    private final String accountNumber;

    public AccountNotFoundException(String accountNumber) {
        super("ACCT_NOT_FOUND", "Konto nie znalezione: " + accountNumber);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() { return accountNumber; }
}

/** Nieprawidłowe dane wejściowe (numer konta, kwota, itd.). */
class InvalidInputException extends BankException {
    private final List<String> violations;

    public InvalidInputException(List<String> violations) {
        super("INVALID_INPUT", "Nieprawidłowe dane: " + violations);
        this.violations = List.copyOf(violations);
    }

    public List<String> getViolations() { return violations; }
}

/** Konto zablokowane. */
class AccountFrozenException extends BankException {
    public AccountFrozenException(String accountNumber) {
        super("ACCT_FROZEN", "Konto zablokowane: " + accountNumber);
    }
}

// =======================================================
// Model domenowy
// =======================================================

enum TransactionType { DEPOSIT, WITHDRAWAL, TRANSFER }

record Transaction(TransactionType type, double amount, String description) {
    @Override
    public String toString() {
        return String.format("%-12s %+8.2f PLN  %s", type,
               type == TransactionType.WITHDRAWAL ? -amount : amount, description);
    }
}

class Account {
    private final String number;
    private final String owner;
    private double balance;
    private boolean frozen;
    private final List<Transaction> history = new ArrayList<>();

    public Account(String number, String owner, double initialBalance)
            throws InvalidInputException {
        List<String> errors = new ArrayList<>();
        if (number == null || !number.matches("[A-Z]{2}\\d{10}"))
            errors.add("Numer konta musi mieć format PL+10cyfr");
        if (owner == null || owner.isBlank())
            errors.add("Właściciel jest wymagany");
        if (initialBalance < 0)
            errors.add("Saldo początkowe nie może być ujemne");
        if (!errors.isEmpty()) throw new InvalidInputException(errors);

        this.number  = number;
        this.owner   = owner;
        this.balance = initialBalance;
    }

    public void deposit(double amount) throws BankException {
        validateAmount(amount, "Wpłata");
        checkNotFrozen();
        balance += amount;
        history.add(new Transaction(TransactionType.DEPOSIT, amount, "Wpłata"));
    }

    public void withdraw(double amount) throws BankException {
        validateAmount(amount, "Wypłata");
        checkNotFrozen();
        if (amount > balance)
            throw new InsufficientFundsException(balance, amount);
        balance -= amount;
        history.add(new Transaction(TransactionType.WITHDRAWAL, amount, "Wypłata"));
    }

    public void transferTo(Account target, double amount, String desc) throws BankException {
        validateAmount(amount, "Przelew");
        checkNotFrozen();
        if (amount > balance)
            throw new InsufficientFundsException(balance, amount);
        balance -= amount;
        history.add(new Transaction(TransactionType.TRANSFER, amount, "Przelew do " + target.number + ": " + desc));
        target.receiveTransfer(amount, "Przelew od " + this.number + ": " + desc);
    }

    void receiveTransfer(double amount, String desc) {
        balance += amount;
        history.add(new Transaction(TransactionType.DEPOSIT, amount, desc));
    }

    public void freeze()   { this.frozen = true; }
    public void unfreeze() { this.frozen = false; }

    public String getNumber()  { return number; }
    public String getOwner()   { return owner; }
    public double getBalance() { return balance; }
    public boolean isFrozen()  { return frozen; }

    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(history);
    }

    private void validateAmount(double amount, String op) throws InvalidInputException {
        if (amount <= 0) {
            throw new InvalidInputException(List.of(op + ": kwota musi być dodatnia, podano: " + amount));
        }
    }

    private void checkNotFrozen() throws AccountFrozenException {
        if (frozen) throw new AccountFrozenException(number);
    }

    @Override
    public String toString() {
        return String.format("Account{%s, %s, balance=%.2f, frozen=%b}",
                number, owner, balance, frozen);
    }
}

// =======================================================
// Repozytorium kont
// =======================================================

class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public void add(Account account) {
        accounts.put(account.getNumber(), account);
    }

    public Account find(String number) throws AccountNotFoundException {
        Account acct = accounts.get(number);
        if (acct == null) throw new AccountNotFoundException(number);
        return acct;
    }

    public List<Account> findAll() {
        return List.copyOf(accounts.values());
    }
}

// =======================================================
// Serwis bankowy — orkiestracja i obsługa błędów
// =======================================================

class BankService {
    private final AccountRepository repo;
    private final List<String> auditLog = new ArrayList<>();

    public BankService(AccountRepository repo) {
        this.repo = repo;
    }

    public void deposit(String accountNo, double amount) {
        try {
            Account acct = repo.find(accountNo);
            acct.deposit(amount);
            audit("DEPOSIT", accountNo, amount, "OK");
        } catch (AccountNotFoundException e) {
            audit("DEPOSIT", accountNo, amount, "FAILED: " + e.getCode());
            System.out.println("  ✗ " + e);
        } catch (InvalidInputException e) {
            audit("DEPOSIT", accountNo, amount, "FAILED: " + e.getCode());
            System.out.println("  ✗ " + e);
        } catch (BankException e) {
            audit("DEPOSIT", accountNo, amount, "FAILED: " + e.getCode());
            System.out.println("  ✗ Nieoczekiwany błąd: " + e);
        }
    }

    public void withdraw(String accountNo, double amount) {
        try {
            Account acct = repo.find(accountNo);
            acct.withdraw(amount);
            audit("WITHDRAW", accountNo, amount, "OK");
        } catch (InsufficientFundsException e) {
            audit("WITHDRAW", accountNo, amount, "FAILED: brak środków");
            System.out.printf("  ✗ %s (saldo: %.2f, żądano: %.2f)%n",
                    e.getCode(), e.getBalance(), e.getRequested());
        } catch (AccountFrozenException | AccountNotFoundException e) {
            audit("WITHDRAW", accountNo, amount, "FAILED: " + e.getCode());
            System.out.println("  ✗ " + e);
        } catch (BankException e) {
            audit("WITHDRAW", accountNo, amount, "FAILED: " + e.getCode());
            System.out.println("  ✗ Nieoczekiwany błąd: " + e);
        }
    }

    public void transfer(String fromNo, String toNo, double amount, String desc) {
        try {
            Account from = repo.find(fromNo);
            Account to   = repo.find(toNo);
            from.transferTo(to, amount, desc);
            audit("TRANSFER", fromNo + "→" + toNo, amount, "OK");
        } catch (BankException e) {
            audit("TRANSFER", fromNo + "→" + toNo, amount, "FAILED: " + e.getCode());
            System.out.println("  ✗ " + e);
        }
    }

    public void printHistory(String accountNo) {
        try {
            Account acct = repo.find(accountNo);
            System.out.println("  Historia konta " + accountNo + " (" + acct.getOwner() + "):");
            acct.getHistory().forEach(t -> System.out.println("    " + t));
            System.out.printf("  Saldo końcowe: %.2f PLN%n", acct.getBalance());
        } catch (AccountNotFoundException e) {
            System.out.println("  ✗ " + e);
        }
    }

    public void printAuditLog() {
        System.out.println("\n=== Dziennik audytowy ===");
        auditLog.forEach(entry -> System.out.println("  " + entry));
    }

    private void audit(String op, String account, double amount, String result) {
        auditLog.add(String.format("%-10s %-25s %8.2f PLN  %s", op, account, amount, result));
    }
}

// =======================================================
// Główna klasa demo
// =======================================================

public class BankApp {
    public static void main(String[] args) throws Exception {
        AccountRepository repo = new AccountRepository();
        BankService bank       = new BankService(repo);

        System.out.println("=== Tworzenie kont ===");

        // Poprawne konta
        Account jan   = new Account("PL0000000001", "Jan Kowalski",  1000.00);
        Account anna  = new Account("PL0000000002", "Anna Nowak",     500.00);
        repo.add(jan);
        repo.add(anna);
        System.out.println("  Konta utworzone: " + jan.getNumber() + ", " + anna.getNumber());

        // Próba stworzenia konta z nieprawidłowymi danymi
        System.out.println("\n=== Walidacja przy tworzeniu ===");
        try {
            Account bad = new Account("INVALID", "", -100);
        } catch (InvalidInputException e) {
            System.out.println("  InvalidInputException: " + e.getMessage());
            e.getViolations().forEach(v -> System.out.println("    - " + v));
        }

        // Operacje poprawne
        System.out.println("\n=== Operacje bankowe ===");
        System.out.println("  Wpłata do Jana:");
        bank.deposit("PL0000000001", 500.00);

        System.out.println("  Przelew Jan → Anna:");
        bank.transfer("PL0000000001", "PL0000000002", 300.00, "Zwrot pożyczki");

        // Operacje błędne
        System.out.println("  Wypłata ponad saldo Jana (1200):");
        bank.withdraw("PL0000000001", 1200.00);

        System.out.println("  Wpłata do nieistniejącego konta:");
        bank.deposit("PL9999999999", 100.00);

        System.out.println("  Operacja na zamrożonym koncie:");
        anna.freeze();
        bank.withdraw("PL0000000002", 50.00);
        anna.unfreeze();

        System.out.println("  Kwota ujemna:");
        bank.deposit("PL0000000001", -50.00);

        // Historie kont
        System.out.println("\n=== Historia kont ===");
        bank.printHistory("PL0000000001");
        System.out.println();
        bank.printHistory("PL0000000002");
        bank.printHistory("PL9999999999");

        // Dziennik audytowy
        bank.printAuditLog();
    }
}

