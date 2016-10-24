package cat.code.netdisk;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;


/**
 * Created by zinc on 2016/10/23.
 *
 * @author Image
 */

@WebServlet(name = "index" , urlPatterns = "/index.html")

public class Index extends HttpServlet{

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
            doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(true);
        IsLogin il=new IsLogin();
        int login=il.isLogin(session,request,response);
        if(login==1){
            response.sendRedirect("/Panel");
        }else {
            File file = new File(getServletContext().getRealPath("/")+"Init.jsp");
            if(file.exists()){
                response.sendRedirect("Init.jsp");
            }else {
                response.sendRedirect("index.jsp");
            }
        }
    }
}
