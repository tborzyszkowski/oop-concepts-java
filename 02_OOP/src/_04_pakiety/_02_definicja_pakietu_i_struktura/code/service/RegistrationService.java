package _04_pakiety._02_definicja_pakietu_i_struktura.code.service;

import _04_pakiety._02_definicja_pakietu_i_struktura.code.model.Student;

public class RegistrationService {
    public String register(Student student) {
        return "Zarejestrowano studenta: " + student.indexNumber() + " " + student.name();
    }
}

