db = connect("localhost:27017/app17");
coll = db.cars;
coll.insert({
    "bankName" : "Chase",
    "accountNumber" : Number(098172304987123),
    "routingNumber" : Number(213947293),
    "accountHolderName" : "Sue Suzzie Sizzle",
    "verified" : false});
