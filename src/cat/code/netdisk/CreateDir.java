package cat.code.netdisk;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zinc on 2016/10/26.
 *
 * @author Image
 */
@WebServlet(name = "CreateDir",urlPatterns = "/CreateDir")
public class CreateDir extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("login").equals("true")){
            String dirname = request.getParameter("dname");
            if(createDir(dirname, (String) session.getAttribute("username"))){
                response.sendRedirect(request.getHeader("Referer"));
            }else {
                response.getWriter().write("<script>alert(\"illegal dir name\");</script>");
                response.sendRedirect(request.getHeader("Referer"));
            }
        }
    }
    public boolean createDir(String dirname,String username){

        if(dirCheck(dirname)){
            String filedir = this.getClass().getClassLoader().getResource("../../WEB-INF/files/"
                    +username+"/").getPath();
            filedir += dirname;
            File newdir = new File(filedir);
            if(!newdir.exists())newdir.mkdirs();
            return true;
        }else {
            return false;
        }

    }
    public boolean dirCheck(String dir){
        String pattern = "((.*)(\\.)(.*)|(.*)>(.*)|(.*)<(.*)|(.*)\\?(.*)|" +
                "(.*)(\\*)(.*)|(.*)(:)(.*)|(.*)(\\\\)(.*)|(.*)\\|(.*)|(.*)\"(.*))";
        //创建Pattern对象
        Pattern r = Pattern.compile(pattern);
        //创建matcher对象
        Matcher m = r.matcher(dir);
        if(m.find()){
            return false;
        } else{
            return true;
        }
    }
}
