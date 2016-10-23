package cat.code.netdisk;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/23.
 *
 * @author Image
 */
@WebServlet(name="token",urlPatterns = "/token")
public class token extends HttpServlet{
    public String aa(HttpServletRequest request, HttpServletResponse response){
        String token=null;
        String username=null;
        Cookie cookie = null;
        Cookie[] cookies = null;
        cookies = request.getCookies();
        for(int i=0;i<cookies.length;i++){
            cookie=cookies[i];
            if(cookie.getName().equals("token")){
                token=cookie.getValue();
            }else if(cookie.getName().equals("username")){
                username=cookie.getValue();
            }
        }
        MySql db = new MySql();
        String sql = "SELECT `password` FROM `user` where username='"
                +username+"'";
        db.insert(sql);
        try {
            ResultSet rs = db.pst.executeQuery();
            if (rs.next()) {
                String checktoken = rs.getString("password");
                checktoken = DigestUtils.sha1Hex(username + checktoken);
                return token+" "+checktoken;
            }else {
                return username+token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "a";
        }
    }
}
