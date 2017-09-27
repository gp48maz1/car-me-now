db = connect("localhost:27017/app17");
coll = db.rideRequest;
coll.insert({
    "startingLocation_Lat" : Number(41.13452345),
    "startingLocation_Lon" : Number(44.13452345),
    "endingLocation_Lat" : Number(44.13452345),
    "endingLocation_Lon" : Number(52.13452345)});
