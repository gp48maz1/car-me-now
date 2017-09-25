db = connect("localhost:27017/app17");
coll = db.driver;
coll.insert({
    "firstName" : "Zoon",
    "middleName": "Xyhe",
    "lastName" : "Vec",
    "emailAddress": "Zoon@gmail.com",
    "password": "password001",
    "address1" : "001 Main Street",
    "address2" : null,
    "city": "Los Angeles",
    "state": "CA",
    "country" : "us",
    "postalCode" : "94035",
    "driverLicense": "F134444",
    "dlIssueState": "CA",
    "rating": Number(4.0)});
coll.insert({
      "firstName" : "Andrew",
      "middleName": "Ben",
      "lastName" : "Carlson",
      "emailAddress": "andrew@gmail.com",
      "password": "password002",
      "address1" : "002 Main Street",
      "address2" : null,
      "city": "Los Angeles",
      "state": "CA",
      "country" : "us",
      "postalCode" : "94035",
      "driverLicense": "F135555",
      "dlIssueState": "CA",
      "rating": Number(4.5)});
