/**
 * Created by unbxd on 03/10/16.
 */
package apoorv.db;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.List;

public interface Connection
{
    public void connect() throws ClassNotFoundException, SQLException;

    public void close() throws SQLException;

    public EmployeeBean getById(String id) throws JSONException, SQLException;

    public List<EmployeeBean> getAll() throws JSONException, SQLException;

    public void add(EmployeeBean bean) throws JSONException, SQLException;

    public void update(EmployeeBean bean) throws JSONException, SQLException;

    public void delete(String id) throws JSONException, SQLException;
}
