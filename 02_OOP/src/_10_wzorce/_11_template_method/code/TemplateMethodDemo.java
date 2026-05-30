package _10_wzorce._11_template_method.code;

import java.util.*;

/**
 * Template Method — szkielet algorytmu w klasie bazowej,
 * detale w podklasach.
 */
public class TemplateMethodDemo {

    // ─── Template: Eksport danych ──────────────────────────────────────────────
    abstract static class DataExporter {
        // Template Method — final, nie mozna nadpisac
        public final String export(List<Map<String, Object>> data) {
            String raw = serialize(data);
            String processed = applyTransformations(raw);
            String withHeader = addHeader(processed);
            return withHeader;
        }

        // Kroki do nadpisania przez podklasy (abstract = obowiazkowe)
        protected abstract String serialize(List<Map<String, Object>> data);

        // Kroki opcjonalne (hook — podklasa moze, ale nie musi nadpisac)
        protected String applyTransformations(String data) {
            return data;  // domyslnie brak transformacji
        }

        protected String addHeader(String data) {
            return "# Exported by " + getClass().getSimpleName() + "\n" + data;
        }

        protected abstract String getFormat();

        public void printExport(List<Map<String, Object>> data) {
            System.out.println("  === Export jako " + getFormat() + " ===");
            System.out.println(export(data));
        }
    }

    static class CsvExporter extends DataExporter {
        @Override
        protected String serialize(List<Map<String, Object>> data) {
            if (data.isEmpty()) return "";
            StringBuilder sb = new StringBuilder();
            // Naglowki
            sb.append(String.join(",", data.get(0).keySet())).append("\n");
            // Wiersze
            for (var row : data) {
                sb.append(String.join(",", row.values().stream()
                        .map(Object::toString).toArray(String[]::new))).append("\n");
            }
            return sb.toString();
        }

        @Override protected String getFormat() { return "CSV"; }
    }

    static class JsonExporter extends DataExporter {
        @Override
        protected String serialize(List<Map<String, Object>> data) {
            StringBuilder sb = new StringBuilder("[\n");
            for (int i = 0; i < data.size(); i++) {
                sb.append("  {");
                var entries = data.get(i).entrySet().stream().toList();
                for (int j = 0; j < entries.size(); j++) {
                    var e = entries.get(j);
                    sb.append("\"").append(e.getKey()).append("\": ");
                    Object v = e.getValue();
                    if (v instanceof String) sb.append("\"").append(v).append("\"");
                    else sb.append(v);
                    if (j < entries.size()-1) sb.append(", ");
                }
                sb.append("}");
                if (i < data.size()-1) sb.append(",");
                sb.append("\n");
            }
            sb.append("]");
            return sb.toString();
        }

        @Override
        protected String addHeader(String data) {
            // JSON nie ma komentarzy — nie dodajemy naglowka
            return data;
        }

        @Override protected String getFormat() { return "JSON"; }
    }

    static class HtmlExporter extends DataExporter {
        @Override
        protected String serialize(List<Map<String, Object>> data) {
            if (data.isEmpty()) return "<table><tr><td>No data</td></tr></table>";
            StringBuilder sb = new StringBuilder("<table border='1'>\n");
            // Naglowki
            sb.append("  <tr>");
            data.get(0).keySet().forEach(k -> sb.append("<th>").append(k).append("</th>"));
            sb.append("</tr>\n");
            // Wiersze
            for (var row : data) {
                sb.append("  <tr>");
                row.values().forEach(v -> sb.append("<td>").append(v).append("</td>"));
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            return sb.toString();
        }

        @Override
        protected String applyTransformations(String data) {
            // Hook — zamieniamy < i > w danych (XSS prevention)
            // (tu uproszczone, w realu uzyj biblioteki)
            return data;
        }

        @Override
        protected String addHeader(String data) {
            return "<!DOCTYPE html>\n<html><body>\n" + data + "\n</body></html>";
        }

        @Override protected String getFormat() { return "HTML"; }
    }

    // ─── Template: Walidacja danych ───────────────────────────────────────────
    abstract static class DataValidator<T> {
        // Template Method
        public final List<String> validate(T data) {
            List<String> errors = new ArrayList<>();
            checkNull(data, errors);
            if (errors.isEmpty()) {
                checkFormat(data, errors);
                checkBusinessRules(data, errors);
            }
            return errors;
        }

        private void checkNull(T data, List<String> errors) {
            if (data == null) errors.add("Dane nie moga byc null");
        }

        protected abstract void checkFormat(T data, List<String> errors);
        protected abstract void checkBusinessRules(T data, List<String> errors);
    }

    record UserRegistration(String username, String email, int age, String password) {}

    static class UserValidator extends DataValidator<UserRegistration> {
        @Override
        protected void checkFormat(UserRegistration data, List<String> errors) {
            if (data.username() == null || data.username().length() < 3)
                errors.add("Nazwa uzytkownika musi miec min 3 znaki");
            if (data.email() == null || !data.email().contains("@"))
                errors.add("Nieprawidlowy email");
            if (data.password() == null || data.password().length() < 8)
                errors.add("Haslo musi miec min 8 znakow");
        }

        @Override
        protected void checkBusinessRules(UserRegistration data, List<String> errors) {
            if (data.age() < 18) errors.add("Uzytkownik musi miec co najmniej 18 lat");
            if (data.age() > 120) errors.add("Nieprawidlowy wiek: " + data.age());
            if (data.password() != null && data.username() != null &&
                data.password().contains(data.username()))
                errors.add("Haslo nie moze zawierac nazwy uzytkownika");
        }
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Template Method Pattern ===\n");

        // Dane testowe
        List<Map<String, Object>> employees = List.of(
            Map.of("id", 1, "name", "Alice Kowalska", "dept", "Engineering", "salary", 8500),
            Map.of("id", 2, "name", "Bob Nowak",      "dept", "Marketing",   "salary", 6200),
            Map.of("id", 3, "name", "Carol Wisniewska","dept", "HR",         "salary", 5800)
        );

        System.out.println("1. Export danych — ten sam algorytm, rozne formaty:");

        new CsvExporter().printExport(employees);
        System.out.println();
        new JsonExporter().printExport(employees);
        System.out.println();
        new HtmlExporter().printExport(employees);

        System.out.println("\n2. Walidacja — Template Method z hookami:");

        UserValidator validator = new UserValidator();

        var okUser = new UserRegistration("alice99", "alice@example.com", 25, "SecurePass1!");
        List<String> errors1 = validator.validate(okUser);
        System.out.println("  " + okUser.username() + ": " +
                (errors1.isEmpty() ? "OK" : "BLEDY: " + errors1));

        var badUser = new UserRegistration("al", "invalid-email", 16, "al");
        List<String> errors2 = validator.validate(badUser);
        System.out.println("  Nieprawidlowy uzytkownik:");
        errors2.forEach(e -> System.out.println("    ✗ " + e));

        System.out.println("\n3. Template Method w JDK:");
        System.out.println("  AbstractList.add() definiuje szkielet,");
        System.out.println("  ArrayList i LinkedList implementuja szczegoly.");
        System.out.println("  HttpServlet.service() wywoluje doGet/doPost/doPut...");
        System.out.println("  Ty implementujesz tylko doGet(), reszta jest w szablonie.");
    }
}

