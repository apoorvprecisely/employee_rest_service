package apoorv.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by unbxd on 03/10/16.
 */
public class MysqlConnection implements Connection
{
    private static Logger LOGGER = Logger.getLogger(MysqlConnection.class.getName());
    private java.sql.Connection connection;

    @Override
    public void connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/apoorv", "root", "root");
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Connection failed, setting null", e);
            connection = null;
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.log(Level.SEVERE, "Connection failed, class not found", e);
            connection = null;
        }
    }

    @Override
    public void close()
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Unable to close connection", e);
        }
    }

    @Override
    public EmployeeBean getById(String query) throws SQLException, JSONException
    {
        EmployeeBean bean = new EmployeeBean();
        Statement statement = null;
        ResultSet rs = null;
        try
        {
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from employee where id=" + query);
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int salary = rs.getInt("salary");
                bean.setId(id + "");
                bean.setName(name);
                bean.setSalary(salary + "");
            }
            return bean;

        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while searching employee", e);
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
            if (statement != null)
            {
                statement.close();
            }
            return bean;
        }
    }

    @Override
    public List<EmployeeBean> getAll() throws JSONException, SQLException
    {
        List<EmployeeBean> employeeBeanList = new ArrayList<EmployeeBean>();
        Statement statement = null;
        ResultSet rs = null;
        try
        {
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from employee");
            while (rs.next())
            {
                EmployeeBean bean = new EmployeeBean();
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int salary = rs.getInt("salary");
                bean.setId(id + "");
                bean.setName(name);
                bean.setSalary(salary + "");
                employeeBeanList.add(bean);
            }
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while searching employee", e);
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
            if (statement != null)
            {
                statement.close();
            }
        }
        return employeeBeanList;
    }

    @Override
    public void add(EmployeeBean bean) throws SQLException, JSONException
    {
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            String sql = "INSERT INTO employee VALUES (" + bean.getId() + " ,'" + bean.getName() + "' ," + bean.getSalary() + ")";
            LOGGER.log(Level.INFO, sql);
            statement.executeUpdate(sql);
            statement.close();
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while adding employee", e);
        }
        finally
        {
            if (statement != null)
            {
                statement.close();
            }
        }
    }

    @Override
    public void update(EmployeeBean bean) throws SQLException, JSONException
    {
        String query = "update employee set name = ? , salary = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try
        {
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getSalary());
            preparedStatement.setInt(3, Integer.parseInt(bean.getId()));
            preparedStatement.executeUpdate();

        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while deleting employee", e);
        }
        finally
        {
            if (preparedStatement != null)
            {
                preparedStatement.close();
            }
        }
    }

    @Override
    public void delete(String query) throws JSONException, SQLException
    {
        PreparedStatement preparedStatement = null;
        try
        {
            String compQuery = "delete from employee where id = ?";
            preparedStatement = connection.prepareStatement(compQuery);
            int id = Integer.parseInt(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while deleting employee", e);
        }
        finally
        {
            if (preparedStatement != null)
            {
                preparedStatement.close();
            }
        }
    }
}
