package introduction.exercises.solutions;

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

