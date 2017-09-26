package app17.rest;


public class PaymentMethod {
    String id = null;
    Number creditCardNumber;
    String creditCardType;
    String expirationDate;
    Number securityCode;
    String cardHolderName;
    public PaymentMethod(Number creditCardNumber, String creditCardType, String expirationDate, Number securityCode,
                         String cardHolderName) {
        this.creditCardNumber = creditCardNumber;
        this.creditCardType = creditCardType;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.cardHolderName = cardHolderName;
    }
    public void setId(String id) {
        this.id = id;
    }
}
