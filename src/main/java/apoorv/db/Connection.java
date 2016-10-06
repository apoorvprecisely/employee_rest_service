/**
 * Created by unbxd on 03/10/16.
 */
package apoorv.db;

import org.json.JSONException;

import java.sql.SQLException;

public interface Connection
{
    public void connect();

    public void close();

    public String execute(String id) throws JSONException, SQLException;

    public String add(String id,String name,String salary) throws JSONException, SQLException;

    public String update(String id, String name, String salary) throws JSONException, SQLException;

    public String delete(String id) throws JSONException, SQLException;
}
