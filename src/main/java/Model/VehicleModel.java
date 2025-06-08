package Model;

// نموذج المركبة
public class VehicleModel {
    private int vehicleId;
    private int stationId;
    private int userId;
    private String carLicense;
    private String plateNumber;
    private boolean inStation;

    public VehicleModel(int vehicleId, int stationId, int userId, String carLicense, String plateNumber, boolean inStation) {
        this.vehicleId = vehicleId;
        this.stationId = stationId;
        this.userId = userId;
        this.carLicense = carLicense;
        this.plateNumber = plateNumber;
        this.inStation = inStation;
    }

    // Getters and setters
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public int getStationId() { return stationId; }
    public void setStationId(int stationId) { this.stationId = stationId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCarLicense() { return carLicense; }
    public void setCarLicense(String carLicense) { this.carLicense = carLicense; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public boolean isInStation() { return inStation; }
    public void setInStation(boolean inStation) { this.inStation = inStation; }
}