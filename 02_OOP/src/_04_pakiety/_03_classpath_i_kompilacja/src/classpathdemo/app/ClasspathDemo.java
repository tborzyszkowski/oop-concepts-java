package classpathdemo.app;

import classpathdemo.api.GreetingService;
import classpathdemo.impl.PolishGreetingService;

public class ClasspathDemo {
    public static void main(String[] args) {
        GreetingService service = new PolishGreetingService();
        System.out.println(service.greet("Studenci"));
    }
}

