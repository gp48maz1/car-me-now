package app17.rest;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("passenger")
public class PassengerInterface {

    MongoCollection<Document> collection;

    public PassengerInterface() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("app17");

        this.collection = database.getCollection("passenger");
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public ArrayList<Passenger> getAll() {

        ArrayList<Passenger> passengerList = new ArrayList<Passenger>();

        FindIterable<Document> results = collection.find();
        if (results == null) {
            return  passengerList;
        }
        for (Document item : results) {
            Passenger passenger = new Passenger(
                    item.getString("firstName"),
                    item.getString("middleName"),
                    item.getString("lastName"),
                    item.getString("emailAddress"),
                    item.getString("password"),
                    item.getString("address1"),
                    item.getString("address2"),
                    item.getString("state"),
                    item.getString("city"),
                    item.getString("countryCode"),
                    item.getString("postalCode"),
                    item.getString("driverLicense"),
                    item.getString("dlIssueState"),
                    item.getDouble("rating")
            );
            passenger.setId(item.getObjectId("_id").toString());
            passengerList.add(passenger);
        }
        return passengerList;
    }

    //{} vs no {}
    //{} are looking for any matching between between brackets
    //{} if conflict probably just goes in order...
    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Passenger getOne(@PathParam("id") String id) {


        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = collection.find(query).first();
        if (item == null) {
            return  null;
        }
        Passenger passenger = new Passenger(
                item.getString("firstName"),
                item.getString("middleName"),
                item.getString("lastName"),
                item.getString("emailAddress"),
                item.getString("password"),
                item.getString("address1"),
                item.getString("address2"),
                item.getString("state"),
                item.getString("city"),
                item.getString("countryCode"),
                item.getString("postalCode"),
                item.getString("driverLicense"),
                item.getString("dlIssueState"),
                item.getDouble("rating")
        );
        passenger.setId(item.getObjectId("_id").toString());
        return passenger;

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
            Document doc = new Document("firstName", obj.getString("firstName"))
                    .append("middleName", obj.getString("middleName"))
                    .append("lastName", obj.getString("lastName"))
                    .append("emailAddress", obj.getString("emailAddress"))
                    .append("password", obj.getString("password"))
                    .append("address1",obj.getString("address1"))
                    .append("address2", obj.getString("address2"))
                    .append("city", obj.getString("city"))
                    .append("state", obj.getString("state"))
                    .append("countryCode", obj.getString("countryCode"))
                    .append("postalCode", obj.getString("postalCode"))
                    .append("driverLicense", obj.getBoolean("driverLicense"))
                    .append("dlIssueState", obj.getBoolean("dlIssueState"))
                    .append("rating", obj.getDouble("rating"));
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
            if (obj.has("firstName"))
                doc.append("firstName",obj.getString("firstName"));
            if (obj.has("middleName"))
                doc.append("middleName",obj.getString("middleName"));
            if (obj.has("lastName"))
                doc.append("lastName",obj.getString("lastName"));
            if (obj.has("emailAddress"))
                doc.append("emailAddress",obj.getString("emailAddress"));
            if (obj.has("password"))
                doc.append("password",obj.getString("password"));
            if (obj.has("address1"))
                doc.append("address1",obj.getString("address1"));
            if (obj.has("address2"))
                doc.append("address2",obj.getString("address2"));
            if (obj.has("city"))
                doc.append("city",obj.getString("city"));
            if (obj.has("state"))
                doc.append("state",obj.getString("state"));
            if (obj.has("countryCode"))
                doc.append("countryCode",obj.getString("countryCode"));
            if (obj.has("postalCode"))
                doc.append("postalCode",obj.getString("postalCode"));
            if (obj.has("driverLicense"))
                doc.append("driverLicense",obj.getString("driverLicense"));
            if (obj.has("dlIssueState"))
                doc.append("dlIssueState",obj.getString("dlIssueState"));
            if (obj.has("rating"))
                doc.append("rating",obj.getDouble("rating"));


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
