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

    String dbname=null;
    String dbuser=null;
    String dbpw=null;
    String adminname=null;
    String adminpw=null;
    String salt=null;
    String size=null;
    String[] regopen=null;
    String title=null;
    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException{
        dbname=request.getParameter("dbname");
        dbuser=request.getParameter("dbuser");
        dbpw=request.getParameter("dbpw");
        adminname=request.getParameter("adminname");
        adminpw=request.getParameter("adminpw");
        String adminpw1=request.getParameter("adminpw1");
        salt=request.getParameter("salt");
        size=request.getParameter("size");
        regopen=request.getParameterValues("reg");
        title=request.getParameter("title");

        if(adminpw1.equals(adminpw)){
            String c=configSetting(dbname,dbuser,dbpw,adminname,salt,size);
            if(c.equals("1")){
                adminpw=DigestUtils.sha256Hex(adminpw+salt);
                if(!createDatabase())response.getWriter().write("database creating error");
                createAdmin(adminname,adminpw);
                File oldfile = new File(getServletContext().getRealPath("/")+"Init.jsp");
                File newfile = new File(getServletContext().getRealPath("/")+"Init-"
                        + RandomStringUtils.randomAlphanumeric(6).toString()+".jsp");
                oldfile.renameTo(newfile);
                createFileDir(adminname);
                setConfig();
                response.sendRedirect("/");
            }else if(c.equals("2")){
                PrintWriter out= response.getWriter();
                out.print("Initialization failed,please check does config file exist");
                out.flush();
                out.close();
            }else {
                PrintWriter out= response.getWriter();
                out.print(c);
                out.flush();
                out.close();
            }
        }else {
            response.sendRedirect(request.getHeader("Refer"));
        }
    }
    public void createFileDir(String username){
        File filedir = new File(getServletContext().getRealPath("/")+
                "WEB-INF/files/"+username);
        filedir.mkdirs();
    }
    public String configSetting(String dbname,String dbuser,String dbpw,
                                 String adminname, String salt,String size){
        try {
            String content = "DEBUG=0" +
                    "\ndbname="+dbname+
                    "\ndbuser="+dbuser+
                    "\ndbpw="+dbpw+
                    "\nadminname="+adminname+
                    "\nsalt="+salt+
                    "\nsize="+size+
                    "\nbasepath="+getServletContext().getRealPath("/");
            if(regopen!=null){
                content+="\nregopen=true";
            }else {
                content+="\nregopen=false";
            }
            File file = new File(getServletContext().getRealPath("/")+
                    "WEB-INF/.properties");
            if (!file.exists()) {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                return "1";
            }else{
                return "2";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public boolean createDatabase(){
        ConfigLoader CL= new ConfigLoader();
        MySql db=new MySql();
        String sql="CREATE TABLE `user`(" +
                "`id` int NOT NULL AUTO_INCREMENT," +
                "`username` varchar(20) NOT NULL ," +
                "`password` char(64) NOT NULL ," +
                "`rank` tinyint NOT NULL DEFAULT '0'," +
                "`regdate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,"+
                "PRIMARY KEY (`id`))";
        String sql1=
                "CREATE TABLE `files`("+
                "`id` int NOT NULL AUTO_INCREMENT,"+
                "`filecount` int(11) NOT NULL DEFAULT '0' ,"+
                "`diskused` decimal(8,2) NOT NULL DEFAULT '0.0',"+
                "`maxdisk` decimal(8,2) NOT NULL DEFAULT '"+ CL.GetValueByKey("size")+"',"+
                "PRIMARY KEY (`id`))";
        String sql2= "CREATE TABLE `share`(" +
                "`id` int(11) NOT NULL AUTO_INCREMENT,"+
                "`path` varchar(500) NOT NULL," +
                "`code` char(6) NOT NULL,"+
                "`username` varchar(20) NOT NULL," +
                "`downcount` int NOT NULL DEFAULT '0'," +
                "`time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP," +
                "`del` tinyint NOT NULL DEFAULT '0'," +
                "PRIMARY KEY(`id`))";

        String sql3="CREATE TABLE `config` (" +
                " id int NOT NULL AUTO_INCREMENT," +
                " cfield char(25) NOT NULL," +
                " cvalue char(50) NOT NULL," +
                " date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                " PRIMARY KEY(`id`))";

        try {
            db.insert(sql);
            db.pst.executeUpdate();
            db.insert(sql1);
            db.pst.executeUpdate();
            db.insert(sql2);
            db.pst.executeUpdate();
            db.insert(sql3);
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
                "('"+adminname+"','"+pw+"','0');";
        String sql1=
                "INSERT INTO `files` (maxdisk) VALUES('"+ size+"')";
        db.insert(sql);
        try {
            db.pst.executeUpdate();
            db.insert(sql1);
            db.pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }
    public void setConfig(){
        String sql = "INSERT INTO config (cfield,cvalue)" +
                "VALUES" +
                "('title',?)," +
                "('salt',?)," +
                "('DEBUG',?)," +
                "('regopen',?)";
        String[] vals={title,salt,"0","close"};
        if(regopen!=null){
            vals[3]="open";
        }
        MySql db = new MySql();
        db.insert(sql,vals);
        try {
            db.pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }
}
