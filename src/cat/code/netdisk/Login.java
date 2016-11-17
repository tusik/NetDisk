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

        password = DigestUtils.sha256Hex(password+salt);
        String sql="SELECT `username` FROM `user` where password=? and username=?";
        String vals[]={password,username};
        MySql db = new MySql(sql,vals);
        try {
            ResultSet rs =db.pst.executeQuery();
            if(rs.next()){
                //设置cookie 和 session
                Cookie un = new Cookie("username",username);
                Cookie token = new Cookie("token",DigestUtils.sha1Hex(username+password));
                Cookie maxdisk = null;
                HttpSession session = request.getSession(true);
                int DEBUG = Integer.parseInt(CL.GetValueByKey("DEBUG"));
                if(DEBUG==1){
                    session.setAttribute("DEBUG",1);
                }
                session.setAttribute("login","true");
                session.setAttribute("username",username);
                session.setAttribute("token",token);
                String get_maxdisk = "SELECT maxdisk from files,user where username=?";
                db.insert(get_maxdisk,username);
                ResultSet rs1= db.pst.executeQuery();
                if(rs1.next()){
                    session.setAttribute("maxdisk",rs1.getDouble("maxdisk"));
                    maxdisk = new Cookie("maxdisk",rs1.getString("maxdisk"));
                }
                un.setMaxAge(60*60*48);
                token.setMaxAge(60*60*48);
                maxdisk.setMaxAge(60*60*48);
                response.addCookie(un);
                response.addCookie(token);
                response.addCookie(maxdisk);
                response.getWriter().write("success");
                //response.sendRedirect("/");
            }else {
                response.getWriter().write("false");
                //response.sendRedirect("/");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
