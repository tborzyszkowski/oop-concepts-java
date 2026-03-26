package inheritance.t09;

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

public class FinalKeywordDemo {
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
    }
}

