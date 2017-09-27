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

@Path("bankAccount")
public class BankAccountInterface {

    MongoCollection<Document> collection;

    public BankAccountInterface() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("app17");

        this.collection = database.getCollection("bankAccount");
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public ArrayList<BankAccount> getAll() {

        ArrayList<BankAccount> bankAccountList = new ArrayList<BankAccount>();

        FindIterable<Document> results = collection.find();
        if (results == null) {
            return  bankAccountList;
        }
        for (Document item : results) {
            BankAccount bankAccount = new BankAccount(
                    item.getString("bankName"),
                    item.getDouble("accountNumber"),
                    item.getDouble("routingNumber"),
                    item.getString("accountHolderName"),
                    item.getBoolean("verified")
            );
            bankAccount.setId(item.getObjectId("_id").toString());
            bankAccountList.add(bankAccount);
        }
        return bankAccountList;
    }

    //{} vs no {}
    //{} are looking for any matching between between brackets
    //{} if conflict probably just goes in order...
    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public BankAccount getOne(@PathParam("id") String id) {


        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = collection.find(query).first();
        if (item == null) {
            return  null;
        }
        BankAccount bankAccount = new BankAccount(
                item.getString("bankName"),
                item.getDouble("accountNumber"),
                item.getDouble("routingNumber"),
                item.getString("accountHolderName"),
                item.getBoolean("verified")
        );
        bankAccount.setId(item.getObjectId("_id").toString());
        return bankAccount;

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
            Document doc = new Document("bankName", obj.getString("bankName"))
                    .append("accountNumber", obj.getDouble("accountNumber"))
                    .append("routingNumber", obj.getDouble("routingNumber"))
                    .append("accountHolderName", obj.getString("accountHolderName"))
                    .append("verified", obj.getBoolean("verified"));
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
            if (obj.has("bankName"))
                doc.append("bankName",obj.getString("bankName"));
            if (obj.has("accountNumber"))
                doc.append("accountNumber",obj.getDouble("accountNumber"));
            if (obj.has("routingNumber"))
                doc.append("routingNumber",obj.getDouble("routingNumber"));
            if (obj.has("accountHolderName"))
                doc.append("accountHolderName",obj.getString("accountHolderName"));
            if (obj.has("verified"))
                doc.append("verified",obj.getBoolean("verified"));


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
