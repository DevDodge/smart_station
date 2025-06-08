package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionModel {
    private int transactionId;
    private int vehicleId;
    private int stationId;
    private int lineNumber;
    private String type;
    private LocalDateTime timestamp;
    private String vehiclePlate;
    private String driverName;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TransactionModel(int transactionId, int vehicleId, int stationId, int lineNumber,
                            String type, LocalDateTime timestamp, String vehiclePlate, String driverName) {
        this.transactionId = transactionId;
        this.vehicleId = vehicleId;
        this.stationId = stationId;
        this.lineNumber = lineNumber;
        this.type = type;
        this.timestamp = timestamp;
        this.vehiclePlate = vehiclePlate;
        this.driverName = driverName;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(formatter);
    }
}