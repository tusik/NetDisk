package cat.code.netdisk;

import javax.servlet.http.*;


/**
 * Created by zinc on 2016/10/23.
 *
 * @author Image
 */
public class IsLogin {


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
