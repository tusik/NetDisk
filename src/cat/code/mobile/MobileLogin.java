package cat.code.mobile;

import cat.code.netdisk.ConfigLoader;
import cat.code.netdisk.MySql;
import cat.code.netdisk.token;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/31.
 *
 * @author Image
 */
@WebServlet(name = "MobileLogin" , urlPatterns = "/MobileLogin")
public class MobileLogin extends HttpServlet{
    private String username;
    private String rawpassword;
    private String password;
    ConfigLoader CL = new ConfigLoader();
    private String salt=CL.GetValueByKey("salt");
    String jsondata=null;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        username=request.getParameter("username");
        rawpassword=request.getParameter("pw");
        MySql db = new MySql();
        token token =new token();
        password = DigestUtils.sha256Hex(rawpassword+salt);
        String sql="SELECT `username` FROM `user` where password='"+password
                +"' and username='"+username+"'";
        db.insert(sql);
        try {
            ResultSet rs=db.pst.executeQuery();
            if(rs.next()){
                jsondata="{\"login\":\"true\",\"username\":\""+username+
                        "\",\"token\":\""+ token.countToken(username,password)+"\"}";
                response.getWriter().write(jsondata);
            }else {
                username=null;
                jsondata="{\"login\":\"false\",\"username\":"+username+
                        ",\"token\":"+ token.countToken(username,password)+"}";
                response.getWriter().write(jsondata);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
