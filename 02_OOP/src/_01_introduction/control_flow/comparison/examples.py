# Instrukcje sterujące w Pythonie
# Porównaj z: Java (examples/) i C (examples.c)
#
# Uruchomienie: python examples.py

# ===== if / elif / else =====
# Python: elif (nie else if)
# Python: wcięcia zamiast nawiasów klamrowych!
temperature = 25

if temperature > 30:
    print("Upał")
elif temperature > 20:
    print(f"Ciepło: {temperature}°C")
else:
    print("Chłodno")

# ===== Operator ternary (wyrażenie warunkowe) =====
# Python: wartość_tak if warunek else wartość_nie (odwrócona kolejność!)
age = 20
status = "dorosły" if age >= 18 else "małoletni"
print(f"Wiek {age} → {status}")

# ===== match/case (Python 3.10+) — odpowiednik switch =====
# Python: match..case zamiast switch
day = 3
match day:
    case 1: print("Poniedziałek")
    case 2: print("Wtorek")
    case 3: print("Środa")
    case _: print("Inny dzień")  # _ to wildcard (default)

# ===== for — iteracja po zakresie =====
# Python: range(n) zamiast int i=0; i<n; i++
for i in range(5):
    print(i, end=" ")
print()

# for po liście (jak Java for-each)
fruits = ["Jabłko", "Banan", "Wiśnia"]
for fruit in fruits:
    print(f"  - {fruit}")

# enumerate — indeks + wartość
for idx, fruit in enumerate(fruits):
    print(f"  [{idx}] {fruit}")

# ===== while =====
n = 1
while n <= 1024:
    print(n, end=" ")
    n *= 2
print()

# ===== Brak do-while w Python! =====
# Ekwiwalent:
x = 10
while True:
    print(f"x={x}")
    x += 1
    if x >= 5:
        break

# ===== break / continue =====
for i in range(10):
    if i == 5:
        break
    if i % 2 == 0:
        continue
    print(i, end=" ")
print()

# ===== else w pętli (unikalna cecha Pythona!) =====
# else wykonuje się gdy pętla zakończyła się normalnie (bez break)
for i in range(5):
    print(i, end=" ")
else:
    print("\nPętla zakończona normalnie (bez break)")

# ===== Comprehensions (brak w Java/C) =====
squares = [x**2 for x in range(10)]
print("Kwadraty:", squares)

even = [x for x in range(20) if x % 2 == 0]
print("Parzyste:", even)

# RÓŻNICE Python vs Java:
#
# 1. Python: wcięcia definiują bloki (nie {})
#    Java: nawiasy klamrowe {}
#
# 2. Python: range() w for
#    Java: int i=0; i<n; i++
#
# 3. Python: brak do-while (emulacja przez while True + break)
#    Java: do { } while(...)
#
# 4. Python: match/case (Python 3.10) z pattern matching
#    Java: switch expression (Java 14) + pattern matching (Java 21)
#
# 5. Python: else po pętli
#    Java: brak odpowiednika
#
# 6. Python: list comprehensions [x for x in ...]
#    Java: Stream API (stream().filter().map().collect())
#
# 7. Python: brak goto (jak Java)
#    Java: labeled break/continue
#
# 8. Python: dynamiczne typowanie (brak deklaracji typu)
#    Java: statyczne typowanie (int i = 0)

