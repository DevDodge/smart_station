package Options.LocalSession;


import java.util.HashMap;
import java.util.Map;

public class DataStorage {

    // Static map to store key-value pairs
    private static final Map<String, Object> dataMap = new HashMap<>();

    // Private constructor to prevent instantiation
    private DataStorage() {
        // Prevent creating instances of this class
    }

    // Method to store a value with a key
    public static void setValue(String key, Object value) {
        if (dataMap.containsKey(key)) {
            updateValue(key, value); // Update value if the key exists
        } else {
            dataMap.put(key, value); // Add new key-value pair if the key doesn't exist
        }
    }

    // Method to update a value by key (only if key exists)
    public static void updateValue(String key, Object value) {
        if (!dataMap.containsKey(key)) {
            throw new IllegalArgumentException("The key '" + key + "' does not exist in the data storage.");
        }
        dataMap.put(key, value); // Update the value for the key
    }


    // Method to retrieve a value by key
    public static Object getValue(String key) {
        return dataMap.get(key);
    }

    // Method to check if a key exists
    public static boolean containsKey(String key) {
        return dataMap.containsKey(key);
    }

    // Method to remove a key-value pair
    public static void removeData(String key) {
        dataMap.remove(key);
    }

    // Method to clear all data
    public static void clearData() {
        dataMap.clear();
    }

    // Method to print all data (for debugging purposes)
    public static void printAllData() {
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}
