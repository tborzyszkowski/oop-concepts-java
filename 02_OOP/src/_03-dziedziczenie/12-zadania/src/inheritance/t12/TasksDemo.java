package inheritance.t12;

interface Formatter {
    String format(String input);
}

class UpperCaseFormatter implements Formatter {
    public String format(String input) {
        return input.toUpperCase();
    }
}

public class TasksDemo {
    public static void main(String[] args) {
        Formatter formatter = new UpperCaseFormatter();
        System.out.println(formatter.format("zadanie"));
    }
}

