package control_flow.examples;

/**
 * Demonstracja instrukcji warunkowych: if/else, ternary, switch klasyczny.
 * Java 21+
 */
public class ConditionalsDemo {

    public static void main(String[] args) {

        System.out.println("=== Instrukcje warunkowe ===");
        System.out.println();

        // ===== if / else if / else =====
        System.out.println("--- 1. if / else if / else ---");
        int temperature = 25;

        if (temperature > 30) {
            System.out.println("Upał — powyżej 30°C");
        } else if (temperature > 20) {
            System.out.println("Ciepło — " + temperature + "°C");
        } else if (temperature > 10) {
            System.out.println("Chłodno");
        } else {
            System.out.println("Zimno");
        }

        // ===== Operator trójargumentowy (ternary) =====
        System.out.println();
        System.out.println("--- 2. Operator ternary: warunek ? tak : nie ---");
        int age = 20;
        String status = (age >= 18) ? "dorosły" : "małoletni";
        System.out.println("Wiek " + age + " → " + status);

        // Zagnieżdżony ternary (OSTROŻNIE — czytelność!)
        int score = 75;
        String grade = (score >= 90) ? "A"
                : (score >= 80) ? "B"
                : (score >= 70) ? "C"
                : (score >= 60) ? "D" : "F";
        System.out.println("Wynik " + score + " → ocena " + grade);

        // ===== switch klasyczny (Java 8 styl) =====
        System.out.println();
        System.out.println("--- 3. switch klasyczny (tradycyjny) ---");
        int dayOfWeek = 3;
        String dayName;

        switch (dayOfWeek) {
            case 1: dayName = "Poniedziałek"; break;
            case 2: dayName = "Wtorek";       break;
            case 3: dayName = "Środa";        break;
            case 4: dayName = "Czwartek";     break;
            case 5: dayName = "Piątek";       break;
            case 6: dayName = "Sobota";       break;
            case 7: dayName = "Niedziela";    break;
            default: dayName = "Nieznany";
        }
        System.out.println("Dzień " + dayOfWeek + " = " + dayName);

        // Fall-through (bez break!) — cecha specyficzna dla Java/C
        System.out.println();
        System.out.println("--- 4. Fall-through w switch (uwaga!) ---");
        int month = 4;
        System.out.print("Miesiąc " + month + " ma ");
        switch (month) {
            case 2:
                System.out.println("28 lub 29 dni");
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                System.out.println("30 dni"); // fall-through na case 4,6,9,11
                break;
            default:
                System.out.println("31 dni");
        }

        // ===== switch expression (Java 14+) =====
        System.out.println();
        System.out.println("--- 5. switch expression (Java 14+, zalecane) ---");
        String day = "WEDNESDAY";

        int numLetters = switch (day) {
            case "MONDAY", "FRIDAY", "SUNDAY" -> 6;
            case "TUESDAY"                    -> 7;
            case "THURSDAY", "SATURDAY"       -> 8;
            case "WEDNESDAY"                  -> 9;
            default -> throw new IllegalArgumentException("Nieznany dzień: " + day);
        };
        System.out.println(day + " ma " + numLetters + " liter");

        // switch expression z blokiem i yield
        System.out.println();
        System.out.println("--- 6. switch expression z yield ---");
        int x = 5;
        String description = switch (x) {
            case 1, 2, 3 -> "małe";
            case 4, 5, 6 -> {
                // blok z wieloma instrukcjami
                String prefix = x % 2 == 0 ? "parzyste" : "nieparzyste";
                yield prefix + " średnie"; // yield zamiast return
            }
            default -> "duże";
        };
        System.out.println("x=" + x + " → " + description);
    }
}

