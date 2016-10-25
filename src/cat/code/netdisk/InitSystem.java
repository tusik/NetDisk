package cat.code.netdisk;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/22.
 *
 * @author Image
 */

@WebServlet(name = "InitSystem" , urlPatterns = "/InitSystem")

public class InitSystem extends HttpServlet{

    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException{
        String dbname=request.getParameter("dbname");
        String dbuser=request.getParameter("dbuser");
        String dbpw=request.getParameter("dbpw");
        String adminname=request.getParameter("adminname");
        String adminpw=request.getParameter("adminpw");
        String salt=request.getParameter("salt");

        if(configSetting(dbname,dbuser,dbpw,adminname,salt)){
            adminpw=DigestUtils.sha256Hex(adminpw+salt);
            createDatabase();
            createAdmin(adminname,adminpw);
            File oldfile = new File(getServletContext().getRealPath("/")+"Init.jsp");
            File newfile = new File(getServletContext().getRealPath("/")+"Init-"
                    + RandomStringUtils.randomAlphanumeric(6).toString()+".jsp");
            oldfile.renameTo(newfile);
            createFileDir(adminname);
            response.sendRedirect("/");
        }else {
            PrintWriter out= response.getWriter();
            out.print("Initialization failed,please check does config file exist");
            out.flush();
            out.close();
        }

    }
    public void createFileDir(String username){
        File filedir = new File(getServletContext().getRealPath("/")+"files/"+username);
        filedir.mkdirs();
    }
    public boolean configSetting(String dbname,String dbuser,String dbpw,
                                 String adminname, String salt){
        try {

            String content = "dbname="+dbname+
                    "\ndbuser="+dbuser+
                    "\ndbpw="+dbpw+
                    "\nadminname="+adminname+
                    "\nsalt="+salt;

            File file = new File(getServletContext().getRealPath("/")+
                    "WEB-INF/.properties");
            if (!file.exists()) {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createDatabase(){
        MySql db=new MySql();
        String sql="CREATE TABLE `user`(" +
                "`id` int(11) NOT NULL AUTO_INCREMENT," +
                "`username` char(20) NOT NULL ," +
                "`password` char(64) NOT NULL ," +
                "`rank` int NOT NULL DEFAULT '0'," +
                "`filecount` int NOT NULL DEFAULT '0'," +
                "`diskused` double(16,2) NOT NULL DEFAULT '0.0'," +
                "`regdate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,"+
                "PRIMARY KEY (`id`))";
        db.insert(sql);
        try {
            db.pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean createAdmin(String adminname,String pw){
        MySql db = new MySql();
        String sql = "INSERT INTO `user` (username,password,rank)" +
                " VALUES" +
                "('"+adminname+"','"+pw+"','0')";
        db.insert(sql);
        try {
            db.pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }
}
