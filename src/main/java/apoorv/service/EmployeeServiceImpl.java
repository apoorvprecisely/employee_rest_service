package apoorv.service;

import apoorv.db.Connection;
import apoorv.db.ConnectionUtility;
import apoorv.db.EmployeeBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by unbxd on 06/10/16.
 */
public class EmployeeServiceImpl implements EmployeeService
{
    @Override
    public void addEmployee(String db, EmployeeBean employeeBean) throws IllegalAccessException, ClassNotFoundException, InstantiationException, JSONException, SQLException
    {
        Connection connection = null;
        try
        {
            connection = ConnectionUtility.getConnection(db);
            connection.connect();
            connection.add(employeeBean);
        }
        finally
        {
            if (connection != null)
            {
                connection.close();
            }
        }
    }

    @Override
    public EmployeeBean getEmployee(String db, String id) throws ClassNotFoundException, SQLException, InstantiationException, JSONException, IllegalAccessException
    {
        Connection connection = null;
        try
        {
            connection = ConnectionUtility.getConnection(db);
            connection.connect();
            EmployeeBean result = connection.getById(id);
            return result;
        }
        finally
        {
            connection.close();
        }
    }

    @Override
    public List<EmployeeBean> getAllEmployee(String db) throws ClassNotFoundException, SQLException, InstantiationException, JSONException, IllegalAccessException
    {
        Connection connection = null;
        try
        {
            connection = ConnectionUtility.getConnection(db);
            connection.connect();
            List<EmployeeBean> result = connection.getAll();
            return result;
        }
        finally
        {
            connection.close();
        }
    }

    @Override
    public void deleteEmployee(String db, String id) throws ClassNotFoundException, SQLException, InstantiationException, JSONException, IllegalAccessException
    {
        Connection connection = null;
        try
        {
            connection = ConnectionUtility.getConnection(db);
            connection.connect();
            connection.delete(id);
        }
        finally
        {
            connection.close();
        }

    }

    @Override
    public void updateEmployee(String db, String id, EmployeeBean bean) throws JSONException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
    {
        String name = bean.getName();
        String salary = bean.getSalary();
        if (name == "" || salary == "")
        {
            //need to fetch from db
            EmployeeBean result = getEmployee(db, id);
            JSONObject oldJson = new JSONObject(result);
            if (name == null)
            {
                name = oldJson.getString("name");
            }
            if (salary == null)
            {
                salary = oldJson.getString("salary");
            }
        }
        Connection connection = ConnectionUtility.getConnection(db);
        connection.connect();
        connection.update(new EmployeeBean(id, name, salary));
        connection.close();
    }
}
