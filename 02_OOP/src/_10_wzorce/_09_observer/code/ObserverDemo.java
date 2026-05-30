package _10_wzorce._09_observer.code;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern — gielda papierow wartosciowych z wieloma obserwatorami.
 */
public class ObserverDemo {

    // ─── Zdarzenie ────────────────────────────────────────────────────────────
    record StockEvent(String symbol, double oldPrice, double newPrice, Instant time) {
        double changePercent() {
            return (newPrice - oldPrice) / oldPrice * 100;
        }
        boolean isGain() { return newPrice > oldPrice; }
    }

    // ─── Observer (Listener) ──────────────────────────────────────────────────
    @FunctionalInterface
    interface StockListener {
        void onPriceChange(StockEvent event);
    }

    // ─── Subject (Publisher) ──────────────────────────────────────────────────
    static class StockMarket {
        private final String name;
        private final java.util.Map<String, Double> prices = new java.util.LinkedHashMap<>();
        private final List<StockListener> listeners = new ArrayList<>();

        StockMarket(String name) {
            this.name = name;
            // Ceny poczatkowe
            prices.put("AAPL",  150.00);
            prices.put("GOOGL", 2800.00);
            prices.put("AMZN",  3400.00);
            prices.put("MSFT",  290.00);
        }

        public void addListener(StockListener listener) {
            listeners.add(listener);
        }

        public void removeListener(StockListener listener) {
            listeners.remove(listener);
        }

        public void updatePrice(String symbol, double newPrice) {
            double oldPrice = prices.getOrDefault(symbol, newPrice);
            prices.put(symbol, newPrice);
            if (Math.abs(newPrice - oldPrice) > 0.001) {
                notifyListeners(new StockEvent(symbol, oldPrice, newPrice, Instant.now()));
            }
        }

        private void notifyListeners(StockEvent event) {
            for (StockListener listener : listeners) {
                listener.onPriceChange(event);   // Subject nie zna konkretnych klas!
            }
        }

        public double getPrice(String symbol) {
            return prices.getOrDefault(symbol, 0.0);
        }
    }

    // ─── Konkretni obserwatorzy ───────────────────────────────────────────────
    static class MobileApp implements StockListener {
        private final String userId;
        private final double threshold;  // procent zmiany wymagajacy powiadomienia

        MobileApp(String userId, double threshold) {
            this.userId    = userId;
            this.threshold = threshold;
        }

        @Override
        public void onPriceChange(StockEvent event) {
            if (Math.abs(event.changePercent()) >= threshold) {
                System.out.printf("  📱 [MobileApp:%s] PUSH: %s %s%.1f%% (%.2f → %.2f)%n",
                        userId, event.symbol(),
                        event.isGain() ? "▲" : "▼",
                        Math.abs(event.changePercent()),
                        event.oldPrice(), event.newPrice());
            }
        }
    }

    static class TradingBot implements StockListener {
        private final String name;
        private double portfolio = 10000.0;
        private final java.util.Map<String, Integer> holdings = new java.util.HashMap<>();

        TradingBot(String name) { this.name = name; }

        @Override
        public void onPriceChange(StockEvent event) {
            if (event.changePercent() < -2.0) {
                // Kup przy spadku > 2%
                int qty = (int)(portfolio / event.newPrice() * 0.1);
                if (qty > 0 && portfolio >= qty * event.newPrice()) {
                    portfolio -= qty * event.newPrice();
                    holdings.merge(event.symbol(), qty, Integer::sum);
                    System.out.printf("  🤖 [Bot:%s] BUY %d × %s @ %.2f (portfolio: %.0f)%n",
                            name, qty, event.symbol(), event.newPrice(), portfolio);
                }
            } else if (event.changePercent() > 3.0) {
                // Sprzedaj przy wzroscie > 3%
                Integer held = holdings.getOrDefault(event.symbol(), 0);
                if (held > 0) {
                    portfolio += held * event.newPrice();
                    holdings.remove(event.symbol());
                    System.out.printf("  🤖 [Bot:%s] SELL %d × %s @ %.2f (portfolio: %.0f)%n",
                            name, held, event.symbol(), event.newPrice(), portfolio);
                }
            }
        }
    }

    static class AuditLogger implements StockListener {
        private final List<String> log = new ArrayList<>();

        @Override
        public void onPriceChange(StockEvent event) {
            String entry = String.format("[%s] %s: %.2f → %.2f (%.1f%%)",
                    event.time(), event.symbol(),
                    event.oldPrice(), event.newPrice(), event.changePercent());
            log.add(entry);
            System.out.println("  📋 [Logger] " + entry);
        }

        public void printReport() {
            System.out.println("  === Raport Audytowy (" + log.size() + " zdarzen) ===");
            log.forEach(e -> System.out.println("    " + e));
        }
    }

    // ─── Lambda jako Observer (functional interface) ──────────────────────────
    static StockListener alertOnCrash(double threshold) {
        return event -> {
            if (event.changePercent() < -threshold) {
                System.out.printf("  🚨 ALERT: %s runuje! Zmiana: %.1f%%%n",
                        event.symbol(), event.changePercent());
            }
        };
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Observer Pattern ===\n");

        StockMarket market = new StockMarket("WSE");

        // Rejestracja obserwatorow
        MobileApp  alice    = new MobileApp("alice", 1.5);
        MobileApp  bob      = new MobileApp("bob", 3.0);
        TradingBot bot1     = new TradingBot("AlphaBot");
        AuditLogger logger  = new AuditLogger();

        market.addListener(alice);
        market.addListener(bob);
        market.addListener(bot1);
        market.addListener(logger);
        market.addListener(alertOnCrash(5.0));  // lambda!

        System.out.println("=== Aktualizacje cen ===");

        // Mala zmiana — tylko Alice to widzi (jej prog to 1.5%)
        System.out.println("\nAktualizacja 1: AAPL maly wzrost");
        market.updatePrice("AAPL", 152.50);  // +1.67%

        // Duzy wzrost — wszyscy widza
        System.out.println("\nAktualizacja 2: MSFT duzy wzrost");
        market.updatePrice("MSFT", 302.00);  // +4.14%

        // Crash — bot kupuje, logger rejestruje, alert lambda odpala sie
        System.out.println("\nAktualizacja 3: GOOGL crash");
        market.updatePrice("GOOGL", 2600.00);  // -7.14%

        // Bob rezygnuje z obserwacji
        System.out.println("\n=== Bob usuwa sie z obserwatorow ===");
        market.removeListener(bob);
        market.updatePrice("AMZN", 3500.00);   // +2.94% — bob nie dostaje

        System.out.println("\n=== Bot sprzedaje na wzroscie AMZN ===");
        market.updatePrice("AMZN", 3200.00);

        System.out.println();
        logger.printReport();
    }
}

