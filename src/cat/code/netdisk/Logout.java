package cat.code.netdisk;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by zinc on 2016/10/24.
 *
 * @author Image
 */

@WebServlet(name = "Logout" , urlPatterns = "/Logout")

public class Logout extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        IsLogin il=new IsLogin();
        int login=il.isLogin(session,request,response);
        if(login==1) {
            session.setAttribute("token",null);
            session.setAttribute("username",null);
            session.setAttribute("login","false");
            session.setAttribute("DEBUG",null);
            Cookie cusername= new Cookie("username",null);
            Cookie ctoken= new Cookie("token",null);
            Cookie maxdisk= new Cookie("maxdisk",null);
            maxdisk.setMaxAge(0);
            cusername.setMaxAge(0);
            ctoken.setMaxAge(0);
            maxdisk.setPath("/");
            cusername.setPath("/");
            ctoken.setPath("/");
            response.addCookie(ctoken);
            response.addCookie(cusername);
            response.addCookie(maxdisk);
            response.sendRedirect("/");
        }else {
            response.sendRedirect("/");
        }
    }
}
