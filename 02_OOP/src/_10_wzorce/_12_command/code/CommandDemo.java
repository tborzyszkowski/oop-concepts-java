package _10_wzorce._12_command.code;

import java.util.*;

/**
 * Command Pattern — edytor tekstu z historia i undo/redo.
 */
public class CommandDemo {

    // ─── Komenda ──────────────────────────────────────────────────────────────
    interface Command {
        void execute();
        void undo();
        String describe();
    }

    // ─── Receiver — obiekt faktycznie wykonujacy prace ────────────────────────
    static class TextEditor {
        private final StringBuilder text = new StringBuilder();
        private int cursorPos = 0;

        public void insertAt(int pos, String s) {
            text.insert(pos, s);
            cursorPos = pos + s.length();
        }

        public String deleteAt(int pos, int len) {
            String deleted = text.substring(pos, Math.min(pos + len, text.length()));
            text.delete(pos, Math.min(pos + len, text.length()));
            cursorPos = pos;
            return deleted;
        }

        public String getText()        { return text.toString(); }
        public int getCursorPos()      { return cursorPos; }
        public int getLength()         { return text.length(); }

        @Override public String toString() {
            return "\"" + text.toString() + "\" (kursor: " + cursorPos + ")";
        }
    }

    // ─── Konkretne komendy ────────────────────────────────────────────────────
    static class InsertCommand implements Command {
        private final TextEditor editor;
        private final int pos;
        private final String text;

        InsertCommand(TextEditor editor, int pos, String text) {
            this.editor = editor;
            this.pos    = pos;
            this.text   = text;
        }

        @Override public void execute() { editor.insertAt(pos, text); }
        @Override public void undo()    { editor.deleteAt(pos, text.length()); }
        @Override public String describe() {
            return "INSERT(pos=" + pos + ", \"" + text + "\")";
        }
    }

    static class DeleteCommand implements Command {
        private final TextEditor editor;
        private final int pos;
        private final int len;
        private String deletedText;  // zapamietane do undo!

        DeleteCommand(TextEditor editor, int pos, int len) {
            this.editor = editor;
            this.pos    = pos;
            this.len    = len;
        }

        @Override public void execute() {
            deletedText = editor.deleteAt(pos, len);  // zapamietaj przed usunieciem
        }

        @Override public void undo() {
            editor.insertAt(pos, deletedText);        // przywroc usuniete
        }

        @Override public String describe() {
            return "DELETE(pos=" + pos + ", len=" + len +
                    (deletedText != null ? ", \"" + deletedText + "\"" : "") + ")";
        }
    }

    // Makro-komenda — grupuje wiele komend w jedna
    static class MacroCommand implements Command {
        private final List<Command> commands;
        private final String name;

        MacroCommand(String name, Command... cmds) {
            this.name     = name;
            this.commands = List.of(cmds);
        }

        @Override public void execute() { commands.forEach(Command::execute); }
        @Override public void undo() {
            // Undo w odwrotnej kolejnosci!
            List<Command> reversed = new ArrayList<>(commands);
            Collections.reverse(reversed);
            reversed.forEach(Command::undo);
        }
        @Override public String describe() { return "MACRO(" + name + ")"; }
    }

    // ─── Invoker — historia i undo/redo ──────────────────────────────────────
    static class CommandHistory {
        private final Deque<Command> history   = new ArrayDeque<>();
        private final Deque<Command> redoStack = new ArrayDeque<>();

        public void execute(Command cmd) {
            cmd.execute();
            history.push(cmd);
            redoStack.clear();   // redo stack jest kasowany po nowej operacji
            System.out.println("  exec: " + cmd.describe());
        }

        public boolean undo() {
            if (history.isEmpty()) {
                System.out.println("  undo: brak historii");
                return false;
            }
            Command cmd = history.pop();
            cmd.undo();
            redoStack.push(cmd);
            System.out.println("  undo: " + cmd.describe());
            return true;
        }

        public boolean redo() {
            if (redoStack.isEmpty()) {
                System.out.println("  redo: brak dostepnych operacji");
                return false;
            }
            Command cmd = redoStack.pop();
            cmd.execute();
            history.push(cmd);
            System.out.println("  redo: " + cmd.describe());
            return true;
        }

        public int historySize() { return history.size(); }
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Command Pattern — Edytor z Undo/Redo ===\n");

        TextEditor editor = new TextEditor();
        CommandHistory history = new CommandHistory();

        System.out.println("Stan poczatkowy: " + editor);
        System.out.println();

        // Seria operacji
        System.out.println("--- Wpisywanie tekstu ---");
        history.execute(new InsertCommand(editor, 0, "Hello"));
        System.out.println("  Stan: " + editor);

        history.execute(new InsertCommand(editor, 5, ", World"));
        System.out.println("  Stan: " + editor);

        history.execute(new InsertCommand(editor, 12, "!"));
        System.out.println("  Stan: " + editor);

        System.out.println("\n--- Usuwanie ---");
        history.execute(new DeleteCommand(editor, 5, 7));  // usuwa ", World"
        System.out.println("  Stan: " + editor);

        System.out.println("\n--- Undo (x3) ---");
        history.undo();
        System.out.println("  Stan: " + editor);

        history.undo();
        System.out.println("  Stan: " + editor);

        history.undo();
        System.out.println("  Stan: " + editor);

        System.out.println("\n--- Redo (x2) ---");
        history.redo();
        System.out.println("  Stan: " + editor);

        history.redo();
        System.out.println("  Stan: " + editor);

        System.out.println("\n--- Makro-komenda (seria operacji jako jedna) ---");
        TextEditor editor2 = new TextEditor();
        CommandHistory history2 = new CommandHistory();

        Command formatHeading = new MacroCommand("FormatHeading",
            new InsertCommand(editor2, 0, "# "),
            new InsertCommand(editor2, 2, "Java Design Patterns"),
            new InsertCommand(editor2, 22, "\n")
        );

        history2.execute(formatHeading);
        System.out.println("  Stan: " + editor2);

        System.out.println("\n  Undo makra (cofa wszystkie 3 operacje naraz):");
        history2.undo();
        System.out.println("  Stan: " + editor2);

        System.out.println("\n--- Command w kolejce zadan ---");
        System.out.println("  Command moze byc serializowany i wykonany pozniej:");
        System.out.println("  new Thread(() -> cmd.execute()).start(); // async");
        System.out.println("  queue.add(cmd); // job queue");
        System.out.println("  executor.submit(cmd::execute); // ExecutorService");
    }
}

