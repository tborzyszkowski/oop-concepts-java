package _04_pakiety._04_kontrola_dostepu.code.outsiders;

import _04_pakiety._04_kontrola_dostepu.code.core.SecureDocument;
import _04_pakiety._04_kontrola_dostepu.code.friends.FriendInspector;

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

