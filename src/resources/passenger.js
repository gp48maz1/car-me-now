db = connect("localhost:27017/app17");
coll = db.passenger;
coll.insert({
    "firstName" : "Alec",
    "middleName": "Boon",
    "lastName" : "Cali",
    "emailAddress": "alec@gmail.com",
    "password": "password003",
    "address1" : "567 Main Street",
    "address2" : null,
    "city": "Los Angeles",
    "state": "CA",
    "country" : "us",
    "postalCode" : "94035",
    "driverLicense": "F1354333",
    "dlIssueState": "CA",
    "rating": Number(4.7)});
