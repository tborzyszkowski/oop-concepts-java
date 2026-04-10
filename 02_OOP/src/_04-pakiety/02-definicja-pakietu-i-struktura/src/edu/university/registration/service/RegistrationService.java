package edu.university.registration.service;

import edu.university.registration.model.Student;

public class RegistrationService {
    public String register(Student student) {
        return "Zarejestrowano studenta: " + student.indexNumber() + " " + student.name();
    }
}

