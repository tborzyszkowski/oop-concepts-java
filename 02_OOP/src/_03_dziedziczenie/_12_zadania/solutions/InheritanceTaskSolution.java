package inheritance.t12.solutions;

public class InheritanceTaskSolution {
    public abstract static class Report {
        public abstract String render();
    }

    public static class PdfReport extends Report {
        @Override
        public String render() {
            return "PDF";
        }
    }

    public static class HtmlReport extends Report {
        @Override
        public String render() {
            return "HTML";
        }
    }

    public static String export(Report report) {
        return report.render();
    }

    public static void main(String[] args) {
        System.out.println(export(new PdfReport()));
        System.out.println(export(new HtmlReport()));
    }
}


