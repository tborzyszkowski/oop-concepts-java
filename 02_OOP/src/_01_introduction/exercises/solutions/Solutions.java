package _01_introduction.exercises.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * Rozwiązania zadań 1–4.
 * Udostępniane PO wykonaniu zadań przez studentów.
 */
public class Solutions {

    // ============================================================
    // ROZWIĄZANIE ZADANIA 1: Rectangle
    // ============================================================
    public static class Rectangle {
        public static int instanceCount = 0;

        private double width;
        private double height;

        public Rectangle(double width, double height) {
            if (width <= 0 || height <= 0)
                throw new IllegalArgumentException("width i height muszą być > 0");
            this.width = width;
            this.height = height;
            instanceCount++;
        }

        public double getWidth()  { return width; }
        public double getHeight() { return height; }

        public void setWidth(double w) {
            if (w <= 0) throw new IllegalArgumentException("width > 0");
            this.width = w;
        }
        public void setHeight(double h) {
            if (h <= 0) throw new IllegalArgumentException("height > 0");
            this.height = h;
        }

        public double area()      { return width * height; }
        public double perimeter() { return 2 * (width + height); }
        public boolean isSquare() { return Double.compare(width, height) == 0; }

        public Rectangle scale(double factor) {
            if (factor <= 0) throw new IllegalArgumentException("factor > 0");
            return new Rectangle(width * factor, height * factor);
        }

        @Override public String toString() {
            return "Rectangle{" + width + " x " + height + "}";
        }
    }

    // ============================================================
    // ROZWIĄZANIE ZADANIA 2: TeamCopy (głęboka kopia)
    // ============================================================
    public static class TeamMember {
        private String name;
        private String role;
        private List<String> skills;

        public TeamMember(String name, String role, List<String> skills) {
            this.name = name;
            this.role = role;
            this.skills = new ArrayList<>(skills);
        }

        // Konstruktor kopiujący — deep copy!
        public TeamMember(TeamMember other) {
            this.name = other.name;
            this.role = other.role;
            this.skills = new ArrayList<>(other.skills); // nowa lista
        }

        public void addSkill(String skill) { skills.add(skill); }
        public String getName() { return name; }
        public String getRole() { return role; }
        public List<String> getSkills() { return List.copyOf(skills); }

        @Override public String toString() {
            return name + "(" + role + ")" + skills;
        }
    }

    public static class Team {
        private String teamName;
        private List<TeamMember> members;

        public Team(String teamName) {
            this.teamName = teamName;
            this.members = new ArrayList<>();
        }

        public void addMember(TeamMember m) { members.add(m); }
        public int size() { return members.size(); }
        public String getTeamName() { return teamName; }
        public List<TeamMember> getMembers() { return List.copyOf(members); }

        public Team deepCopy() {
            Team copy = new Team(this.teamName);
            for (TeamMember m : members) {
                copy.addMember(new TeamMember(m)); // deep copy każdego członka
            }
            return copy;
        }
    }

    // ============================================================
    // ROZWIĄZANIE ZADANIA 4: Calculator z TDD
    // ============================================================
    public static class Calculator {
        private final List<String> history = new ArrayList<>();

        public double add(double a, double b) {
            double result = a + b;
            history.add("add(" + a + "," + b + ")=" + result);
            return result;
        }

        public double subtract(double a, double b) {
            double result = a - b;
            history.add("subtract(" + a + "," + b + ")=" + result);
            return result;
        }

        public double multiply(double a, double b) {
            double result = a * b;
            history.add("multiply(" + a + "," + b + ")=" + result);
            return result;
        }

        public double divide(double a, double b) {
            if (b == 0) throw new ArithmeticException("Dzielenie przez zero");
            double result = a / b;
            history.add("divide(" + a + "," + b + ")=" + result);
            return result;
        }

        public double power(double base, int exponent) {
            double result = Math.pow(base, exponent);
            history.add("power(" + base + "," + exponent + ")=" + result);
            return result;
        }

        public double sqrt(double n) {
            if (n < 0) throw new IllegalArgumentException("Pierwiastek z ujemnej");
            double result = Math.sqrt(n);
            history.add("sqrt(" + n + ")=" + result);
            return result;
        }

        public List<String> getHistory() { return List.copyOf(history); }
        public void clearHistory()       { history.clear(); }
    }
}

// ================================================================
// Rozwiązania Zadania 5 — Temperature i GeoPoint (record, Value Object)
// ================================================================

