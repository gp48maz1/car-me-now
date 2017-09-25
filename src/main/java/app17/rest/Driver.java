package app17.rest;


import javax.ws.rs.Path;

public class Driver {
    String id = null;
    String firstName;
    String middleName;
    String lastName;
    String emailAddress;
    String password;
    String address1;
    String address2;
    String city;
    String stateCode;
    String countryCode;
    String postalCode;
    String driverLicense;
    String dlIssueState;
    Number rating;
    public Driver(String firstName, String middleName, String lastName, String emailAddress, String password,
                  String address1, String address2, String city, String stateCode, String countryCode, String postalCode,
                  String driverLicense, String dlIssueState, Number rating) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.stateCode = stateCode;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.driverLicense = driverLicense;
        this.dlIssueState = dlIssueState;
        this.rating = rating;
    }
    public void setId(String id) {
        this.id = id;
    }
}
