package cat.code.netdisk.admin;

import cat.code.netdisk.ConfigLoader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zinc on 2016/11/17.
 *
 * @author Image
 */
@WebServlet( name = "Manage",urlPatterns = "/admin/Manage")
public class Manage extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        ConfigLoader CL = new ConfigLoader();
        Map<String,String> map=new HashMap<String,String>();
        map.put("size",CL.GetValueByKey("size"));
        map.put("DEBUG",ConfigFromSQL.GetValues("DEBUG"));
        map.put("title",ConfigFromSQL.GetValues("title"));
        map.put("regopen",ConfigFromSQL.GetValues("regopen"));
        request.setAttribute("data",map);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/Index.jsp");
        dispatcher.forward(request, response);
    }
}
