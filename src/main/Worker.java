package main;

public class Worker {
    // Reference to ParcelMap for accessing parcels
    private ParcelMap parcelMap;
    // Reference to Log for recording events
    private Log log;

    // Constructor to initialize ParcelMap and Log instance
    public Worker(ParcelMap parcelMap) {
        this.parcelMap = parcelMap;
        this.log = Log.getInstance();
    }

    // Processes a customer's request to collect a parcel
    public void processCustomer(Customer customer) {
        String packageID = customer.getPackageID();
        Parcel parcel = parcelMap.getPackage(packageID);

        if (parcel != null && !parcel.isCollected()) {
            double fee = calculateFee(parcel);
            parcel.setCollected(true);
            log.addEvent("Customer " + customer.getName() + " collected parcel " + packageID + ". Fee: $" + fee);
        } else {
            log.addEvent("Parcel " + packageID + " not found or already collected for customer " + customer.getName());
        }
    }

    // Calculates the fee for a parcel based on various factors
    public double calculateFee(Parcel parcel) {
        double baseRate = 5.0;
        double weightFee = parcel.getWeight() * 0.1;
        double sizeFee = parcel.calculateVolume() * 0.05;
        double daysFee = parcel.getDaysInDepot() * 0.2;

        return baseRate + weightFee + sizeFee + daysFee;
    }

    // Returns a string representation of the Worker
    @Override
    public String toString() {
        return "Worker processing parcels";
    }
}
