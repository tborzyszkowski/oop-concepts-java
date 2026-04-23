package _04_pakiety._04_kontrola_dostepu.code.core;

public class SecureDocument {
    private final String secret = "private-secret";
    final String packageInfo = "package-info";
    protected final String inheritedInfo = "protected-info";
    public final String apiInfo = "public-info";

    private String privateMessage() {
        return "private:" + secret;
    }

    String packageMessage() {
        return "package:" + packageInfo;
    }

    protected String protectedMessage() {
        return "protected:" + inheritedInfo;
    }

    public String publicMessage() {
        return "public:" + apiInfo + " | " + privateMessage();
    }
}

