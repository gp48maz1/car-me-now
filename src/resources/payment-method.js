db = connect("localhost:27017/app17");
coll = db.cars;
coll.insert({
    "creditCardNumber" : Number(1234555512345555),
    "creditCardType" : "Visa",
    "expirationDate" : "09/20",
    "securityCode" : Number(101),
    "cardHolderName" : "Sam Freddric"});
