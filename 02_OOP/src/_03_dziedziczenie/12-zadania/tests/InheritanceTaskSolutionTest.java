package inheritance.t12.tests;

import inheritance.t12.solutions.InheritanceTaskSolution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InheritanceTaskSolutionTest {
    @Test
    void smokeTest() {
        assertEquals("PDF", InheritanceTaskSolution.export(new InheritanceTaskSolution.PdfReport()));
    }
}


