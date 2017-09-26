package app17.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;

@Path("cars")
public class CarsInterface {

    MongoCollection<Document> collection;

    public CarsInterface() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("app17");

        MongoCollection<Document> collection = database.getCollection("cars");
        this.collection = collection;

    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public ArrayList<Car> getAll() {

        ArrayList<Car> carList = new ArrayList<Car>();

        FindIterable<Document> results = collection.find();
        if (results == null) {
            return  carList;
        }
        for (Document item : results) {
            Car car = new Car(
                    item.getString("make"),
                    item.getString("model"),
                    item.getInteger("year", -1),
                    item.getString("size"),
                    item.getInteger("odometer"),
                    item.getString("licensePlate"),
                    item.getString("licenseState"),
                    item.getInteger("vin"),
                    item.getString("currentInsurer"),
                    item.getInteger("purchasedYear"),
                    item.getString("ownerNameTitle"),
                    item.getBoolean("isAccident")
            );
            car.setId(item.getObjectId("_id").toString());
            carList.add(car);
        }
        return carList;

    }

    //{} vs no {}
    //{} are looking for any matching between between brackets
    //{} if conflict probably just goes in order...
    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Car getOne(@PathParam("id") String id) {


        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = collection.find(query).first();
        if (item == null) {
            return  null;
        }
        Car car = new Car(
                item.getString("make"),
                item.getString("model"),
                item.getInteger("year", -1),
                item.getString("size"),
                item.getInteger("odometer"),
                item.getString("licensePlate"),
                item.getString("licenseState"),
                item.getInteger("vin"),
                item.getString("currentInsurer"),
                item.getInteger("purchasedYear"),
                item.getString("ownerNameTitle"),
                item.getBoolean("isAccident")
        );
        car.setId(item.getObjectId("_id").toString());
        return car;

    }

    //many JSON objects out there with different methods, need to watch out for errors and stuff
    //can keep attaching on property after another then it is ready to just send
    //many people set up API as a bulk POST (like location, so you are not sending every second)

    //this object is currently being sent back without an id, meaning it is not a valuable return
    //currently my produces say I am just returning a certain JSON, so i would have to reconfigure to send back JSON


    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Object create(JSONObject obj) {
        try {
            Document doc = new Document("make", obj.getString("make"))
                    .append("model", obj.getString("model"))
                    .append("size", obj.getString("size"))
                    .append("year", obj.getInt("year"))
                    .append("odometer", obj.getInt("odometer"))
                    .append("licensePlate",obj.getString("licensePlate"))
                    .append("licensedState", obj.getString("licenseState"))
                    .append("vin", obj.getString("vin"))
                    .append("currentInsurer", obj.getString("currentInsurer"))
                    .append("purchasedYear", obj.getInt("purchasedYear"))
                    .append("ownerNameTitle", obj.getString("ownerNameTitle"))
                    .append("isAccident", obj.getBoolean("isAccident"));
            collection.insertOne(doc);

        } catch(JSONException e) {

        }
        return obj;
    }

    @PATCH
    @Path("{id}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Object update(@PathParam("id") String id, JSONObject obj) {
        try {

            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));

            Document doc = new Document();
            if (obj.has("make"))
                doc.append("make",obj.getString("make"));
            if (obj.has("model"))
                doc.append("model",obj.getString("model"));
            if (obj.has("size"))
                doc.append("size",obj.getString("size"));
            if (obj.has("year"))
                doc.append("year",obj.getInt("year"));
            if (obj.has("odometer"))
                doc.append("odometer",obj.getString("odometer"));
            if (obj.has("licensePlate"))
                doc.append("licensePlate",obj.getString("licensePlate"));
            if (obj.has("licensedState"))
                doc.append("licensedState",obj.getString("licensedState"));
            if (obj.has("vin"))
                doc.append("vin",obj.getInt("vin"));
            if (obj.has("currentInsurer"))
                doc.append("currentInsurer",obj.getString("currentInsurer"));
            if (obj.has("purchasedYear"))
                doc.append("purchasedYear",obj.getInt("purchasedYear"));
            if (obj.has("ownerNameTitle"))
                doc.append("ownerNameTitle",obj.getInt("ownerNameTitle"));
            if (obj.has("isAccident"))
                doc.append("isAccident",obj.getBoolean("isAccident"));


            Document set = new Document("$set", doc);
            collection.updateOne(query,set);

        } catch(JSONException e) {
            System.out.println("Failed to create a document");

        }
        return obj;
    }


    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Object delete(@PathParam("id") String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        collection.deleteOne(query);

        return new JSONObject();
    }
}
