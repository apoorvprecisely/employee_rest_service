
import apoorv.db.EmployeeBean;
import apoorv.service.EmployeeServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
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
            String id = null;
            if (request instanceof HttpServletRequest)
            {
                String db = "MysqlConnection";
                if (request.getParameter("db") != null)
                {
                    db = request.getParameter("db");
                }
                if (request.getParameter("id") != null)
                {
                    id = request.getParameter("id");
                }
                if (id != null)
                {
                    response.setContentType("text/html");
                    EmployeeBean result = new EmployeeServiceImpl().getEmployee(db, id);
                    if ("".equals(result.getId()))
                    {
                        out.println(new JSONObject().put("status", "404").toString());
                    }
                    else
                    {
                        out.println(result.toJson().toString());
                    }
                }
                else
                {
                    response.setContentType("text/html");
                    List<EmployeeBean> result = new EmployeeServiceImpl().getAllEmployee(db);
                    StringBuilder sb = new StringBuilder();
                    ObjectMapper mapper = new ObjectMapper();
                    if (result.size() > 0)
                    {
                        for (EmployeeBean employeeBean : result)
                        {
                            String json = mapper.writeValueAsString(employeeBean);
                            sb.append(json + " ");
                        }
                        out.println(sb.toString());
                    }
                    else
                    {
                        out.println(new JSONObject().put("status", "404").toString());
                    }
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
                LOGGER.log(Level.SEVERE, "Exception thrown", e);
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            PrintWriter out = response.getWriter();
            String id = null;
            if (request instanceof HttpServletRequest)
            {
                String db = "MysqlConnection";
                if (request.getParameter("db") != null)
                {
                    db = request.getParameter("db");
                }
                if (request.getParameter("id") != null)
                {
                    id = request.getParameter("id");
                }
                String name = request.getParameter("name");
                String salary = request.getParameter("salary");
                response.setContentType("text/html");
                new EmployeeServiceImpl().addEmployee(db, new EmployeeBean(id, name, salary));
                out.println(new JSONObject().put("status", "201"));

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
                LOGGER.log(Level.SEVERE, "Exception thrown", e);
            }
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String db = "MysqlConnection";
            String id = null;
            if (request.getParameter("db") != null)
            {
                db = request.getParameter("db");
            }
            if (request.getParameter("id") != null)
            {
                id = request.getParameter("id");
            }
            PrintWriter out = response.getWriter();
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
                LOGGER.log(Level.SEVERE, "Exception thrown", e);
            }
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String db = "MysqlConnection";
            String id = null;
            if (request.getParameter("db") != null)
            {
                db = request.getParameter("db");
            }
            if (request.getParameter("id") != null)
            {
                id = request.getParameter("id");
            }
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            new EmployeeServiceImpl().deleteEmployee(db, id);
            out.println(new JSONObject().put("status", "204"));
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
                LOGGER.log(Level.SEVERE, "Exception thrown", e);
            }
        }
    }

    public void destroy()
    {
    }
}