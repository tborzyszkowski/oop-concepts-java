/*
 * Instrukcje sterujące w języku C
 * Porównaj z: Java (examples/) i Python (examples.py)
 *
 * Kompilacja: gcc -o examples examples.c
 * Uruchomienie: ./examples
 */
#include <stdio.h>
#include <stdbool.h>

int main() {

    /* ===== if / else ===== */
    int temperature = 25;
    if (temperature > 30) {
        printf("Upał\n");
    } else if (temperature > 20) {
        printf("Ciepło: %d°C\n", temperature);
    } else {
        printf("Chłodno\n");
    }

    /* ===== Operator ternary ===== */
    int age = 20;
    const char* status = (age >= 18) ? "dorosły" : "małoletni";
    printf("Wiek %d → %s\n", age, status);

    /* ===== switch ===== */
    /* UWAGA: w C switch działa tylko dla int/char/enum */
    /* BRAK switch expression jak w Java 14+! */
    int day = 3;
    switch (day) {
        case 1: printf("Poniedziałek\n"); break;
        case 2: printf("Wtorek\n");       break;
        case 3: printf("Środa\n");        break;
        default: printf("Inny dzień\n");
    }

    /* ===== for ===== */
    /* Identyczne jak Java! (Java przejęła składnię z C) */
    for (int i = 0; i < 5; i++) {
        printf("%d ", i);
    }
    printf("\n");

    /* ===== while ===== */
    int n = 1;
    while (n <= 1024) {
        printf("%d ", n);
        n *= 2;
    }
    printf("\n");

    /* ===== do-while ===== */
    int x = 10;
    do {
        printf("x=%d\n", x);
        x++;
    } while (x < 5);

    /* ===== break / continue ===== */
    for (int i = 0; i < 10; i++) {
        if (i == 5) break;
        if (i % 2 == 0) continue;
        printf("%d ", i);
    }
    printf("\n");

    /* ===== goto — istnieje w C, BRAK w Java i Python! ===== */
    /* (nie zalecane, ale istnieje) */
    int counter = 0;
    loop:
        counter++;
        if (counter < 5) goto loop;
    printf("goto counter = %d\n", counter);

    return 0;
}

/*
 * RÓŻNICE C vs Java:
 *
 * 1. C: brak boolean (używa int 0/1 lub stdbool.h)
 *    Java: boolean jako typ prymitywny
 *
 * 2. C: switch tylko dla typów całkowitych (int, char, enum)
 *    Java: switch dla int, char, String, enum + pattern matching (Java 21)
 *
 * 3. C: brak for-each (brak iteratorów wbudowanych)
 *    Java: for-each dla tablic i kolekcji
 *
 * 4. C: goto istnieje (nie zalecane)
 *    Java: labeled break/continue zamiast goto
 *
 * 5. C: wskaźniki — arytmetyka adresów
 *    Java: referencje — brak arytmetyki
 *
 * 6. C: ręczne zarządzanie pamięcią (malloc/free)
 *    Java: automatyczny GC
 */

