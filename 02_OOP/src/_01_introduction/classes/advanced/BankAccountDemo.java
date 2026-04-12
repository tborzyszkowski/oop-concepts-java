package classes.advanced;

/**
 * Demonstracja klasy BankAccount - realistyczny scenariusz.
 */
public class BankAccountDemo {

    public static void main(String[] args) {

        System.out.println("=== Demonstracja klasy BankAccount ===");
        System.out.println();

        // Tworzenie obiektów za pomocą konstruktora
        BankAccount aliceAccount = new BankAccount("Alice Kowalska", "PL61109010140000071219812874", 1000.00);
        BankAccount bobAccount   = new BankAccount("Bob Nowak",     "PL27114020040000300201355387", 500.00);

        System.out.println();
        System.out.println("--- Stan kont po otwarciu ---");
        System.out.println(aliceAccount);
        System.out.println(bobAccount);

        System.out.println();
        System.out.println("--- Operacje na koncie Alice ---");
        aliceAccount.deposit(500.00);
        aliceAccount.withdraw(200.00);
        aliceAccount.withdraw(2000.00); // błąd - za mało środków

        System.out.println();
        System.out.println("--- Przelew Alice -> Bob ---");
        aliceAccount.transfer(bobAccount, 300.00);

        System.out.println();
        System.out.println("--- Stan końcowy ---");
        System.out.println(aliceAccount);
        System.out.println(bobAccount);

        System.out.println();
        System.out.println("--- Dostęp przez gettery ---");
        System.out.println("Alice balance: " + String.format("%.2f", aliceAccount.getBalance()) + " PLN");
        System.out.println("Bob balance:   " + String.format("%.2f", bobAccount.getBalance()) + " PLN");

        // Pola prywatne są NIEDOSTĘPNE bezpośrednio:
        // aliceAccount.balance = 9999;  // błąd kompilacji!
    }
}

