package _04_pakiety._01_przestrzenie_nazw_i_pakiety.code;

public class NamespaceCollisionDemo {
    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = java.sql.Date.valueOf("2026-04-10");

        System.out.println("java.util.Date: " + utilDate);
        System.out.println("java.sql.Date: " + sqlDate);

        _04_pakiety._01_przestrzenie_nazw_i_pakiety.code.account.User accountUser =
                new _04_pakiety._01_przestrzenie_nazw_i_pakiety.code.account.User("anna");
        _04_pakiety._01_przestrzenie_nazw_i_pakiety.code.admin.User adminUser =
                new _04_pakiety._01_przestrzenie_nazw_i_pakiety.code.admin.User("root");

        System.out.println("Uzytkownik konta: " + accountUser.username());
        System.out.println("Uzytkownik administracyjny: " + adminUser.username());
    }
}

