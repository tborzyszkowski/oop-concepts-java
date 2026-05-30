package _10_wzorce._04_builder.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder Pattern — dwa przykladowe zastosowania:
 * 1. Pizza.Builder (klasyczny GoF)
 * 2. HttpRequest.Builder (styl JDK 11+)
 */
public class BuilderDemo {

    // ─── 1. KLASYCZNY BUILDER — Pizza ────────────────────────────────────────
    static final class Pizza {
        private final String size;
        private final String crust;
        private final String sauce;
        private final List<String> toppings;
        private final boolean extraCheese;
        private final boolean glutenFree;

        private Pizza(Builder b) {
            this.size        = b.size;
            this.crust       = b.crust;
            this.sauce       = b.sauce;
            this.toppings    = Collections.unmodifiableList(new ArrayList<>(b.toppings));
            this.extraCheese = b.extraCheese;
            this.glutenFree  = b.glutenFree;
        }

        @Override public String toString() {
            return "Pizza{size=" + size + ", crust=" + crust
                    + ", sauce=" + sauce + ", toppings=" + toppings
                    + (extraCheese ? ", extraCheese" : "")
                    + (glutenFree  ? ", glutenFree"  : "") + "}";
        }

        public double calculatePrice() {
            double base = switch (size) {
                case "small"  -> 15.0;
                case "medium" -> 20.0;
                case "large"  -> 25.0;
                default       -> 20.0;
            };
            return base + toppings.size() * 2.5
                        + (extraCheese ? 3.0 : 0)
                        + (glutenFree  ? 4.0 : 0);
        }

        // ── Builder ──────────────────────────────────────────────────────────
        static final class Builder {
            private String size = "medium";
            private String crust = "normal";
            private String sauce = "tomato";
            private final List<String> toppings = new ArrayList<>();
            private boolean extraCheese = false;
            private boolean glutenFree  = false;

            public Builder size(String size) {
                if (!List.of("small","medium","large").contains(size))
                    throw new IllegalArgumentException("Nieprawidlowy rozmiar: " + size);
                this.size = size;
                return this;
            }
            public Builder crust(String crust)    { this.crust = crust; return this; }
            public Builder sauce(String sauce)    { this.sauce = sauce; return this; }
            public Builder addTopping(String top) { this.toppings.add(top); return this; }
            public Builder extraCheese()          { this.extraCheese = true; return this; }
            public Builder glutenFree()           { this.glutenFree = true; return this; }

            public Pizza build() {
                if (toppings.isEmpty()) throw new IllegalStateException("Pizza musi miec co najmniej 1 topping");
                return new Pizza(this);
            }
        }
    }

    // ─── 2. BUILDER STYL JDK — HttpRequest ───────────────────────────────────
    static final class HttpRequest {
        private final String method;
        private final String url;
        private final java.util.Map<String, String> headers;
        private final String body;
        private final int timeoutMs;

        private HttpRequest(Builder b) {
            this.method    = b.method;
            this.url       = b.url;
            this.headers   = Collections.unmodifiableMap(new java.util.LinkedHashMap<>(b.headers));
            this.body      = b.body;
            this.timeoutMs = b.timeoutMs;
        }

        @Override public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(method).append(" ").append(url).append("\n");
            headers.forEach((k,v) -> sb.append("  ").append(k).append(": ").append(v).append("\n"));
            if (body != null) sb.append("  Body: ").append(body.substring(0, Math.min(body.length(), 50)));
            return sb.toString();
        }

        static Builder newBuilder(String method, String url) {
            return new Builder(method, url);
        }

        static final class Builder {
            private final String method;
            private final String url;
            private final java.util.Map<String,String> headers = new java.util.LinkedHashMap<>();
            private String body;
            private int timeoutMs = 30_000;

            private Builder(String method, String url) {
                this.method = method;
                this.url    = url;
            }

            public Builder header(String key, String value) { headers.put(key, value); return this; }
            public Builder body(String body) { this.body = body; return this; }
            public Builder timeout(int ms)   { this.timeoutMs = ms; return this; }
            public Builder contentType(String ct) { return header("Content-Type", ct); }
            public Builder bearer(String token)   { return header("Authorization", "Bearer " + token); }

            public HttpRequest build() {
                if (url == null || url.isBlank())
                    throw new IllegalStateException("URL jest wymagany");
                return new HttpRequest(this);
            }
        }
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Builder Pattern ===\n");

        System.out.println("1. Pizza.Builder — Fluent API:");

        Pizza margherita = new Pizza.Builder()
                .size("medium")
                .crust("thin")
                .sauce("tomato")
                .addTopping("mozzarella")
                .addTopping("basil")
                .build();
        System.out.println("  Margherita: " + margherita);
        System.out.printf("  Cena: %.1f PLN%n", margherita.calculatePrice());

        Pizza special = new Pizza.Builder()
                .size("large")
                .crust("stuffed")
                .sauce("bbq")
                .addTopping("chicken")
                .addTopping("bacon")
                .addTopping("red onion")
                .extraCheese()
                .glutenFree()
                .build();
        System.out.println("  Special: " + special);
        System.out.printf("  Cena: %.1f PLN%n", special.calculatePrice());

        System.out.println("\n2. HttpRequest.Builder — styl JDK 11+:");

        HttpRequest getReq = HttpRequest.newBuilder("GET", "https://api.example.com/users")
                .bearer("eyJhbGciOiJSUzI1NiJ9...")
                .header("Accept", "application/json")
                .timeout(5000)
                .build();
        System.out.println("GET request:\n" + getReq);

        HttpRequest postReq = HttpRequest.newBuilder("POST", "https://api.example.com/users")
                .bearer("eyJhbGciOiJSUzI1NiJ9...")
                .contentType("application/json")
                .body("{\"name\":\"Alice\",\"email\":\"alice@example.com\"}")
                .timeout(10000)
                .build();
        System.out.println("POST request:\n" + postReq);

        System.out.println("\n3. Builder wbudowany w JDK:");
        String sql = new StringBuilder()
                .append("SELECT u.name, o.total ")
                .append("FROM users u ")
                .append("JOIN orders o ON u.id = o.user_id ")
                .append("WHERE u.active = true ")
                .append("ORDER BY o.total DESC")
                .toString();
        System.out.println("  SQL: " + sql);
    }
}

