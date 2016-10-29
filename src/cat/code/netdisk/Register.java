package cat.code.netdisk;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/27.
 *
 * @author Image
 */

@WebServlet(name = "Register",urlPatterns = "/Register")

public class Register extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        ConfigLoader CL = new ConfigLoader();
        if(CL.GetValueByKey("regopen").equals("true")){
            String username = request.getParameter("username");
            String pw = request.getParameter("pw");
            String rpw = request.getParameter("pw1");

            if(!pw.equals(rpw)){
                response.sendRedirect("/register.jsp?error=notmatch");
            }else {
                User user = new User(username,pw);
                int usercode=user.create();
                if(usercode==1){
                    response.sendRedirect("/");
                }else if(usercode==2){
                    response.sendRedirect("/register.jsp?error=username");
                }else if(usercode==3){
                    response.sendRedirect("/register.jsp?error=database");
                }
            }
        }else {
            response.sendRedirect("/register.jsp?error=close");
        }
    }
}
