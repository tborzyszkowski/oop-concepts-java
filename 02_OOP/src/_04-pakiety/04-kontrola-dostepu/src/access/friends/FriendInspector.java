package access.friends;

import access.core.SecureDocument;

public class FriendInspector extends SecureDocument {
    public void inspect() {
        System.out.println("dziedziczenie: " + protectedMessage());
        System.out.println("public: " + publicMessage());
    }
}

