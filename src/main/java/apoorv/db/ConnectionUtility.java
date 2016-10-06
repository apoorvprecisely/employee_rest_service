package apoorv.db;

/**
 * Created by unbxd on 03/10/16.
 */
public class ConnectionUtility
{
    public static Connection getConnection(String type) throws IllegalAccessException, InstantiationException, ClassNotFoundException
    {
        Class c = Class.forName("apoorv.db." + type);
        Connection connection = (Connection) c.newInstance();
        return connection;
    }
}
