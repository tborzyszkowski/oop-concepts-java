package _01_introduction.object_lifecycle.tests;

import _01_introduction.object_lifecycle.copies.CopyDemo;
import _01_introduction.object_lifecycle.copies.CopyDemo.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy kopii płytkich i głębokich.
 * Precyzyjnie weryfikują zachowanie współdzielenia referencji.
 */
@DisplayName("CopyDemo - kopia platka vs gleboka")
class CopyTest {

    @Test
    @DisplayName("Kopia platka wspoldzieli obiekt Address")
    void shallowCopySharesAddressReference() {
        CopyDemo.Address addr = new CopyDemo.Address("Kwiatowa 5", "Warszawa");
        CopyDemo.PersonShallow original = new CopyDemo.PersonShallow(
                "Anna", 30, addr, new ArrayList<>(List.of("hobby1")));

        CopyDemo.PersonShallow copy = original.shallowCopy();

        assertSame(original.address, copy.address,
                "Kopia platka powinna wspoldzielic ten sam obiekt Address");
    }

    @Test
    @DisplayName("Kopia platka wspoldzieli liste hobbies")
    void shallowCopySharesHobbiesList() {
        CopyDemo.Address addr = new CopyDemo.Address("Ul. 1", "Krakow");
        List<String> hobbies = new ArrayList<>(List.of("a", "b"));
        CopyDemo.PersonShallow original = new CopyDemo.PersonShallow("Jan", 25, addr, hobbies);

        CopyDemo.PersonShallow copy = original.shallowCopy();

        assertSame(original.hobbies, copy.hobbies,
                "Kopia platka powinna wspoldzielic te sama liste");
    }

    @Test
    @DisplayName("Kopia platka: zmiana address przez kope wplywa na oryginal")
    void shallowCopyMutationAffectsOriginal() {
        CopyDemo.Address addr = new CopyDemo.Address("Ul. A", "Warszawa");
        CopyDemo.PersonShallow original = new CopyDemo.PersonShallow(
                "Anna", 30, addr, new ArrayList<>());
        CopyDemo.PersonShallow copy = original.shallowCopy();

        copy.address.city = "Krakow"; // modyfikacja przez kope

        assertEquals("Krakow", original.address.city,
                "Zmiana przez kope powinna wplynac na oryginal (shared ref)");
    }

    @Test
    @DisplayName("Kopia gleboka NIE wspoldzieli obiektu Address")
    void deepCopyDoesNotShareAddress() {
        CopyDemo.Address addr = new CopyDemo.Address("Sloneczna 10", "Gdansk");
        CopyDemo.PersonDeep original = new CopyDemo.PersonDeep(
                "Kamil", 35, addr, new ArrayList<>(List.of("muzyka")));

        CopyDemo.PersonDeep copy = original.deepCopy();

        assertNotSame(original.address, copy.address,
                "Kopia gleboka powinna miec wlasny obiekt Address");
    }

    @Test
    @DisplayName("Kopia gleboka NIE wspoldzieli listy hobbies")
    void deepCopyDoesNotShareHobbies() {
        CopyDemo.Address addr = new CopyDemo.Address("Ul. B", "Poznan");
        CopyDemo.PersonDeep original = new CopyDemo.PersonDeep(
                "Ewa", 28, addr, new ArrayList<>(List.of("taniec")));

        CopyDemo.PersonDeep copy = original.deepCopy();

        assertNotSame(original.hobbies, copy.hobbies,
                "Kopia gleboka powinna miec wlasna liste");
    }

    @Test
    @DisplayName("Kopia gleboka: zmiana address przez kope NIE wplywa na oryginal")
    void deepCopyMutationDoesNotAffectOriginal() {
        CopyDemo.Address addr = new CopyDemo.Address("Ul. C", "Wroclaw");
        CopyDemo.PersonDeep original = new CopyDemo.PersonDeep(
                "Piotr", 40, addr, new ArrayList<>());
        CopyDemo.PersonDeep copy = original.deepCopy();

        copy.address.city = "Lublin";

        assertEquals("Wroclaw", original.address.city,
                "Oryginal powinien pozostac niezmieniony po modyfikacji kopii");
    }

    @Test
    @DisplayName("Kopia gleboka: dodanie do listy kopii NIE wplywa na oryginal")
    void deepCopyListMutationDoesNotAffectOriginal() {
        CopyDemo.Address addr = new CopyDemo.Address("Ul. D", "Lodz");
        List<String> hobbies = new ArrayList<>(List.of("bieganie"));
        CopyDemo.PersonDeep original = new CopyDemo.PersonDeep("Marta", 33, addr, hobbies);
        CopyDemo.PersonDeep copy = original.deepCopy();

        copy.hobbies.add("plywanie");

        assertEquals(1, original.hobbies.size(),
                "Lista oryginalu powinna miec wciaz 1 element");
        assertEquals(2, copy.hobbies.size());
    }

    @Test
    @DisplayName("Kopia gleboka zachowuje te same wartosci danych")
    void deepCopyPreservesDataValues() {
        CopyDemo.Address addr = new CopyDemo.Address("Glowna 1", "Katowice");
        List<String> hobbies = new ArrayList<>(List.of("sport", "ksiazki"));
        CopyDemo.PersonDeep original = new CopyDemo.PersonDeep("Tomek", 45, addr, hobbies);

        CopyDemo.PersonDeep copy = original.deepCopy();

        assertEquals(original.name,           copy.name);
        assertEquals(original.age,            copy.age);
        assertEquals(original.address.street, copy.address.street);
        assertEquals(original.address.city,   copy.address.city);
        assertEquals(original.hobbies,        copy.hobbies); // te same wartosci
    }
}

