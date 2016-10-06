
import apoorv.db.Connection;
import apoorv.db.ConnectionUtility;
import apoorv.db.EmployeeBean;
import apoorv.service.EmployeeServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class ApiHandler extends HttpServlet
{
    private static Logger LOGGER = Logger.getLogger(ApiHandler.class.getName());

    public void init() throws ServletException
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            PrintWriter out = response.getWriter();
            if (request instanceof HttpServletRequest)
            {
                String url = ((HttpServletRequest) request).getRequestURL().toString();
                String db = "MysqlConnection";
                if (request.getParameter("db") != null)
                {
                    db = request.getParameter("db");
                }

                String[] urlParts = url.split("/");
                if (urlParts.length > 4)
                {
                    String id = urlParts[4];
                    response.setContentType("text/html");
                    EmployeeBean result = new EmployeeServiceImpl().getEmployee(db, id);
                    out.println(result.toJson().toString());
                }
                else
                {
                    response.setContentType("text/html");
                    List<EmployeeBean> result = new EmployeeServiceImpl().getAllEmployee(db);
                    JSONArray jsonArray = new JSONArray();
                    for (EmployeeBean employeeBean : result)
                    {
                        JSONObject temp = new JSONObject();
                        temp.put("id", employeeBean.getId());
                        temp.put("name", employeeBean.getName());
                        temp.put("salary", employeeBean.getSalary());
                        jsonArray.put(temp);
                    }
                    out.println(jsonArray.toString());
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "info", e);
            PrintWriter out = response.getWriter();
            try
            {
                out.println(new JSONObject().put("status", "500"));
            }
            catch (JSONException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            PrintWriter out = response.getWriter();
            if (request instanceof HttpServletRequest)
            {
                String url = ((HttpServletRequest) request).getRequestURL().toString();
                String db = "mysql";
                if (request.getParameter("db") != null)
                {
                    db = request.getParameter("db");
                }
                String[] urlParts = url.split("/");
                if (urlParts.length > 4)
                {
                    String id = urlParts[4];
                    String action = request.getParameter("action");
                    if ("delete".equals(action))
                    {
                        response.setContentType("text/html");
                        new EmployeeServiceImpl().deleteEmployee(db, id);
                        out.println(new JSONObject().put("status", "204"));
                    }
                    else if ("update".equals(action))
                    {
                        response.setContentType("text/html");
                        String name = "";
                        String salary = "";
                        if (name != null)
                            name = request.getParameter("name");
                        if (salary != null)
                            salary = request.getParameter("salary");
                        new EmployeeServiceImpl().updateEmployee(db, id, new EmployeeBean(id, name, salary));
                        out.println(new JSONObject().put("status", "200"));
                    }
                    else
                    {
                        out.println(new JSONObject().put("error", "unknown action" + action));
                    }
                }
                else
                {
                    // add new entry
                    String name = request.getParameter("name");
                    String salary = request.getParameter("salary");
                    String id = request.getParameter("id");
                    response.setContentType("text/html");
                    new EmployeeServiceImpl().addEmployee(db, new EmployeeBean(id, name, salary));
                    out.println(new JSONObject().put("status", "201"));
                }

            }

        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "info", e);
            PrintWriter out = response.getWriter();
            try
            {
                out.println(new JSONObject().put("status", "500"));
            }
            catch (JSONException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public void destroy()
    {
    }
}