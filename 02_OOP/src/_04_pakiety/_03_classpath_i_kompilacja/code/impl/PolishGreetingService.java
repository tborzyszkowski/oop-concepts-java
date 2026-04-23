package _04_pakiety._03_classpath_i_kompilacja.code.impl;

import _04_pakiety._03_classpath_i_kompilacja.code.api.GreetingService;

public class PolishGreetingService implements GreetingService {
    @Override
    public String greet(String name) {
        return "Czesc, " + name + "!";
    }
}

