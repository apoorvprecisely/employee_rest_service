package apoorv.db;

import com.mongodb.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by unbxd on 04/10/16.
 */
public class MongoConnection implements ConnectionImpl
{
    private MongoClient mongoClient;

    @Override
    public void connect()
    {
        mongoClient = new MongoClient("localhost", 27017);
    }

    @Override
    public void close()
    {

    }

    @Override
    public String execute(String query) throws JSONException
    {
        if ("*".equals(query))
        {
            DB db = mongoClient.getDB("apoorv");
            DBCollection coll = db.getCollection("employee");
            DBCursor cursor = coll.find();
            JSONArray resultArr = new JSONArray();
            while (cursor.hasNext())
            {
                DBObject rs = cursor.next();
                JSONObject result = new JSONObject();
                String id = rs.get("id").toString();
                String name = rs.get("name").toString();
                String salary = rs.get("salary").toString();
                result.put("id", id);
                result.put("name", name);
                result.put("salary", salary);
                resultArr.put(result);
            }
            return resultArr.toString();
        }
        else
        {
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("id", query);
            DB db = mongoClient.getDB("apoorv");
            DBCollection coll = db.getCollection("employee");
            DBCursor cursor = coll.find(whereQuery);
            JSONObject result = new JSONObject();
            while (cursor.hasNext())
            {
                DBObject rs = cursor.next();
                String id = rs.get("id").toString();
                String name = rs.get("name").toString();
                String salary = rs.get("salary").toString();
                result.put("id", id);
                result.put("name", name);
                result.put("salary", salary);
            }
            return result.toString();
        }
    }

    @Override
    public String add(String inputString) throws JSONException
    {
        DB db = mongoClient.getDB("apoorv");
        DBCollection coll = db.getCollection("employee");
        BasicDBObject data = new BasicDBObject();
        String[] parts = inputString.split(" ,");
        String id = parts[0];
        String salary = parts[2];
        String name = parts[1].substring(1, parts[1].length() - 1);
        data.append("id", id);
        data.append("name", name);
        data.append("salary", salary);
        coll.insert(data);
        return new JSONObject().put("status", "success").toString();
    }

    @Override
    public String update(String id, String name, String salary) throws JSONException
    {
        DB db = mongoClient.getDB("apoorv");
        DBCollection coll = db.getCollection("employee");
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("id", id);
        newDocument.put("salary", salary);
        newDocument.put("name", name);
        BasicDBObject searchQuery = new BasicDBObject().append("id", id);
        coll.update(searchQuery, newDocument);
        return new JSONObject().put("status", "success").toString();
    }

    @Override
    public String delete(String id) throws JSONException
    {
        DB db = mongoClient.getDB("apoorv");
        DBCollection coll = db.getCollection("employee");
        BasicDBObject document = new BasicDBObject();
        document.put("id", id);
        coll.remove(document);
        return new JSONObject().put("status", "success").toString();
    }
}
