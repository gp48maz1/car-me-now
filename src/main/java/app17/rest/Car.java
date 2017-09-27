package app17.rest;

public class Car {

    String id = null;
    String make, model, size, licensePlate, licenseState, currentInsurer, ownerNameTitle;
    Number year, odometer, vin, purchasedYear;
    Boolean isAccident;

    public Car(String make, String model, Number year, String size,
               String licensePlate, String licenseState, Number vin, Number odometer,
               String currentInsurer, Number purchasedYear, String ownerNameTitle,
               Boolean isAccident) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.size = size;
        this.licensePlate = licensePlate;
        this.licenseState = licenseState;
        this.vin = vin;
        this.odometer = odometer;
        this.currentInsurer = currentInsurer;
        this.purchasedYear = purchasedYear;
        this.ownerNameTitle = ownerNameTitle;
        this.isAccident = isAccident;
    }

    public void setId(String id) {
        this.id = id;
    }
}
