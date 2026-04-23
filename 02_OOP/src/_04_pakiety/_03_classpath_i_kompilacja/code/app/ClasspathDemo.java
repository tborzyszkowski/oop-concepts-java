package _04_pakiety._03_classpath_i_kompilacja.code.app;

import _04_pakiety._03_classpath_i_kompilacja.code.api.GreetingService;
import _04_pakiety._03_classpath_i_kompilacja.code.impl.PolishGreetingService;

public class ClasspathDemo {
    public static void main(String[] args) {
        GreetingService service = new PolishGreetingService();
        System.out.println(service.greet("Studenci"));
    }
}

