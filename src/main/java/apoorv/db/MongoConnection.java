package apoorv.db;

import com.mongodb.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by unbxd on 04/10/16.
 */
public class MongoConnection implements Connection
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
    public EmployeeBean getById(String query) throws JSONException
    {
        EmployeeBean bean = new EmployeeBean();

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", query);
        DB db = mongoClient.getDB("apoorv");
        DBCollection coll = db.getCollection("employee");
        DBCursor cursor = coll.find(whereQuery);
        while (cursor.hasNext())
        {
            DBObject rs = cursor.next();
            String id = rs.get("id").toString();
            String name = rs.get("name").toString();
            String salary = rs.get("salary").toString();
            bean.setId(id);
            bean.setName(name);
            bean.setSalary(salary);
        }
        return bean;
    }

    @Override
    public List<EmployeeBean> getAll() throws JSONException, SQLException
    {
        List<EmployeeBean> employeeBeanList = new ArrayList<EmployeeBean>();

        DB db = mongoClient.getDB("apoorv");
        DBCollection coll = db.getCollection("employee");
        DBCursor cursor = coll.find();
        while (cursor.hasNext())
        {
            DBObject rs = cursor.next();
            EmployeeBean bean = new EmployeeBean();
            String id = rs.get("id").toString();
            String name = rs.get("name").toString();
            String salary = rs.get("salary").toString();
            bean.setId(id + "");
            bean.setName(name);
            bean.setSalary(salary + "");
            employeeBeanList.add(bean);
        }
        return employeeBeanList;
    }

    @Override
    public void add(EmployeeBean bean) throws JSONException
    {
        DB db = mongoClient.getDB("apoorv");
        DBCollection coll = db.getCollection("employee");
        BasicDBObject data = new BasicDBObject();
        data.append("id", bean.getId());
        data.append("name", bean.getName());
        data.append("salary", bean.getSalary());
        coll.insert(data);
    }

    @Override
    public void update(EmployeeBean bean) throws JSONException
    {
        DB db = mongoClient.getDB("apoorv");
        DBCollection coll = db.getCollection("employee");
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("id", bean.getId());
        newDocument.put("salary", bean.getSalary());
        newDocument.put("name", bean.getName());
        BasicDBObject searchQuery = new BasicDBObject().append("id", bean.getName());
        coll.update(searchQuery, newDocument);
    }

    @Override
    public void delete(String id) throws JSONException
    {
        DB db = mongoClient.getDB("apoorv");
        DBCollection coll = db.getCollection("employee");
        BasicDBObject document = new BasicDBObject();
        document.put("id", id);
        coll.remove(document);
    }
}
