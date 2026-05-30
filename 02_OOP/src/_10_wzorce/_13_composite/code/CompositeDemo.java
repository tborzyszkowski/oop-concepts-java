package _10_wzorce._13_composite.code;

import java.util.*;

/**
 * Composite Pattern — system plikow i menu hierarchiczne.
 */
public class CompositeDemo {

    // ─── SYSTEM PLIKOW ────────────────────────────────────────────────────────
    interface FileSystemItem {
        String getName();
        long getSize();
        void print(String indent);
        void accept(FileSystemVisitor visitor);
    }

    // Visitor (uzywany razem z Composite do operacji na drzewie)
    interface FileSystemVisitor {
        void visitFile(FileItem file);
        void visitDirectory(Directory dir);
    }

    // Leaf — plik
    static class FileItem implements FileSystemItem {
        private final String name;
        private final long size;
        private final String type;

        FileItem(String name, long sizeKb, String type) {
            this.name = name;
            this.size = sizeKb * 1024;
            this.type = type;
        }

        @Override public String getName() { return name; }
        @Override public long getSize()   { return size; }

        @Override public void print(String indent) {
            System.out.printf("%s📄 %-30s %s (%,d KB)%n",
                    indent, name, type, size / 1024);
        }

        @Override public void accept(FileSystemVisitor visitor) {
            visitor.visitFile(this);
        }

        public String getType() { return type; }
    }

    // Composite — katalog
    static class Directory implements FileSystemItem {
        private final String name;
        private final List<FileSystemItem> children = new ArrayList<>();

        Directory(String name) { this.name = name; }

        public void add(FileSystemItem item) { children.add(item); }
        public void remove(FileSystemItem item) { children.remove(item); }
        public List<FileSystemItem> getChildren() { return Collections.unmodifiableList(children); }

        @Override public String getName() { return name; }

        @Override public long getSize() {
            // Rekurencyjnie sumuje rozmiar wszystkich dzieci
            return children.stream().mapToLong(FileSystemItem::getSize).sum();
        }

        @Override public void print(String indent) {
            System.out.printf("%s📁 %-30s [%,d KB total]%n",
                    indent, name + "/", getSize() / 1024);
            children.forEach(child -> child.print(indent + "  "));
        }

        @Override public void accept(FileSystemVisitor visitor) {
            visitor.visitDirectory(this);
            children.forEach(c -> c.accept(visitor));
        }
    }

    // Visitor — zlicza pliki wg typu
    static class FileTypeCounter implements FileSystemVisitor {
        private final Map<String, Integer> counts = new TreeMap<>();
        private int totalFiles = 0;

        @Override public void visitFile(FileItem file) {
            counts.merge(file.getType(), 1, Integer::sum);
            totalFiles++;
        }

        @Override public void visitDirectory(Directory dir) { /* nic */ }

        public void printReport() {
            System.out.println("  === Raport typow plikow ===");
            counts.forEach((type, count) ->
                    System.out.printf("  %-10s : %d plikow%n", type, count));
            System.out.println("  Razem    : " + totalFiles + " plikow");
        }
    }

    // ─── MENU HIERARCHICZNE ───────────────────────────────────────────────────
    interface MenuComponent {
        String getName();
        void print(String indent);
        boolean isLeaf();
    }

    static class MenuItem implements MenuComponent {
        private final String name;
        private final String shortcut;
        private final Runnable action;

        MenuItem(String name, String shortcut, Runnable action) {
            this.name     = name;
            this.shortcut = shortcut;
            this.action   = action;
        }

        @Override public String getName() { return name; }
        @Override public boolean isLeaf() { return true; }

        @Override public void print(String indent) {
            System.out.printf("%s  %-20s %s%n", indent, name,
                    shortcut.isEmpty() ? "" : "[" + shortcut + "]");
        }

        public void trigger() { action.run(); }
    }

    static class Menu implements MenuComponent {
        private final String name;
        private final List<MenuComponent> items = new ArrayList<>();

        Menu(String name) { this.name = name; }

        public Menu add(MenuComponent item) { items.add(item); return this; }

