package cat.code.netdisk;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/22.
 *
 * @author Image
 */
@WebServlet(name="Login",urlPatterns = "/Login")
public class Login extends HttpServlet{
    private String username;
    private String password;
    ConfigLoader CL = new ConfigLoader();
    private String salt=CL.GetValueByKey("salt");

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        username=request.getParameter("username");
        password=request.getParameter("pw");
        MySql db = new MySql();
        password = DigestUtils.sha256Hex(password+salt);
        String sql="SELECT `username` FROM `user` where password='"+password
                +"' and username='"+username+"'";
        db.insert(sql);
        try {
            ResultSet rs =db.pst.executeQuery();
            if(rs.next()){
                Cookie un = new Cookie("username",username);
                Cookie token = new Cookie("token",DigestUtils.sha1Hex(username+password));
                un.setMaxAge(60*60*48);
                token.setMaxAge(60*60*48);
                response.addCookie(un);
                response.addCookie(token);
                HttpSession session = request.getSession(true);
                session.setAttribute("login","true");
                session.setAttribute("username",username);
                session.setAttribute("token",token);
                response.sendRedirect("/");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
