package Model;

public class usersModel {
    // Enum for the 'type' field
    public enum UserType {
        MANAGER,
        DRIVER
    }
    
    // Fields
    private int userId;
    private String fullName;
    private String phoneNumber;
    private long nid;
    private String address;
    private UserType type;
    private String personalLicense;
    
    public usersModel() { }
    
    // Parameterized constructor
    public usersModel(int userId, String fullName, String phoneNumber, long nid, String address, UserType type, String personalLicense) {
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.nid = nid;
        this.address = address;
        this.type = type;
        this.personalLicense = personalLicense;
    }
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public long getNid() {
        return nid;
    }
    
    public void setNid(long nid) {
        this.nid = nid;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getType() {
        return type.toString();
    }
    
    public UserType getUserType() {
        return type;
    }
    
    public void setType(UserType type) {
        this.type = type;
    }
    
    public String getPersonalLicense() {
        return personalLicense;
    }
    
    public void setPersonalLicense(String personalLicense) {
        this.personalLicense = personalLicense;
    }
    
    // Overriding toString() method
    @Override
    public String toString() {
        return "usersModel{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nid=" + nid +
                ", address='" + address + '\'' +
                ", type=" + type +
                ", personalLicense='" + personalLicense + '\'' +
                '}';
    }
    
}
