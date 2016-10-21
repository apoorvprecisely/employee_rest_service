/**
 * Created by unbxd on 06/10/16.
 */
package apoorv.service;

import apoorv.db.EmployeeBean;
import org.json.JSONException;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService
{
    public void addEmployee(String db, EmployeeBean employeeBean) throws IllegalAccessException, ClassNotFoundException, InstantiationException, JSONException, SQLException;

    public EmployeeBean getEmployee(String db, String id) throws ClassNotFoundException, SQLException, InstantiationException, JSONException, IllegalAccessException;

    public List<EmployeeBean> getAllEmployee(String db) throws ClassNotFoundException, SQLException, InstantiationException, JSONException, IllegalAccessException;

    public void deleteEmployee(String db, String id) throws JSONException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException;

    public void updateEmployee(String db, String id, EmployeeBean bean) throws JSONException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException;

}
