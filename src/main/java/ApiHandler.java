
import apoorv.db.Connection;
import apoorv.db.ConnectionUtility;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
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
                String db = "mysql";
                if (request.getParameter("db") != null)
                {
                    db = request.getParameter("db");
                }
                String id = "*";
                String[] urlParts = url.split("/");
                if (urlParts.length > 4)
                {
                    id = urlParts[4];
                }
                response.setContentType("text/html");
                String result = getEntryForId(db, id);
                out.println(result);
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
                        String result = deleteForId(db, id);
                        out.println(result);
                    }
                    else if ("update".equals(action))
                    {
                        response.setContentType("text/html");
                        String name = request.getParameter("name");
                        String salary = request.getParameter("salary");
                        String result = updateForId(db, id, name, salary);
                        out.println(result);
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
                    Connection connection = ConnectionUtility.getConnection(db);
                    connection.connect();
                    String result = connection.add(id,name,salary);
                    connection.close();
                    out.println(result);
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

    private String updateForId(String db, String id, String name, String salary) throws Exception
    {
        if (name == null || salary == null)
        {
            //need to fetch from db
            String result = getEntryForId(db, id);
            JSONObject oldJson = new JSONObject(result);
            if (name == null)
            {
                name = oldJson.getString("name");
            }
            if (salary == null)
            {
                salary = oldJson.getString("salary");
            }
        }
        Connection connection = ConnectionUtility.getConnection(db);
        connection.connect();
        String result = connection.update(id, name, salary);
        connection.close();
        return result;
    }

    private String deleteForId(String db, String id) throws Exception
    {
        Connection connection = null;
        try
        {
            connection = ConnectionUtility.getConnection(db);
            connection.connect();
            String result = connection.delete(id);
            return result;
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error Details", e);
            return new JSONObject().put("status", "connection_error").toString();
        }
        finally
        {
            connection.close();
        }
    }

    private String getEntryForId(String db, String id) throws Exception
    {
        Connection connection = null;
        try
        {
            connection = ConnectionUtility.getConnection(db);
            connection.connect();
            String result = connection.execute(id);
            return result;
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error Details", e);
            return new JSONObject().put("status", "connection_error").toString();
        }
        finally
        {
            connection.close();
        }
    }

    public void destroy()
    {
    }
}