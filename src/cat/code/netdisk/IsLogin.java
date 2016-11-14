package cat.code.netdisk;

import javax.servlet.http.*;
import java.sql.ResultSet;
import java.sql.SQLException;


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
                session.setAttribute("diskused",getUsed(session));
                return 1;
            }else {
                return 2;
            }
        }else {
            if(token.equals(t.cookieToken())){
                session.setAttribute("diskused",getUsed(session));
                return 3;
            }else {
                return 4;
            }
        }
    }
    public double getUsed(HttpSession session){
        MySql db = new MySql();
        String sql = "SELECT diskused from files,user where username='"
                +session.getAttribute("username")+"'";
        db.insert(sql);
        try {
            ResultSet rs = db.pst.executeQuery();
            if(rs.next()){
                return rs.getDouble("diskused");
            }else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