        @Override public String getName() { return name; }
        @Override public boolean isLeaf() { return false; }

        @Override public void print(String indent) {
            System.out.println(indent + "▶ " + name);
            items.forEach(item -> item.print(indent + "  "));
        }
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Composite Pattern ===\n");

        // ── System plikow ─────────────────────────────────────────────────────
        System.out.println("1. System plikow (Composite + rekurencja):");

        Directory home = new Directory("home");

        Directory docs = new Directory("documents");
        docs.add(new FileItem("cv.pdf",            150, "PDF"));
        docs.add(new FileItem("cover_letter.docx",  45, "DOCX"));

        Directory projects = new Directory("projects");
        Directory javaApp  = new Directory("my-java-app");
        javaApp.add(new FileItem("App.java",   12, "Java"));
        javaApp.add(new FileItem("pom.xml",     3, "XML"));
        Directory src = new Directory("src");
        src.add(new FileItem("Main.java",      25, "Java"));
        src.add(new FileItem("Service.java",   18, "Java"));
        src.add(new FileItem("config.yml",      2, "YAML"));
        javaApp.add(src);
        projects.add(javaApp);

        Directory photos = new Directory("photos");
        photos.add(new FileItem("holiday-2024.jpg", 3500, "JPEG"));
        photos.add(new FileItem("portrait.png",     1200, "PNG"));
        photos.add(new FileItem("family.jpg",       4100, "JPEG"));

        home.add(docs);
        home.add(projects);
        home.add(photos);
        home.add(new FileItem(".bashrc",  2, "CONFIG"));

        System.out.println("\nStruktura katalogow:");
        home.print("");

        System.out.printf("%nRozmiar /home: %,d KB%n", home.getSize() / 1024);
        System.out.printf("Rozmiar /home/documents: %,d KB%n", docs.getSize() / 1024);

        // Visitor na Composite
        System.out.println();
        FileTypeCounter counter = new FileTypeCounter();
        home.accept(counter);
        counter.printReport();

        // ── Menu hierarchiczne ────────────────────────────────────────────────
        System.out.println("\n2. Menu hierarchiczne (Composite):");

        Menu fileMenu = new Menu("Plik")
                .add(new MenuItem("Nowy",          "Ctrl+N", () -> System.out.println("  [Nowy dokument]")))
                .add(new MenuItem("Otworz",        "Ctrl+O", () -> System.out.println("  [Otworz plik]")))
                .add(new MenuItem("Zapisz",        "Ctrl+S", () -> System.out.println("  [Zapisz]")))
                .add(new MenuItem("Zapisz jako...", "",      () -> System.out.println("  [Zapisz jako]")));

        Menu editMenu = new Menu("Edycja")
                .add(new MenuItem("Cofnij",  "Ctrl+Z", () -> System.out.println("  [Undo]")))
                .add(new MenuItem("Ponow",   "Ctrl+Y", () -> System.out.println("  [Redo]")))
                .add(new MenuItem("Kopiuj",  "Ctrl+C", () -> System.out.println("  [Kopiuj]")))
                .add(new MenuItem("Wklej",   "Ctrl+V", () -> System.out.println("  [Wklej]")));

        Menu advancedEdit = new Menu("Zaawansowane");
        advancedEdit.add(new MenuItem("Regex Replace", "Ctrl+R", () -> System.out.println("  [Regex]")));
        advancedEdit.add(new MenuItem("Format code",   "Ctrl+L", () -> System.out.println("  [Format]")));
        editMenu.add(advancedEdit);

        Menu mainMenu = new Menu("=== MENU APLIKACJI ===")
                .add(fileMenu)
                .add(editMenu)
                .add(new MenuItem("Pomoc", "F1", () -> System.out.println("  [Pomoc]")));

        mainMenu.print("");

        System.out.println("\nKlucz: Composite traktuje Plik i Katalog tak samo,");
        System.out.println("  bo oba implementuja FileSystemItem.");
        System.out.println("  client.print() dziala rekurencyjnie na calym drzewie.");
    }
}

