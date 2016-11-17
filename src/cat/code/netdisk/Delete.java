package cat.code.netdisk;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/27.
 *
 * @author Image
 */

@WebServlet(name = "Delete",urlPatterns = "/Delete")
public class Delete extends HttpServlet{
    ConfigLoader CL = new ConfigLoader();
    String base = CL.GetValueByKey("basepath");
    String username;
    long sss;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        String path = request.getParameter("path");
        String admin = request.getParameter("admin");
        if(admin==null){
            HttpSession session = request.getSession();
            username = (String) session.getAttribute("username");
            File file = new File(base + "WEB-INF/files/"+username+"/"+path);
            deleteAllFilesOfDir(file);
            //response.getWriter().write(file.getPath().replaceAll(base,"%"));
            response.sendRedirect(request.getHeader("Referer"));
            //response.getWriter().write(path);
        }else if(admin.equals("true")){
            deleteFromAdmin(request,response,path);
        }

    }

    public void deleteFromAdmin(HttpServletRequest request,HttpServletResponse response
            ,String path)
            throws IOException {
        File file = new File(base + "WEB-INF/files/"+path);
        deleteAllFilesOfDir(file);
        response.sendRedirect(request.getHeader("Referer"));
    }
    public void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            sss=path.length();
            releaseDisk(path.length()/1024.0/1024.0,username);
            path.delete();
            canelSharing(path.getPath().replaceAll(base+"WEB-INF/files/"+username,"%"));
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
        canelSharing(path.getPath().replaceAll(base+"WEB-INF/files/"+username,"%"));
    }
    public void releaseDisk(double size,String username){
        MySql db = new MySql();
        String sql = "UPDATE files,user set filecount=filecount-1,diskused=diskused-"
                +size+" where username='"+username+"'";
        db.insert(sql);
        try {
            db.pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void canelSharing(String path){
        String sql ="DELETE FROM `share` WHERE path like ?";
        MySql db = new MySql(sql,path);
        try {
            db.pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
