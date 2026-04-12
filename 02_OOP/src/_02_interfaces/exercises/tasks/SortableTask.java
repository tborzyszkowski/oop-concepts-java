package _02_interfaces.exercises.tasks;

import java.util.*;

/**
 * ZADANIE 2 (⭐⭐) — Comparable i Comparator
 *
 * Klasa Student powinna implementować Comparable<Student>.
 * Naturalne porządkowanie: malejąco po średniej ocen.
 *
 * Oczekiwany output (lista 5 studentów):
 *   Naturalne (srednia malejaco):
 *     1. Nowak Anna       4.8
 *     2. Kowalski Jan     4.5
 *     ...
 *   Alfabetycznie (po nazwisku):
 *     Kowalski Jan     4.5
 *     ...
 */
public class SortableTask {

    // TODO: Klasa Student implements Comparable<Student>
    //   - pola: String firstName, String lastName, double gpa
    //   - compareTo: malejaco po gpa; przy równej gpa — po nazwisku A-Z
    //   - toString: String.format("%-12s %-10s %.1f", lastName, firstName, gpa)

    public static void main(String[] args) {
        // TODO: Utwórz listę co najmniej 5 studentów
        // TODO: Collections.sort(lista) — naturalne (po gpa malejąco)
        // TODO: lista.sort(Comparator po nazwisku) — alfabetycznie
        // TODO: TreeSet<Student> — automatyczne sortowanie naturalne
        System.out.println("TODO: zaimplementuj zadanie");
    }
}

