package app17.rest;


public class RideRequest {
    String id = null;
    Number startingLocation_Lat, startingLocation_Lon, endingLocation_Lat, endingLocation_Lon;

    public RideRequest(Number startingLocation_Lat, Number startingLocation_Lon, Number endingLocation_Lat,
                       Number endingLocation_Lon) {
        this.startingLocation_Lat = startingLocation_Lat;
        this.startingLocation_Lon = startingLocation_Lon;
        this.endingLocation_Lat = endingLocation_Lat;
        this.endingLocation_Lon = endingLocation_Lon;
    }
    public void setId(String id) {
        this.id = id;
    }
}
