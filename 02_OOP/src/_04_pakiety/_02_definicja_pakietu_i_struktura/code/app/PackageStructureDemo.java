package _04_pakiety._02_definicja_pakietu_i_struktura.code.app;

import _04_pakiety._02_definicja_pakietu_i_struktura.code.model.Student;
import _04_pakiety._02_definicja_pakietu_i_struktura.code.service.RegistrationService;

public class PackageStructureDemo {
    public static void main(String[] args) {
        Student student = new Student("s12345", "Jan Kowalski");
        RegistrationService service = new RegistrationService();
        System.out.println(service.register(student));
    }
}

