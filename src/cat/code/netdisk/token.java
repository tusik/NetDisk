package cat.code.netdisk;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/23.
 *
 * @author Image
 */
@WebServlet(name="token",urlPatterns = "/token")
public class token {
    String token=null;
    String username=null;
    String checktoken=null;

    public String getToken(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = null;
        Cookie[] cookies = null;

        if((cookies = request.getCookies())==null){return "false";}
        else if (cookies.length<1){return "false";}
        for(int i=0;i<cookies.length;i++){
            cookie=cookies[i];
            if(cookie.getName().equals("token")){
                token=cookie.getValue();
            }else if(cookie.getName().equals("username")){
                username=cookie.getValue();
            }
        }
        MySql db = new MySql();
        String sql = "SELECT `password` FROM `user` where username=?";
        db.insert(sql,username);
        ResultSet rs=null;
        try {
             rs = db.pst.executeQuery();
            if (rs.next()) {
                checktoken = rs.getString("password");
                checktoken = DigestUtils.sha1Hex(username + checktoken);
                if(token.equals(checktoken)){
                    return "true";
                }else {
                    return "false";
                }

            }else {
                return username+token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "a";
        }finally {
            db.close(rs);
        }
    }
    public void setSession(HttpSession session){
        session.setAttribute("login","true");
        session.setAttribute("username",username);
        session.setAttribute("token",token);
    }
    public String cookieToken(){
        return checktoken;
    }
    public String sessionToken(){
        return token;
    }
    public String countToken(String user,String password){
        if(user==null||password==null)return null;
        ConfigLoader CL=new ConfigLoader();
        String enpw=DigestUtils.sha256Hex(password+CL.GetValueByKey("salt"));
        return DigestUtils.sha1Hex(user + enpw);
    }
}
