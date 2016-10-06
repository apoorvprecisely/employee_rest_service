package apoorv.db;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by unbxd on 03/10/16.
 */
public class ConnectionUtility
{
    private static Logger LOGGER = Logger.getLogger(ConnectionUtility.class.getName());

    public static Connection getConnection(String type) throws IllegalAccessException, InstantiationException, ClassNotFoundException
    {
        Class c = null;
        try
        {
            c = Class.forName("apoorv.db." + type);
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.log(Level.SEVERE, "Class not defined " + type + ",Returning MysqlConnection", e);
            c = Class.forName("apoorv.db.MysqlConnection");
        }
        Connection connection = (Connection) c.newInstance();
        return connection;
    }
}
