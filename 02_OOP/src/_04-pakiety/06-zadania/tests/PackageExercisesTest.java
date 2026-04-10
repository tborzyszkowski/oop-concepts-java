package exercises.tests;

import exercises.solutions.PackageSolutions;

public class PackageExercisesTest {
    public static void main(String[] args) {
        PackageSolutions solutions = new PackageSolutions();

        String teacherInfo = solutions.buildQualifiedMessage();
        String merged = solutions.mergeOrders();

        assertContains(teacherInfo, "dr Nowak", "buildQualifiedMessage powinno zwracac nazwe prowadzacego");
        assertContains(merged, "B-10", "mergeOrders powinno zawierac identyfikator zamowienia billing");
        assertContains(merged, "S-10", "mergeOrders powinno zawierac identyfikator zamowienia shipping");

        System.out.println("OK: PackageExercisesTest");
    }

    private static void assertContains(String actual, String expectedFragment, String message) {
        if (actual == null || !actual.contains(expectedFragment)) {
            throw new AssertionError(message + " | actual='" + actual + "'");
        }
    }
}

