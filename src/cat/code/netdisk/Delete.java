package cat.code.netdisk;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * Created by zinc on 2016/10/27.
 *
 * @author Image
 */

@WebServlet(name = "Delete",urlPatterns = "/Delete")
public class Delete extends HttpServlet{
    ConfigLoader CL = new ConfigLoader();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        String path = request.getParameter("path");
        HttpSession session = request.getSession();
        File file = new File(CL.GetValueByKey("basepath")+
                "WEB-INF/files/"+session.getAttribute("username")+"/"+path);
        if(file.exists()){
            file.delete();
        }
        response.sendRedirect("/");
    }
}