/**
 * Zadanie 5a: Temperature — niemutowalny Value Object reprezentujący temperaturę.
 */
record Temperature(double value, String scale) {

    private static final java.util.Set<String> VALID_SCALES =
        java.util.Set.of("C", "F", "K");

    Temperature {
        if (!VALID_SCALES.contains(scale))
            throw new IllegalArgumentException(
                "Nieznana skala: " + scale + ". Dopuszczalne: C, F, K");
        if ("K".equals(scale) && value < 0)
            throw new IllegalArgumentException(
                "Temperatura w Kelvinach nie może być ujemna: " + value);
    }

    /** Zwraca nowy obiekt Temperature w skali Celsius. */
    public Temperature toCelsius() {
        return switch (scale) {
            case "C" -> this;
            case "F" -> new Temperature((value - 32.0) * 5.0 / 9.0, "C");
            case "K" -> new Temperature(value - 273.15, "C");
            default  -> throw new AssertionError("Nieznana skala: " + scale);
        };
    }

    /** Zwraca nowy obiekt Temperature w skali Fahrenheit. */
    public Temperature toFahrenheit() {
        return switch (scale) {
            case "F" -> this;
            case "C" -> new Temperature(value * 9.0 / 5.0 + 32.0, "F");
            case "K" -> new Temperature((value - 273.15) * 9.0 / 5.0 + 32.0, "F");
            default  -> throw new AssertionError("Nieznana skala: " + scale);
        };
    }

    @Override public String toString() {
        return "%.2f°%s".formatted(value, scale);
    }
}

/**
 * Zadanie 5b: GeoPoint — niemutowalny Value Object reprezentujący punkt geograficzny.
 */
record GeoPoint(double latitude, double longitude) {

    private static final double EARTH_RADIUS_KM = 6371.0;

    GeoPoint {
        if (latitude < -90.0 || latitude > 90.0)
            throw new IllegalArgumentException(
                "Szerokość geograficzna poza zakresem [-90, 90]: " + latitude);
        if (longitude < -180.0 || longitude > 180.0)
            throw new IllegalArgumentException(
                "Długość geograficzna poza zakresem [-180, 180]: " + longitude);
    }

    /**
     * Odległość w km — uproszczona formuła (bez krzywizny Ziemi).
     * Dla dokładnych obliczeń użyj wzoru Haversine.
     */
    public double distanceTo(GeoPoint other) {
        double latDiff = Math.toRadians(other.latitude  - this.latitude);
        double lonDiff = Math.toRadians(other.longitude - this.longitude);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                 + Math.cos(Math.toRadians(this.latitude))
                   * Math.cos(Math.toRadians(other.latitude))
                   * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    /** Czy ten punkt leży na północ od podanego? */
    public boolean isNorthOf(GeoPoint other) {
        return this.latitude > other.latitude;
    }

    @Override public String toString() {
        return "(%.4f°N, %.4f°E)".formatted(latitude, longitude);
    }

    /** Demo rozwiązania — uruchamialny main */
    public static void main(String[] args) {
        System.out.println("=== Zadanie 5 — rozwiązania ===");

        Temperature boiling = new Temperature(100.0, "C");
        System.out.println("100°C → F: " + boiling.toFahrenheit());     // 212.00°F

        Temperature absZero = new Temperature(0.0, "K");
        System.out.println("0 K → C: " + absZero.toCelsius());           // -273.15°C

        Temperature bodyTemp = new Temperature(98.6, "F");
        System.out.println("98.6°F → C: " + bodyTemp.toCelsius());       // 37.00°C

        GeoPoint warsaw = new GeoPoint(52.23, 21.01);
        GeoPoint krakow = new GeoPoint(50.06, 19.94);
        System.out.println("Warszawa: " + warsaw);
        System.out.println("Kraków:   " + krakow);
        System.out.printf("Odległość: %.1f km%n", warsaw.distanceTo(krakow));
        System.out.println("Warszawa jest na północ od Krakowa: " + warsaw.isNorthOf(krakow));

        try { new Temperature(-1.0, "K"); }
        catch (IllegalArgumentException e) { System.out.println("Walidacja K: " + e.getMessage()); }

        try { new GeoPoint(91.0, 0.0); }
        catch (IllegalArgumentException e) { System.out.println("Walidacja lat: " + e.getMessage()); }
    }
}


