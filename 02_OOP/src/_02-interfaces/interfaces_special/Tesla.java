package interfaces_special;

/** Konkretna implementacja ElectricVehicle (i przez dziedziczenie — Vehicle). */
public class Tesla implements ElectricVehicle {
    private final String model;
    private int speed   = 0;
    private int battery = 80;

    public Tesla(String model) { this.model = model; }

    @Override public String getBrand()          { return "Tesla " + model; }
    @Override public int    getCurrentSpeed()   { return speed; }
    @Override public int    getBatteryPercent() { return battery; }

    @Override
    public void charge(int kilowatts) {
        int added = Math.min(kilowatts / 10, 100 - battery);
        battery += added;
        System.out.printf("%s: ładowanie %d kW -> bateria %d%%%n", getBrand(), kilowatts, battery);
    }

    public void accelerate(int kmh) {
        speed = Math.min(speed + kmh, Vehicle.MAX_SPEED_KMH);
        System.out.println(getBrand() + ": prędkość " + speed + " km/h");
    }
}

