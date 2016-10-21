package apoorv.db;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by unbxd on 06/10/16.
 */
public class EmployeeBean
{
    private String name;
    private String id;
    private String salary;

    public EmployeeBean()
    {
        name = "";
        id = "";
        salary = "";
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("id", id);
        jsonObject.put("salary", salary);
        return jsonObject;
    }

    public EmployeeBean(String id, String name, String salary)
    {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public String getSalary()
    {
        return salary;
    }

    public void setSalary(String salary)
    {
        this.salary = salary;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
