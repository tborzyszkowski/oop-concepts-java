package inheritance.t11;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Kontrakt dla elementu, ktory mozna zafakturowac.
 *
 * @since 1.0
 */
interface Billable {
    /**
     * @return wartosc netto dodatnia albo rowna zero
     */
    BigDecimal netValue();

    /**
     * @return opis pozycji widoczny na fakturze
     */
    String description();
}

/**
 * Prosta implementacja pozycji faktury.
 *
 * @param description opis pozycji
 * @param netValue wartosc netto
 * @since 1.0
 */
record ServiceItem(String description, BigDecimal netValue) implements Billable {
    ServiceItem {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description must not be blank");
        }
        if (netValue == null || netValue.signum() < 0) {
            throw new IllegalArgumentException("netValue must be >= 0");
        }
        netValue = netValue.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public BigDecimal netValue() {
        return netValue;
    }
}

/**
 * Strategia liczenia kwoty brutto z netto.
 *
 * @see VatTaxPolicy
 * @since 1.0
 */
interface TaxPolicy {
    /**
     * @param net kwota netto
     * @return kwota brutto
     */
    BigDecimal grossFromNet(BigDecimal net);
}

/**
 * Bazowa implementacja polityki podatkowej.
 *
 * @since 1.0
 */
abstract class AbstractTaxPolicy implements TaxPolicy {
    /**
     * @param net kwota netto
     * @return kwota brutto po zastosowaniu stawki
     * @throws IllegalArgumentException gdy net jest null lub mniejsze od zera
     * Implementacja domyslna zaokragla wynik do 2 miejsc po przecinku.
     */
    @Override
    public BigDecimal grossFromNet(BigDecimal net) {
        if (net == null || net.signum() < 0) {
            throw new IllegalArgumentException("net must be >= 0");
        }
        return net
                .multiply(BigDecimal.ONE.add(rate()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @return stawka podatku, np. 0.23 dla 23%
     */
    protected abstract BigDecimal rate();
}

/**
 * Standardowa polityka VAT.
 *
 * @since 1.0
 */
final class VatTaxPolicy extends AbstractTaxPolicy {
    private final BigDecimal vatRate;

    /**
     * @param vatRate stawka VAT (np. 0.23)
     * Najczestszy wybor w prostych przykladach edukacyjnych.
     */
    VatTaxPolicy(BigDecimal vatRate) {
        if (vatRate == null || vatRate.signum() < 0) {
            throw new IllegalArgumentException("vatRate must be >= 0");
        }
        this.vatRate = vatRate;
    }

    /** {@inheritDoc} */
    @Override
    protected BigDecimal rate() {
        return vatRate;
    }
}

/**
 * Narzedzie do sumowania pozycji faktury.
 *
 * @author OOP Team
 * @version 2.0
 * @since 1.0
 */
class InvoiceCalculator {
    /**
     * Liczy laczna kwote netto.
     *
     * @param items lista pozycji
     * @return suma netto
     * @throws IllegalArgumentException gdy lista jest null lub pusta
     */
    public BigDecimal netTotal(List<? extends Billable> items) {
        requireItems(items);
        return items.stream()
                .map(Billable::netValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Liczy kwote brutto na podstawie polityki podatkowej.
     *
     * @param items lista pozycji
     * @param policy strategia podatkowa
     * @return suma brutto
     * @throws IllegalArgumentException gdy items lub policy sa niepoprawne
     * @see #netTotal(List)
     * @see TaxPolicy
     */
    public BigDecimal grossTotal(List<? extends Billable> items, TaxPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("policy must not be null");
        }
        return policy.grossFromNet(netTotal(items));
    }

    /**
     * Przestarzala metoda liczaca brutto zawsze dla 23% VAT.
     *
     * @param items lista pozycji
     * @return suma brutto dla stalej stawki 23%
     * @deprecated Uzyj {@link #grossTotal(List, TaxPolicy)} i przekaz {@link VatTaxPolicy}.
     */
    @Deprecated(since = "2.0", forRemoval = false)
    public BigDecimal grossTotalWithFixedVat(List<? extends Billable> items) {
        return grossTotal(items, new VatTaxPolicy(new BigDecimal("0.23")));
    }

    /**
     * Buduje read-only liste opisow pozycji.
     *
     * @param items lista pozycji
     * @return niemutowalna lista opisow
     */
    public List<String> descriptions(List<? extends Billable> items) {
        requireItems(items);
        List<String> result = new ArrayList<>();
        for (Billable item : items) {
            result.add(item.description());
        }
        return List.copyOf(result);
    }

    private static void requireItems(List<? extends Billable> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items must not be null or empty");
        }
    }
}

/**
 * Punkt startowy demonstracji modulu Javadoc.
 *
 * @since 1.0
 */
public class JavadocDemo {
    /**
     * Uruchamia przyklad i wypisuje wartosci netto/brutto.
     *
     * @param args argumenty CLI (nieuzywane)
     */
    public static void main(String[] args) {
        List<ServiceItem> items = List.of(
                new ServiceItem("Konsultacje", new BigDecimal("200.00")),
                new ServiceItem("Przygotowanie raportu", new BigDecimal("150.50"))
        );

        InvoiceCalculator calculator = new InvoiceCalculator();
        TaxPolicy vat23 = new VatTaxPolicy(new BigDecimal("0.23"));

        System.out.println("Netto:  " + calculator.netTotal(items));
        System.out.println("Brutto: " + calculator.grossTotal(items, vat23));
        System.out.println("Opisy:  " + calculator.descriptions(items));
    }
}
