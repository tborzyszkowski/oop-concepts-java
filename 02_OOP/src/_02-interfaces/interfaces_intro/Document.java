package interfaces_intro;

/** Dokument — pierwsza implementacja kontraktu Printable. */
public class Document implements Printable {
    private final String title;
    private final String content;

    public Document(String title, String content) {
        this.title   = title;
        this.content = content;
    }

    @Override
    public void print() {
        System.out.println("=== DOKUMENT: " + title + " ===");
        System.out.println(content);
        System.out.println("=================================");
    }

    public String getTitle() { return title; }
}

