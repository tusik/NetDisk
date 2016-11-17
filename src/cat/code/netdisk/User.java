package cat.code.netdisk;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/29.
 *
 * @author Image
 */
@WebServlet(name = "User" , urlPatterns = "/User")

public class User extends HttpServlet{
    private String username;
    private String rawpassword;
    private String salt;
    ConfigLoader CL = new ConfigLoader();

    public User(String username,String rawpassword){
        this.username=username;
        this.rawpassword=rawpassword;
    }
    public int create(){
        salt=CL.GetValueByKey("salt");
        MySql db = new MySql();
        String sql = "SELECT id FROM `user` WHERE username = ?";
        db.insert(sql,username);
        try {
            ResultSet rs = db.pst.executeQuery();
            if (rs.next()) {
                return 2;//username repeat
            } else {
                sql = "INSERT INTO `user` (username,password,rank)" +
                        "VALUES" +
                        "(?,?,1);";
                String insertUser[]={username,DigestUtils.sha256Hex(rawpassword + salt)};
                String sql1 =
                        "INSERT INTO `files`(maxdisk)VALUES('" + CL.GetValueByKey("size") + "')";
                db.insert(sql,insertUser);
                db.pst.execute();
                db.insert(sql1);
                db.pst.execute();
                createFileDir(username);
                return 1;//no error
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return 3;//database error
        }
    }
    public void createFileDir(String username){
        File filedir = new File(CL.GetValueByKey("basepath")
                +"WEB-INF/files/" + username);
        filedir.mkdirs();
    }
}
