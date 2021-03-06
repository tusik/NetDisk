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
                setUsed(session);
                setDebug(session);
                return 1;
            }else {
                return 2;
            }
        }else {
            if(token.equals(t.cookieToken())){
                setUsed(session);
                setDebug(session);
                return 3;
            }else {
                return 4;
            }
        }
    }
    public void setUsed(HttpSession session){
        MySql db = new MySql();
        String sql = "SELECT user.id,diskused,maxdisk,filecount,rank " +
                "from files join user on user.id=files.id where username=?";
        db.insert(sql,(String)session.getAttribute("username"));
        try {
            ResultSet rs = db.pst.executeQuery();
            if(rs.next()){
                session.setAttribute("maxdisk",rs.getDouble("maxdisk"));
                session.setAttribute("diskused",rs.getDouble("diskused"));
                session.setAttribute("filecount",rs.getDouble("filecount"));
                session.setAttribute("rank",rs.getInt("rank"));
                session.setAttribute("uid",rs.getInt("id"));

            }else {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }
    public void setDebug(HttpSession session){
        ConfigLoader CL = new ConfigLoader();
        int DEBUG = Integer.parseInt(CL.GetValueByKey("DEBUG"));
        if(DEBUG==1){
            session.setAttribute("DEBUG",1);
        }
    }
}
