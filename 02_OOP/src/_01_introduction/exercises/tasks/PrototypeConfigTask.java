package _01_introduction.exercises.tasks;

/**
 * ZADANIE 3: Wzorzec Prototype — konfiguracja serwera
 * =====================================================
 * Poziom: Średni/Trudny — oparty na module "object_lifecycle/copies"
 *
 * POLECENIE:
 *   Zaimplementuj system konfiguracji serwera przy użyciu wzorca Prototype.
 *   Serwery webowe, bazodanowe i cache'owe różnią się konfiguracją,
 *   ale każdego rodzaju może być wiele instancji (klonów).
 *
 * WYMAGANIA:
 *
 *   Interfejs ServerConfig (implementuje Prototype<ServerConfig>):
 *     - copy() : ServerConfig    (głęboka kopia)
 *     - getType() : String
 *     - withPort(int port) : ServerConfig   (fluent — zwraca this)
 *     - withHost(String host) : ServerConfig
 *     - withName(String name) : ServerConfig
 *     - start() : void   (drukuje "Starting [type] server [name] at host:port")
 *
 *   Klasa WebServerConfig implements ServerConfig:
 *     - Dodatkowe pola: maxConnections (int), sslEnabled (boolean)
 *     - withMaxConnections(int), withSsl(boolean)
 *
 *   Klasa DatabaseConfig implements ServerConfig:
 *     - Dodatkowe pola: dbName (String), poolSize (int)
 *     - withDatabase(String), withPoolSize(int)
 *
 *   Klasa ConfigRegistry:
 *     - register(String key, ServerConfig prototype)
 *     - ServerConfig spawn(String key)
 *     - Rzuca IllegalArgumentException dla nieznanego klucza
 *
 * ZACHOWANIE:
 *   ConfigRegistry reg = new ConfigRegistry();
 *   reg.register("web",  new WebServerConfig("web", "localhost", 8080));
 *   reg.register("db",   new DatabaseConfig("db",  "localhost", 5432));
 *
 *   ServerConfig s1 = reg.spawn("web").withPort(8081).withName("web-1");
 *   ServerConfig s2 = reg.spawn("web").withPort(8082).withName("web-2");
 *   s1.start(); // Starting web server web-1 at localhost:8081
 *   s2.start(); // Starting web server web-2 at localhost:8082
 *
 * TESTY (w ConfigRegistryTest.java):
 *   - spawn tworzy niezalezny obiekt (nie modyfikuje prototypu)
 *   - Każdy spawn to nowy obiekt (assertNotSame)
 *   - Walidacja portów (1-65535)
 *   - Rzucanie wyjątku dla nieznanego klucza
 */
public class PrototypeConfigTask {

    // TODO: Zaimplementuj interfejs ServerConfig i klasy

    interface ServerConfig {
        // TODO
    }

    static class WebServerConfig implements ServerConfig {
        // TODO
    }

    static class DatabaseConfig implements ServerConfig {
        // TODO
    }

    static class ConfigRegistry {
        // TODO
    }

    public static void main(String[] args) {
        // TODO: Demonstracja działania
    }
}

