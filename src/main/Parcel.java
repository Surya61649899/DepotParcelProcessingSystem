package main;

/**
 * The Parcel class represents a package with specific attributes such as 
 * parcel ID, days in depot, weight, dimensions, and collection status.
 */
public class Parcel {
    // Unique identifier for the parcel
    private String packageID;
    // Number of days the parcel has been in the depot
    private int daysInDepot;
    // Weight of the parcel in kilograms
    private double weight;
    // Length of the parcel in meters
    private double length;
    // Width of the parcel in meters
    private double width;
    // Height of the parcel in meters
    private double height;
    // Boolean flag to indicate if the parcel has been collected
    private boolean isCollected;

    /**
     * Constructor to initialize the Parcel object with specified attributes.
     * @param parcelID Unique identifier for the parcel
     * @param daysInDepot Number of days the parcel has been in the depot
     * @param weight Weight of the parcel in kilograms
     * @param length Length of the parcel in meters
     * @param width Width of the parcel in meters
     * @param height Height of the parcel in meters
     */
    public Parcel(String packageID, int daysInDepot, double weight, double length, double width, double height) {
        this.packageID = packageID;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.isCollected = false; // Initializing the parcel as not collected
    }

    // Getter method for parcelID
    public String getPackageID() {
        return packageID;
    }

    // Getter method for daysInDepot
    public int getDaysInDepot() {
        return daysInDepot;
    }

    // Getter method for weight
    public double getWeight() {
        return weight;
    }

    // Getter method for length
    public double getLength() {
        return length;
    }

    // Getter method for width
    public double getWidth() {
        return width;
    }

    // Getter method for height
    public double getHeight() {
        return height;
    }

    // Getter method for isCollected
    public boolean isCollected() {
        return isCollected;
    }

    // Setter method for isCollected
    public void setCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    /**
     * Method to calculate the volume of the parcel.
     * @return The volume of the parcel in cubic meters
     */
    public double calculateVolume() {
        return length * width * height;
    }

    /**
     * Override of the toString method to provide a string representation
     * of the parcel's attributes.
     * @return A string representation of the parcel
     */
    @Override
    public String toString() {
        return "Package ID: " + packageID + ", Days in Depot: " + daysInDepot + ", Weight: " + weight + "kg, Dimensions: " 
               + length + "x" + width + "x" + height + " meters, Collected: " + (isCollected ? "Yes" : "No");
    }
}
