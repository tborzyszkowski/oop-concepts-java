package interfaces_special;

/**
 * Interfejs dziedziczący z Vehicle — extends interfejsu.
 * Dodaje własne stałe i metody, nadpisuje default method.
 */
public interface ElectricVehicle extends Vehicle {
    // Własna stała — ukrywa DEFAULT_FUEL z Vehicle
    String DEFAULT_FUEL  = "Elektryczność";
    int    MAX_CHARGE_KW = 250;

    // Dodatkowe metody abstrakcyjne
    int  getBatteryPercent();
    void charge(int kilowatts);

    // Nadpisanie default method z Vehicle — EV ma inny start
    @Override
    default void startEngine() {
        System.out.println(getBrand() + ": Napęd elektryczny aktywny (cicho!)");
    }

    // Nowa default method specyficzna dla EV
    default String batteryStatus() {
        int pct = getBatteryPercent();
        String icon = pct > 50 ? "[OK]" : pct > 20 ? "[LOW]" : "[CRITICAL]";
        return icon + " Bateria: " + pct + "%  (max ładowanie: " + MAX_CHARGE_KW + " kW)";
    }
}

