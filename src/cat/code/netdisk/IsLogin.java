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

    String token = null;
    String username = null;
    Cookie cookie = null;
    Cookie[] cookies = null;


    public IsLogin(){}
    public int isLogin(HttpSession session,
                       HttpServletRequest request,HttpServletResponse response){
        token t=new token();
        String token = t.sessionToken();
        String tokenValuable=t.getToken(request,response);

        if(token==null){

            if(tokenValuable.equals("true")){
                t.setSession(session);
                return 1;
            }else {
                return 2;
            }
        }else {
            if(token.equals(t.cookieToken())){
                return 3;
            }else {
                return 4;
            }
        }
    }

}
