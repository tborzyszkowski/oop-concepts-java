package access.outsiders;

import access.core.SecureDocument;
import access.friends.FriendInspector;

public class AccessControlDemo {
    public static void main(String[] args) {
        SecureDocument doc = new SecureDocument();
        System.out.println(doc.publicMessage());

        FriendInspector inspector = new FriendInspector();
        inspector.inspect();

        // doc.packageMessage();   // blad: package-private
        // doc.protectedMessage(); // blad: protected poza pakietem i bez dziedziczenia
    }
}

