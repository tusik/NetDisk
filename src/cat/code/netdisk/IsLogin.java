package cat.code.netdisk;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/23.
 *
 * @author Image
 */
public class IsLogin {
    boolean isLogin=false;
    public IsLogin(){}
    public boolean check(HttpServletRequest request){
       HttpSession session = request.getSession(true);
        String token = null;
        String username = null;
       if(session.getAttribute("token")==null){
           isLogin=false;
           return isLogin;
       }else{
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
           if(token==null||username==null){
               return isLogin;
           }else {
               MySql db = new MySql();
               String sql = "SELECT `password` FROM `user` where username='"
                       +username+"'";
               db.insert(sql);
               try {
                   ResultSet rs = db.pst.executeQuery();
                   if(rs.next()){
                       String checktoken = rs.getString("password");
                       checktoken= DigestUtils.sha1Hex(username+checktoken);
                       if(token.equals(checktoken)){
                           isLogin=true;
                           return isLogin;
                       }else{
                           return isLogin;
                       }
                   }else {return isLogin;}
               } catch (SQLException e) {
                   e.printStackTrace();
                   return isLogin;
               }
           }
       }
    }
}
