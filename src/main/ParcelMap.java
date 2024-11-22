package main;

import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    // Map to store parcels with their parcelID as the key
    private Map<String, Parcel> parcels;

    // Constructor initializes the parcel map
    public ParcelMap() {
        this.parcels = new HashMap<>();
    }

    // Adds a parcel to the map
    public void addPackage(Parcel parcel) {
        parcels.put(parcel.getPackageID(), parcel);
    }

    // Retrieves a parcel by its ID
    public Parcel getPackage(String packageID) {
        return parcels.get(packageID);
    }

    // Removes and returns a parcel by its ID
    public Parcel removePackage(String packageID) {
        return parcels.remove(packageID);
    }

    // Checks if a parcel exists in the map by its ID
    public boolean containsPackage(String packageID) {
        return parcels.containsKey(packageID);
    }

    // Returns the number of parcels in the map
    public int getPackageCount() {
        return parcels.size();
    }

    // Returns a string representation of the parcels in the map
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Parcel parcel : parcels.values()) {
            sb.append(parcel).append("\n");
        }
        return sb.toString();
    }
}
