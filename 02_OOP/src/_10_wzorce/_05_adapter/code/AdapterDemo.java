package _10_wzorce._05_adapter.code;

/**
 * Adapter Pattern — dwa warianty:
 * 1. Object Adapter (kompozycja — preferowany)
 * 2. Class Adapter (dziedziczenie)
 */
public class AdapterDemo {

    // ─── STARY INTERFEJS (Legacy) ─────────────────────────────────────────────
    // Biblioteka zewnetrzna ze starym interfejsem
    static class LegacyXmlParser {
        public String parseXmlToString(String xml) {
            // Symulacja parsowania XML
            return "{parsed: '" + xml.replaceAll("<[^>]+>", "").trim() + "'}";
        }
        public boolean validateXml(String xml) {
            return xml.startsWith("<") && xml.endsWith(">");
        }
    }

    // ─── NOWY INTERFEJS (Target) ──────────────────────────────────────────────
    interface DataParser {
        Object parse(String data);
        boolean validate(String data);
        String getFormat();
    }

    // ─── OBJECT ADAPTER (kompozycja) ─────────────────────────────────────────
    static class XmlToDataParserAdapter implements DataParser {
        private final LegacyXmlParser legacy;  // kompozycja — nie dziedziczenie!

        XmlToDataParserAdapter(LegacyXmlParser legacy) {
            this.legacy = legacy;
        }

        @Override
        public Object parse(String data) {
            // Translacja: nowy format → stary interfejs
            return legacy.parseXmlToString(data);
        }

        @Override
        public boolean validate(String data) {
            return legacy.validateXml(data);
        }

        @Override
        public String getFormat() { return "XML (via Legacy Adapter)"; }
    }

    // ─── NATYWNA IMPLEMENTACJA (dla porownania) ───────────────────────────────
    static class JsonDataParser implements DataParser {
        @Override
        public Object parse(String data) {
            // Symulacja parsowania JSON
            return data.replaceAll("\"(\\w+)\"\\s*:", "$1:");
        }

        @Override
        public boolean validate(String data) {
            return data.startsWith("{") || data.startsWith("[");
        }

        @Override
        public String getFormat() { return "JSON"; }
    }

    // ─── KLIENT — uzywajacy DataParser ───────────────────────────────────────
    static class DataProcessor {
        private final DataParser parser;

        DataProcessor(DataParser parser) {
            this.parser = parser;
        }

        void processData(String data) {
            System.out.println("  Format: " + parser.getFormat());
            if (parser.validate(data)) {
                Object result = parser.parse(data);
                System.out.println("  Wynik parsowania: " + result);
            } else {
                System.out.println("  BLAD: Nieprawidlowe dane dla " + parser.getFormat());
            }
        }
    }

    // ─── ADAPTER dla java.util.Arrays ────────────────────────────────────────
    // Przyklad jak Arrays.asList() jest adapterem tablicy do List
    static void showBuiltinAdapter() {
        System.out.println("\nBuilt-in Adapter w JDK:");

        // String[] to Array (stary interfejs)
        String[] arr = {"java", "scala", "kotlin"};
        System.out.println("  Array: " + java.util.Arrays.toString(arr));

        // Arrays.asList() to ADAPTER — opakowuje tablice jako List
        java.util.List<String> list = java.util.Arrays.asList(arr);
        System.out.println("  List (adapter): " + list);
        System.out.println("  Moge uzyc List.get(1): " + list.get(1));

        // InputStreamReader — adapter InputStream (bajty) → Reader (znaki)
        System.out.println("\n  InputStreamReader to adapter InputStream → Reader:");
        System.out.println("  new InputStreamReader(System.in, StandardCharsets.UTF_8)");
        System.out.println("  Konwertuje strumien bajtow na strumien znakow.");
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Adapter Pattern ===\n");

        // Istniejacy kod legacy
        LegacyXmlParser legacyParser = new LegacyXmlParser();

        // Adapter opakowuje legacy w nowy interfejs
        DataParser xmlAdapter = new XmlToDataParserAdapter(legacyParser);
        DataParser jsonParser  = new JsonDataParser();

        System.out.println("1. Przetwarzanie XML przez Adapter:");
        DataProcessor xmlProcessor = new DataProcessor(xmlAdapter);
        xmlProcessor.processData("<user><name>Alice</name><age>30</age></user>");
        xmlProcessor.processData("{ invalid json }");  // bledne XML

        System.out.println("\n2. Przetwarzanie JSON natively:");
        DataProcessor jsonProcessor = new DataProcessor(jsonParser);
        jsonProcessor.processData("{\"name\":\"Bob\",\"age\":25}");
        jsonProcessor.processData("<invalid xml>");   // bledne JSON

        System.out.println("\n3. Oba parsery przez wspolny interfejs (polimorfizm):");
        DataParser[] parsers = {xmlAdapter, jsonParser};
        String[] data = {
            "<product><id>42</id></product>",
            "{\"product\":{\"id\":42}}"
        };
        for (int i = 0; i < parsers.length; i++) {
            new DataProcessor(parsers[i]).processData(data[i]);
        }

        showBuiltinAdapter();
    }
}

