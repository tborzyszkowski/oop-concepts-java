package introduction.tdd.tests;

import introduction.tdd.src.Stack;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy dla Stack<T> pisane metodą TDD.
 *
 * Każda sekcja pokazuje jeden cykl Red → Green → Refactor.
 * Kolejność testów odzwierciedla porządek ich powstawania w TDD.
 *
 * Konwencja nazewnicza: methodUnderTest_StateUnderTest_ExpectedBehavior
 */
@DisplayName("Stack<T> - TDD Cykl Red-Green-Refactor")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StackTest {

    private Stack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new Stack<>();
    }

    // ======================================================
    // CYKL 1: Stos jest pusty po utworzeniu
    // ======================================================

    @Test @Order(1)
    @DisplayName("[Cykl 1] Nowy stos jest pusty")
    void newStack_IsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test @Order(2)
    @DisplayName("[Cykl 1] Nowy stos ma rozmiar 0")
    void newStack_HasSizeZero() {
        assertEquals(0, stack.size());
    }

    // ======================================================
    // CYKL 2: push dodaje element
    // ======================================================

    @Test @Order(3)
    @DisplayName("[Cykl 2] Po push stos nie jest pusty")
    void afterPush_StackIsNotEmpty() {
        stack.push(42);
        assertFalse(stack.isEmpty());
    }

    @Test @Order(4)
    @DisplayName("[Cykl 2] Po push rozmiar wynosi 1")
    void afterOnePush_SizeIsOne() {
        stack.push(10);
        assertEquals(1, stack.size());
    }

    @ParameterizedTest(name = "push {0} elementow -> size = {0}")
    @ValueSource(ints = {1, 3, 5, 10})
    @Order(5)
    @DisplayName("[Cykl 2] Rozmiar odpowiada liczbie push")
    void multiplePushes_SizeMatchesPushCount(int count) {
        for (int i = 0; i < count; i++) stack.push(i);
        assertEquals(count, stack.size());
    }

    // ======================================================
    // CYKL 3: peek zwraca wierzcholek bez usuwania
    // ======================================================

    @Test @Order(6)
    @DisplayName("[Cykl 3] peek zwraca ostatnio dodany element")
    void peek_ReturnsLastPushedElement() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.peek());
    }

    @Test @Order(7)
    @DisplayName("[Cykl 3] peek nie zmienia rozmiaru stosu")
    void peek_DoesNotChangeSize() {
        stack.push(99);
        stack.peek();
        assertEquals(1, stack.size());
    }

    @Test @Order(8)
    @DisplayName("[Cykl 3] peek na pustym stosie rzuca wyjatek")
    void peek_OnEmptyStack_ThrowsException() {
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    // ======================================================
    // CYKL 4: pop usuwa i zwraca wierzcholek
    // ======================================================

    @Test @Order(9)
    @DisplayName("[Cykl 4] pop zwraca ostatnio dodany element")
    void pop_ReturnsLastPushedElement() {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop());
    }

    @Test @Order(10)
    @DisplayName("[Cykl 4] pop zmniejsza rozmiar o 1")
    void pop_DecreasesSizeByOne() {
        stack.push(1);
        stack.push(2);
        stack.pop();
        assertEquals(1, stack.size());
    }

    @Test @Order(11)
    @DisplayName("[Cykl 4] pop na pustym stosie rzuca wyjatek")
    void pop_OnEmptyStack_ThrowsException() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test @Order(12)
    @DisplayName("[Cykl 4] LIFO: elementy zdejmowane w odwrotnej kolejnosci")
    void pop_RespectsLIFOOrder() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    // ======================================================
    // CYKL 5: pojemnosc automatycznie rosnaca
    // ======================================================

    @Test @Order(13)
    @DisplayName("[Cykl 5] Stos obsluguje wiele elementow (>16 domyslna pojemnosc)")
    void push_ManyElements_StackGrowsAutomatically() {
        for (int i = 0; i < 100; i++) stack.push(i);
        assertEquals(100, stack.size());
        assertEquals(99, stack.peek());
    }

    @Test @Order(14)
    @DisplayName("[Cykl 5] Po pop() do pustego stos jest pusty")
    void afterAllPops_StackIsEmpty() {
        stack.push(5);
        stack.push(10);
        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // ======================================================
    // CYKL 6: generyczny typ
    // ======================================================

    @Test @Order(15)
    @DisplayName("[Cykl 6] Stack dziala ze Stringami")
    void stack_WorksWithStrings() {
        Stack<String> strStack = new Stack<>();
        strStack.push("A");
        strStack.push("B");
        strStack.push("C");

        assertEquals("C", strStack.pop());
        assertEquals("B", strStack.pop());
        assertEquals("A", strStack.pop());
    }

    @Test @Order(16)
    @DisplayName("[Cykl 6] Stack dziala z obiektami")
    void stack_WorksWithObjects() {
        Stack<double[]> doubleStack = new Stack<>();
        double[] arr = {1.0, 2.0};
        doubleStack.push(arr);
        assertSame(arr, doubleStack.pop());
    }
}

