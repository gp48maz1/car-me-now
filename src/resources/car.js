db = connect("localhost:27017/app17");
coll = db.car;
coll.insert({
    "make" : "Ford",
    "model" : "Ranger",
    "year" : Number(1981),
    "size" : "10.5",
    "licensePlate" : "FR46XG",
    "licenseState": "CA",
    "vin": Number(77754126),
    "odometer" : Number(123202),
    "currentInsurer" : "AIG",
    "purchasedYear": Number(1979),
    "ownerNameTitle": "June Smith",
    "isAccident": false});
coll.insert({
    "make" : "BMW",
    "model" : "Series 5",
    "year" : Number(1981),
    "size" : "12.5",
    "licensePlate" : "XX46DG",
    "licenseState": "CA",
    "vin": Number(123333326),
    "odometer" : Number(124402),
    "currentInsurer" : "AIG",
    "purchasedYear": Number(1977),
    "ownerNameTitle": "Joey Smitheres",
    "isAccident": false});
coll.insert({
    "make" : "Merceds",
    "model" : "CLS",
    "year" : Number(1990),
    "size" : "9.5",
    "licensePlate" : "JREKW4",
    "licenseState": "CA",
    "vin": Number(284628736487),
    "odometer" : Number(238498),
    "currentInsurer" : "AIG",
    "purchasedYear": Number(1986),
    "ownerNameTitle": "Rick Richards",
    "isAccident": false});

