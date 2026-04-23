package _04_pakiety._04_kontrola_dostepu.code.core;

/**
 * Demonstruje dostęp package-private z klasy w tym samym pakiecie.
 * Porównaj z access.outsiders.AccessControlDemo — klient z zewnątrz.
 */
public class SamePackageDemo {

    public static void main(String[] args) {
        SecureDocument doc = new SecureDocument();

        // Pola - ten sam pakiet widzi package-private i wyższe
        System.out.println("apiInfo (public):     " + doc.apiInfo);
        System.out.println("inheritedInfo (prot): " + doc.inheritedInfo);
        System.out.println("packageInfo (pkg):    " + doc.packageInfo);
        // doc.secret  -> błąd: private

        System.out.println();

        // Metody - ten sam pakiet
        System.out.println("publicMessage():    " + doc.publicMessage());
        System.out.println("protectedMessage(): " + doc.protectedMessage());
        System.out.println("packageMessage():   " + doc.packageMessage());
        // doc.privateMessage() -> błąd: private
    }
}

