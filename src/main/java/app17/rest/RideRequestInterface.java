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

@Path("rideRequest")
public class RideRequestInterface {

    MongoCollection<Document> collection;

    public RideRequestInterface() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("app17");

        this.collection = database.getCollection("rideRequest");
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public ArrayList<RideRequest> getAll() {

        ArrayList<RideRequest> rideRequestList = new ArrayList<RideRequest>();

        FindIterable<Document> results = collection.find();
        if (results == null) {
            return  rideRequestList;
        }
        for (Document item : results) {
            RideRequest rideRequest = new RideRequest(
                    item.getInteger("startingLocation_Lat"),
                    item.getInteger("startingLocation_Lon"),
                    item.getInteger("endingLocation_Lat"),
                    item.getInteger("endingLocation_Lon")
            );
            rideRequest.setId(item.getObjectId("_id").toString());
            rideRequestList.add(rideRequest);
        }
        return rideRequestList;
    }

    //{} vs no {}
    //{} are looking for any matching between between brackets
    //{} if conflict probably just goes in order...
    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public RideRequest getOne(@PathParam("id") String id) {


        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = collection.find(query).first();
        if (item == null) {
            return  null;
        }
        RideRequest rideRequest = new RideRequest(
                item.getInteger("startingLocation_Lat"),
                item.getInteger("startingLocation_Lon"),
                item.getInteger("endingLocation_Lat"),
                item.getInteger("endingLocation_Lon")
        );
        rideRequest.setId(item.getObjectId("_id").toString());
        return rideRequest;

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
            Document doc = new Document("startingLocation_Lat", obj.getInt("startingLocation_Lat"))
                    .append("startingLocation_Lon", obj.getInt("startingLocation_Lon"))
                    .append("endingLocation_Lat", obj.getInt("endingLocation_Lat"))
                    .append("endingLocation_Lon", obj.getInt("endingLocation_Lon"));
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
            if (obj.has("startingLocation_Lat"))
                doc.append("startingLocation_Lat",obj.getInt("startingLocation_Lat"));
            if (obj.has("startingLocation_Lon"))
                doc.append("startingLocation_Lon",obj.getInt("startingLocation_Lon"));
            if (obj.has("endingLocation_Lat"))
                doc.append("endingLocation_Lat",obj.getInt("endingLocation_Lat"));
            if (obj.has("endingLocation_Lon"))
                doc.append("endingLocation_Lon",obj.getInt("endingLocation_Lon"));


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
