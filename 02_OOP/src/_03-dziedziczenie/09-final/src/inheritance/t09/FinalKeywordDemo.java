package inheritance.t09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

final class ImmutablePoint {
    private final int x;
    private final int y;

    ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }
}

// Value Object: bez tozsamosci, porownywany po wartosci, niemutowalny.
final class Money {
    private final BigDecimal amount;
    private final String currency;

    Money(BigDecimal amount, String currency) {
        if (amount == null || currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("amount i currency musza byc ustawione");
        }
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    BigDecimal amount() {
        return amount;
    }

    String currency() {
        return currency;
    }

    Money add(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    private void requireSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Waluty musza byc zgodne");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Money other)) {
            return false;
        }
        return amount.compareTo(other.amount) == 0 && currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        return 31 * amount.stripTrailingZeros().hashCode() + currency.hashCode();
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}

// Niemutowalny obiekt, ale semantycznie encja (tozsamosc przez tokenId).
final class SessionToken {
    private final String tokenId;
    private final String ownerLogin;

    SessionToken(String tokenId, String ownerLogin) {
        this.tokenId = tokenId;
        this.ownerLogin = ownerLogin;
    }

    String tokenId() {
        return tokenId;
    }

    String ownerLogin() {
        return ownerLogin;
    }
}

class BaseService {
    public final void audit() {
        System.out.println("audit");
    }
}

final class AppConstants {
    static final String APP_NAME = "OOP-LAB";
    static final int MAX_RETRIES = 3;
    static final BigDecimal VAT_RATE = new BigDecimal("0.23");

    private AppConstants() {
    }
}

record ReadOnlyConfig(String environment, int timeoutSeconds) {
    ReadOnlyConfig {
        if (environment == null || environment.isBlank()) {
            throw new IllegalArgumentException("environment nie moze byc puste");
        }
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("timeout musi byc dodatni");
        }
    }
}

public class FinalKeywordDemo {
    private static void constantsDemo() {
        System.out.println("\n=== Stale (constant) ===");
        System.out.println("AppConstants.APP_NAME = " + AppConstants.APP_NAME);
        System.out.println("AppConstants.MAX_RETRIES = " + AppConstants.MAX_RETRIES);
        System.out.println("AppConstants.VAT_RATE = " + AppConstants.VAT_RATE);
    }

    private static void finalReferenceDemo() {
        System.out.println("\n=== final referencja != read-only obiekt ===");
        final List<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("oop");
        System.out.println("Po dodaniu: " + tags);
        tags.add("solid");
        System.out.println("Po kolejnej modyfikacji: " + tags);
        System.out.println("Referencja jest stala, ale obiekt dalej mutowalny.");
    }

    private static void readOnlyCollectionsDemo() {
        System.out.println("\n=== Read-only: widok vs snapshot ===");
        List<String> source = new ArrayList<>(List.of("A", "B"));
        List<String> unmodifiableView = Collections.unmodifiableList(source);
        List<String> snapshot = List.copyOf(source);

        source.add("C");
        System.out.println("source = " + source);
        System.out.println("unmodifiableView (widzi zmiane) = " + unmodifiableView);
        System.out.println("snapshot (bez zmiany) = " + snapshot);

        try {
            unmodifiableView.add("X");
        } catch (UnsupportedOperationException ex) {
            System.out.println("Modyfikacja read-only widoku: " + ex.getClass().getSimpleName());
        }
    }

    private static void recordReadOnlyDemo() {
        System.out.println("\n=== Read-only obiekt przez record ===");
        ReadOnlyConfig config = new ReadOnlyConfig("prod", 30);
        System.out.println("config = " + config);
        System.out.println("Record daje niemutowalne pola i semantyke wartosci.");
    }

    public static void main(String[] args) {
        ImmutablePoint p = new ImmutablePoint(3, 4);
        System.out.println("(" + p.x() + "," + p.y() + ")");
        new BaseService().audit();

        Money netPrice = new Money(new BigDecimal("99.99"), "PLN");
        Money vat = new Money(new BigDecimal("23.00"), "PLN");
        Money grossPrice = netPrice.add(vat);

        System.out.println("netPrice = " + netPrice);
        System.out.println("vat = " + vat);
        System.out.println("grossPrice = " + grossPrice);
        System.out.println("Czy 99.99 PLN == 99.990 PLN? "
                + netPrice.equals(new Money(new BigDecimal("99.990"), "PLN")));

        SessionToken t1 = new SessionToken("tok-1", "ala");
        SessionToken t2 = new SessionToken("tok-1", "ala");
        System.out.println("SessionToken t1 == t2 po equals? " + t1.equals(t2));
        System.out.println("To nadal niemutowalny obiekt, ale nie Value Object.");

        constantsDemo();
        finalReferenceDemo();
        readOnlyCollectionsDemo();
        recordReadOnlyDemo();
    }
}

