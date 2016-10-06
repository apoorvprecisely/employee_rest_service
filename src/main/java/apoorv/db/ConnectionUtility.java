package apoorv.db;

/**
 * Created by unbxd on 03/10/16.
 */
public class ConnectionUtility
{
    public static Connection getConnection(String type) throws Exception
    {
        if ("mysql".equals(type))
        {
            return new MysqlConnection();
        }
        else if ("mongo".equals(type))
        {
            return new MongoConnection();
        }
        else
        {
            throw new Exception("Unknown DB Type" + type);
        }
    }
}
