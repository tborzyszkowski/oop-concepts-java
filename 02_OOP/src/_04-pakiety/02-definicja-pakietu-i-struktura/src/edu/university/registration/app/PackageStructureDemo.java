package edu.university.registration.app;

import edu.university.registration.model.Student;
import edu.university.registration.service.RegistrationService;

public class PackageStructureDemo {
    public static void main(String[] args) {
        Student student = new Student("s12345", "Jan Kowalski");
        RegistrationService service = new RegistrationService();
        System.out.println(service.register(student));
    }
}

