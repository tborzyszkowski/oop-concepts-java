package _04_pakiety._06_zadania.code.tasks;

/**
 * Zadanie 3 (⭐⭐ średnie): Kontrola dostępu — konto bankowe
 *
 * INSTRUKCJA:
 * Zaprojektuj mini-system bankowy z czterema poziomami dostępu.
 *
 * 1) bank.core.BankAccount:
 *    - private double balance       — saldo (niedostępne bezpośrednio z zewnątrz)
 *    - String accountType           — package-private (tylko bank.core widzi)
 *    - protected void applyInterest(double rate)  — dla podklas
 *    - public double getBalance()   — publiczny getter
 *    - public void deposit(double amount) — wpłata; rzuca IllegalArgumentException gdy amount <= 0
 *
 * 2) bank.core.AuditService:
 *    public String audit(BankAccount acc) {
 *        return "Typ konta: " + acc.accountType + ", saldo: " + acc.getBalance();
 *    }
 *    (AuditService jest w tym samym pakiecie — może odczytać accountType)
 *
 * 3) bank.premium.PremiumAccount extends BankAccount:
 *    public PremiumAccount() { accountType = "PREMIUM"; }  // ← test: czy protected wystarczy?
 *    @Override
 *    protected void applyInterest(double rate) {
 *        // premium: wyższe oprocentowanie (+0.5%)
 *        super.applyInterest(rate + 0.005);
 *    }
 *    public void activatePremiumInterest() { applyInterest(0.05); }
 *
 * 4) bank.app.BankApp z main:
 *    BankAccount acc = new PremiumAccount();
 *    acc.deposit(1000.0);
 *    acc.activatePremiumInterest();  // ← czy to skompiluje się z bank.app?
 *    System.out.println("Saldo: " + acc.getBalance());
 *    // acc.accountType  → błąd: package-private!
 *
 * OCZEKIWANY WYNIK:
 *   Saldo: 1000.0
 *   Saldo po odsetkach: 1055.0
 *
 * Patrz: moduł 4.4 (tabela modyfikatorów dostępu)
 */
public final class PackageTask3 {
    private PackageTask3() {}
}

