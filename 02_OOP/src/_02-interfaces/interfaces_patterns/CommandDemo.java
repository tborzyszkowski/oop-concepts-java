package interfaces_patterns;

import java.util.ArrayDeque;
import java.util.Deque;

/** Command — interfejs polecenia z obsługą undo */
interface Command {
    void execute();
    void undo();
    String description();
}

/** Receiver — obiekt wykonujący faktyczne operacje */
class TextEditor {
    private final StringBuilder text = new StringBuilder();

    public void insertText(String s, int pos) {
        text.insert(Math.min(pos, text.length()), s);
    }
    public void deleteText(int from, int length) {
        int to = Math.min(from + length, text.length());
        text.delete(from, to);
    }
    public String getText() { return text.toString(); }
}

/** Konkretne Command — wstawianie tekstu */
class TypeCommand implements Command {
    private final TextEditor editor;
    private final String text;
    private final int position;

    TypeCommand(TextEditor editor, String text, int position) {
        this.editor = editor; this.text = text; this.position = position;
    }
    @Override public void execute()  { editor.insertText(text, position); }
    @Override public void undo()     { editor.deleteText(position, text.length()); }
    @Override public String description() { return "Type(\"" + text + "\", pos=" + position + ")"; }
}

/** Konkretne Command — usuwanie tekstu */
class DeleteCommand implements Command {
    private final TextEditor editor;
    private final int from, length;
    private String deletedText = "";

    DeleteCommand(TextEditor editor, int from, int length) {
        this.editor = editor; this.from = from; this.length = length;
    }
    @Override public void execute() {
        int to = Math.min(from + length, editor.getText().length());
        deletedText = editor.getText().substring(from, to);
        editor.deleteText(from, length);
    }
    @Override public void undo()     { editor.insertText(deletedText, from); }
    @Override public String description() { return "Delete(from=" + from + ", len=" + length + ")"; }
}

/** Invoker — zarządza historią poleceń */
class CommandHistory {
    private final Deque<Command> history = new ArrayDeque<>();

    public void execute(Command cmd) {
        cmd.execute();
        history.push(cmd);
        System.out.println("  execute: " + cmd.description());
    }

    public void undo() {
        if (history.isEmpty()) { System.out.println("  Nic do cofniecia"); return; }
        Command cmd = history.pop();
        cmd.undo();
        System.out.println("  undo: " + cmd.description());
    }
}

/**
 * WZORZEC COMMAND
 * Hermetyzacja operacji jako obiekt — pozwala na undo/redo, kolejkowanie.
 *
 * Uruchomienie (z katalogu _02-interfaces):
 *   javac -d out interfaces_patterns/CommandDemo.java
 *   java -cp out interfaces_patterns.CommandDemo
 */
public class CommandDemo {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        CommandHistory history = new CommandHistory();

        System.out.println("=== Edytor tekstu z undo ===");
        history.execute(new TypeCommand(editor, "Hello", 0));
        System.out.println("  Tekst: \"" + editor.getText() + "\"");

        history.execute(new TypeCommand(editor, " World", 5));
        System.out.println("  Tekst: \"" + editor.getText() + "\"");

        history.execute(new DeleteCommand(editor, 5, 6));
        System.out.println("  Tekst: \"" + editor.getText() + "\"");

        System.out.println("\n=== Cofanie (undo) ===");
        history.undo();
        System.out.println("  Tekst: \"" + editor.getText() + "\"");

        history.undo();
        System.out.println("  Tekst: \"" + editor.getText() + "\"");

        history.undo();
        System.out.println("  Tekst: \"" + editor.getText() + "\"");

        history.undo(); // nic do cofniecia
    }
}

