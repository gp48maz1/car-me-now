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

@Path("paymentMethod")
public class PaymentMethodInterface {

    MongoCollection<Document> collection;

    public PaymentMethodInterface() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("app17");

        this.collection = database.getCollection("paymentMethod");
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public ArrayList<PaymentMethod> getAll() {

        ArrayList<PaymentMethod> paymentMethodList = new ArrayList<PaymentMethod>();

        FindIterable<Document> results = collection.find();
        if (results == null) {
            return  paymentMethodList;
        }
        for (Document item : results) {
            PaymentMethod paymentMethod = new PaymentMethod(
                    item.getInteger("creditCardNumber"),
                    item.getString("creditCardType"),
                    item.getString("expirationDate"),
                    item.getInteger("securityCode"),
                    item.getString("cardHolderName")
            );
            paymentMethod.setId(item.getObjectId("_id").toString());
            paymentMethodList.add(paymentMethod);
        }
        return paymentMethodList;
    }

    //{} vs no {}
    //{} are looking for any matching between between brackets
    //{} if conflict probably just goes in order...
    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public PaymentMethod getOne(@PathParam("id") String id) {


        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = collection.find(query).first();
        if (item == null) {
            return  null;
        }
        PaymentMethod paymentMethod = new PaymentMethod(
                item.getInteger("creditCardNumber"),
                item.getString("creditCardType"),
                item.getString("expirationDate"),
                item.getInteger("securityCode"),
                item.getString("cardHolderName")
        );
        paymentMethod.setId(item.getObjectId("_id").toString());
        return paymentMethod;

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
            Document doc = new Document("creditCardNumber", obj.getInt("creditCardNumber"))
                    .append("creditCardType", obj.getString("creditCardType"))
                    .append("expirationDate", obj.getString("expirationDate"))
                    .append("securityCode", obj.getString("securityCode"))
                    .append("cardHolderName", obj.getString("cardHolderName"));
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
            if (obj.has("creditCardNumber"))
                doc.append("creditCardNumber",obj.getString("creditCardNumber"));
            if (obj.has("creditCardType"))
                doc.append("creditCardType",obj.getString("creditCardType"));
            if (obj.has("expirationDate"))
                doc.append("expirationDate",obj.getString("expirationDate"));
            if (obj.has("securityCode"))
                doc.append("securityCode",obj.getString("securityCode"));
            if (obj.has("cardHolderName"))
                doc.append("cardHolderName",obj.getString("cardHolderName"));


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
