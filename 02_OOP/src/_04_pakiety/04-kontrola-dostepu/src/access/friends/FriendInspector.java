package access.friends;

import access.core.SecureDocument;

/**
 * Podklasa z innego pakietu — widzi protected i public, ale nie package-private.
 */
public class FriendInspector extends SecureDocument {

    public void inspect() {
        System.out.println("protected (via dziedziczenie): " + protectedMessage());
        System.out.println("public:                        " + publicMessage());
        // packageMessage()  -> błąd: package-private, inny pakiet
    }

    public static void main(String[] args) {
        new FriendInspector().inspect();
    }
}

