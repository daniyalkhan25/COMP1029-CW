public class RestrictedSpots {

    private int spotID;
    private String spotName;
    private double spotArea;
    private double usableArea;
    private int permittedAvgTime;
    private int maxCapacity;

    // 1 m² per person (social distancing grid model)
    public static final double DISTANCING_AREA = 1.0;

    /**
     * @param usableRatio fraction of total area available for occupancy (0.0–1.0)
     */
    public RestrictedSpots(int spotID, String spotName,
            double spotArea, double usableRatio,
            int permittedAvgTime) {
        this.spotID = spotID;
        this.spotName = spotName;
        this.spotArea = spotArea;
        this.usableArea = spotArea * usableRatio;
        this.permittedAvgTime = permittedAvgTime;
        this.maxCapacity = calculateMaxCapacity();
    }

    // Ensures at least 1 slot is always available
    private int calculateMaxCapacity() {
        int cap = (int) Math.floor(usableArea / DISTANCING_AREA);
        return Math.max(cap, 1);
    }

    public int getSpotID() {
        return spotID;
    }

    public String getSpotName() {
        return spotName;
    }

    public double getSpotArea() {
        return spotArea;
    }

    public double getUsableArea() {
        return usableArea;
    }

    public int getPermittedAvgTime() {
        return permittedAvgTime;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setUsableArea(double usableArea) {
        this.usableArea = usableArea;
        this.maxCapacity = calculateMaxCapacity();
    }

    public void displaySpotInfo() {
        System.out.println("  ┌──────────────────────────────────────────┐");
        System.out.printf("  │ Spot ID      : %-25d │%n", spotID);
        System.out.printf("  │ Name         : %-25s │%n", spotName);

        // Group the number and unit together first, then pad the whole string to 25
        // spaces
        System.out.printf("  │ Total Area   : %-25s │%n", String.format("%.1f m²", spotArea));
        System.out.printf("  │ Usable Area  : %-25s │%n", String.format("%.1f m²", usableArea));

        System.out.printf("  │ Max Capacity : %-25d │%n", maxCapacity);
        System.out.printf("  │ Avg Wait Time: %-25s │%n", permittedAvgTime + " min");
        System.out.println("  └──────────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (Max: %d people, Avg wait: %d min)",
                spotID, spotName, maxCapacity, permittedAvgTime);
    }
}
