package cat.code.netdisk.admin;

import cat.code.netdisk.ConfigLoader;
import cat.code.netdisk.MySql;

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
        String title = request.getParameter("title");
        String DEBUG = request.getParameter("DEBUG");
        String size = request.getParameter("size");
        String regopen = request.getParameter("regopen");
        MySql db = new MySql();
        try {
            if(title!=null){
                db.insert("update config set cvalue='"+title+"' where cfield='title';");
                db.pst.execute();
            }
            if(DEBUG!=null){
                db.insert("update config set cvalue='"+DEBUG+"' where cfield='DEBUG';");
                db.pst.execute();
            }
            if(size!=null){
                db.insert("update config set cvalue='"+size+"' where cfield='size';");
                db.pst.execute();
            }
            if(regopen!=null){
                db.insert("update config set cvalue='"+regopen+"' where cfield='regopen';");
                db.pst.execute();
            }
        }catch (Exception e){e.printStackTrace();}

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
