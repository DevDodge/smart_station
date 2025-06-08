package Model;

public class stationModel {
    private int stationId;
    private String stationName;
    private String locGov;
    private String locCity;
    private String locRegion;
    private int vehicleCapacity;
    private boolean isPrimary;

    public stationModel(int stationId, String stationName, String locGov, String locCity, String locRegion, int vehicleCapacity, boolean isPrimary) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.locGov = locGov;
        this.locCity = locCity;
        this.locRegion = locRegion;
        this.vehicleCapacity = vehicleCapacity;
        this.isPrimary = isPrimary;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getLocGov() {
        return locGov;
    }

    public void setLocGov(String locGov) {
        this.locGov = locGov;
    }

    public String getLocCity() {
        return locCity;
    }

    public void setLocCity(String locCity) {
        this.locCity = locCity;
    }

    public String getLocRegion() {
        return locRegion;
    }

    public void setLocRegion(String locRegion) {
        this.locRegion = locRegion;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(int vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
