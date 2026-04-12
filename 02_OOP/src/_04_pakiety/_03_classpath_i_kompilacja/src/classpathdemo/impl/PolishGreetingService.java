package classpathdemo.impl;

import classpathdemo.api.GreetingService;

public class PolishGreetingService implements GreetingService {
    @Override
    public String greet(String name) {
        return "Czesc, " + name + "!";
    }
}

