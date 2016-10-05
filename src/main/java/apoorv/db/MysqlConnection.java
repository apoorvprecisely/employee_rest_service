package apoorv.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by unbxd on 03/10/16.
 */
public class MysqlConnection implements ConnectionImpl
{
    private static Logger LOGGER = Logger.getLogger(MysqlConnection.class.getName());
    private Connection connection;

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
    public String execute(String query) throws SQLException, JSONException
    {
        Statement statement = null;
        ResultSet rs = null;
        try
        {
            statement = connection.createStatement();
            //TODO: change
            if ("*".equals(query))
            {
                rs = statement.executeQuery("select * from employee");
                JSONArray resultArr = new JSONArray();
                while (rs.next())
                {
                    JSONObject result = new JSONObject();
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int salary = rs.getInt("salary");
                    result.put("id", id);
                    result.put("name", name);
                    result.put("salary", salary + "");
                    resultArr.put(result);
                }
                return resultArr.toString();
            }
            else
            {
                rs = statement.executeQuery("select * from employee where id=" + query);
                JSONObject result = new JSONObject();
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int salary = rs.getInt("salary");
                    result.put("id", id);
                    result.put("name", name);
                    result.put("salary", salary + "");
                }
                return result.toString();
            }
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while searching employee", e);
            return new JSONObject().put("status", "failed").toString();
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
    }

    @Override
    public String add(String query) throws SQLException, JSONException
    {
        // **format**   connection2.add("1 ,'Tyler Durden' ,2000000");
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            String sql = "INSERT INTO employee VALUES (" + query + ")";
            LOGGER.log(Level.INFO, sql);
            statement.executeUpdate(sql);
            statement.close();
            return new JSONObject().put("status", "success").toString();
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while adding employee", e);
            return new JSONObject().put("status", "failed").toString();
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
    public String update(String id, String name, String salary) throws SQLException, JSONException
    {
        String query = "update employee set name = ? , salary = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try
        {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, salary);
            preparedStatement.setInt(3, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            return new JSONObject().put("status", "success").toString();

        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while deleting employee", e);
            return new JSONObject().put("status", "failed").toString();
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
    public String delete(String query) throws JSONException, SQLException
    {
        PreparedStatement preparedStatement = null;
        try
        {
            String compQuery = "delete from employee where id = ?";
            preparedStatement = connection.prepareStatement(compQuery);
            int id = Integer.parseInt(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return new JSONObject().put("status", "success").toString();

        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error while deleting employee", e);
            return new JSONObject().put("status", "failed").toString();
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
