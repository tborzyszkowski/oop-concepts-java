package _10_wzorce._07_facade.code;

/**
 * Facade Pattern — uproszczony interfejs do skomplikowanego podsystemu kina domowego.
 */
public class FacadeDemo {

    // ─── Podsystem — wiele skomplikowanych klas ───────────────────────────────
    static class DvdPlayer {
        private String disc;
        public void on()                    { System.out.println("  [DVD] Wlaczony"); }
        public void load(String disc)       { this.disc = disc; System.out.println("  [DVD] Zaladowano: " + disc); }
        public void play()                  { System.out.println("  [DVD] Odtwarza: " + disc); }
        public void pause()                 { System.out.println("  [DVD] Pauza"); }
        public void stop()                  { System.out.println("  [DVD] Stop"); }
        public void off()                   { System.out.println("  [DVD] Wylaczony"); }
        public String getCurrentDisc()      { return disc; }
    }

    static class Projector {
        private String inputSource;
        public void on()                    { System.out.println("  [PROJEKTOR] Wlaczony"); }
        public void setInput(String source) { this.inputSource = source; System.out.println("  [PROJEKTOR] Zrodlo: " + source); }
        public void wideScreenMode()        { System.out.println("  [PROJEKTOR] Tryb 16:9"); }
        public void off()                   { System.out.println("  [PROJEKTOR] Wylaczony"); }
    }

    static class Amplifier {
        private int volume;
        public void on()                    { System.out.println("  [WZMACNIACZ] Wlaczony"); }
        public void setInput(String src)    { System.out.println("  [WZMACNIACZ] Wejscie: " + src); }
        public void setSurroundSound()      { System.out.println("  [WZMACNIACZ] Surround 5.1"); }
        public void setVolume(int v)        { this.volume = v; System.out.println("  [WZMACNIACZ] Glosnosc: " + v); }
        public void off()                   { System.out.println("  [WZMACNIACZ] Wylaczony"); }
        public int getVolume()              { return volume; }
    }

    static class TheaterLights {
        private int level;
        public void dim(int level)          { this.level = level; System.out.println("  [OSWIETLENIE] Sciemniono do " + level + "%"); }
        public void brighten()              { this.level = 100; System.out.println("  [OSWIETLENIE] Pelna jasnosc"); }
        public int getLevel()               { return level; }
    }

    static class StreamingService {
        public void on()                    { System.out.println("  [STREAMING] Polaczono z siecią"); }
        public void loadTitle(String title) { System.out.println("  [STREAMING] Zaladowano: " + title); }
        public void startStream()           { System.out.println("  [STREAMING] Streaming uruchomiony"); }
        public void off()                   { System.out.println("  [STREAMING] Rozlaczono"); }
    }

    // ─── FACADE ───────────────────────────────────────────────────────────────
    static class HomeTheaterFacade {
        private final DvdPlayer       dvd;
        private final Projector       projector;
        private final Amplifier       amplifier;
        private final TheaterLights   lights;
        private final StreamingService streaming;

        private boolean movieMode = false;
        private int savedVolume = 5;

        HomeTheaterFacade() {
            this.dvd       = new DvdPlayer();
            this.projector = new Projector();
            this.amplifier = new Amplifier();
            this.lights    = new TheaterLights();
            this.streaming = new StreamingService();
        }

        // Prosta metoda zamiast 10+ wywolan
        public void watchDvd(String disc) {
            System.out.println("  → Przygotowanie kina na DVD...");
            lights.dim(10);
            projector.on();
            projector.wideScreenMode();
            projector.setInput("DVD");
            amplifier.on();
            amplifier.setInput("DVD");
            amplifier.setSurroundSound();
            amplifier.setVolume(8);
            dvd.on();
            dvd.load(disc);
            dvd.play();
            movieMode = true;
        }

        public void watchStreaming(String title) {
            System.out.println("  → Przygotowanie kina na streaming...");
            lights.dim(5);
            projector.on();
            projector.wideScreenMode();
            projector.setInput("HDMI-Streaming");
            amplifier.on();
            amplifier.setInput("HDMI");
            amplifier.setSurroundSound();
            amplifier.setVolume(7);
            streaming.on();
            streaming.loadTitle(title);
            streaming.startStream();
            movieMode = true;
        }

        public void pauseMovie() {
            if (movieMode) {
                System.out.println("  → Pauza...");
                lights.dim(40);
                dvd.pause();
            }
        }

        public void endMovie() {
            System.out.println("  → Koniec seansu. Wylaczam system...");
            lights.brighten();
            dvd.stop();
            dvd.off();
            streaming.off();
            amplifier.off();
            projector.off();
            movieMode = false;
        }

        public void setVolume(int v) {
            amplifier.setVolume(v);
        }
    }

    // ─── MAIN ────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Facade Pattern ===\n");

        HomeTheaterFacade theater = new HomeTheaterFacade();

        System.out.println("=== Seans DVD ===");
        theater.watchDvd("Inception (2010)");

        System.out.println("\n=== Po 90 minutach — przerwa ===");
        theater.pauseMovie();

        System.out.println("\n=== Koniec przerwy — reszta seansu ===");
        theater.setVolume(9);

        System.out.println("\n=== Koniec seansu ===");
        theater.endMovie();

        System.out.println("\n=== Streaming zamiast DVD ===");
        theater.watchStreaming("The Matrix (1999)");
        theater.endMovie();

        System.out.println("\n=== Bez Facade — co musialbys wpisac recznie ===");
        System.out.println("  dvd.on(); projector.on(); projector.wideScreenMode();");
        System.out.println("  projector.setInput(\"DVD\"); amplifier.on();");
        System.out.println("  amplifier.setInput(\"DVD\"); amplifier.setSurroundSound();");
        System.out.println("  amplifier.setVolume(8); lights.dim(10);");
        System.out.println("  dvd.load(disc); dvd.play();");
        System.out.println("  → 10+ linii zamiast theater.watchDvd(disc)");
    }
}

