package app17.rest;


public class BankAccount {
    String id = null;
    String bankName, accountHolderName;
    Number accountNumber, routingNumber;
    Boolean verified;

    public BankAccount(String bankName, Number accountNumber, Number routingNumber, String accountHolderName,
                       Boolean verified) {

        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.accountHolderName = accountHolderName;
        this.verified = verified;
    }
    public void setId(String id) {
        this.id = id;
    }
}
