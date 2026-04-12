package pakiety.t01;

public class NamespaceCollisionDemo {
    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = java.sql.Date.valueOf("2026-04-10");

        System.out.println("java.util.Date: " + utilDate);
        System.out.println("java.sql.Date: " + sqlDate);

        pakiety.t01.account.User accountUser = new pakiety.t01.account.User("anna");
        pakiety.t01.admin.User adminUser = new pakiety.t01.admin.User("root");

        System.out.println("Uzytkownik konta: " + accountUser.username());
        System.out.println("Uzytkownik administracyjny: " + adminUser.username());
    }
}

