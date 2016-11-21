package cat.code.netdisk;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/21.
 *
 * @author Image
 */

@WebServlet(name="/UploadServlet",urlPatterns = "/UploadServlet")
@MultipartConfig

public class Upload extends HttpServlet{
    ConfigLoader CL= new ConfigLoader();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding( "utf-8" );
        response.setContentType("charset=utf-8");
        String dir=request.getParameter("dir");
        HttpSession session=request.getSession();
        Part part = request.getPart("myFile");
        String filename =request.getParameter("filename");
        if(filename!=null){
            if(dir!=null)dir+="/";
            else dir="";
            String path=CL.GetValueByKey("basepath")+"WEB-INF/files/"+
                    session.getAttribute("username")+
                    "/"+dir+filename;
            if(chechUsed(part.getSize()/1024.0/1024.0,
                    (String) session.getAttribute("username"))==1){
                part.write(path);
                setUsed(part.getSize()/1024.0/1024.0,
                        (String) session.getAttribute("username"));
                response.sendRedirect(request.getHeader("Referer"));
            }else {
                response.sendRedirect(request.getHeader("Referer")+"?error=out");
            }

        }else {
            response.sendRedirect(request.getHeader("Referer")+"?error=empty");
        }
    }
    //增加使用量
    public void setUsed(double size,String username){
        MySql db = new MySql();
        String sql = "UPDATE files,user set filecount=filecount+1,diskused=diskused+"
                +size+" where username='"+username+"'";
        db.insert(sql);
        try {
            db.pst.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }
    //检查使用量
    public int chechUsed(double size,String username){
        MySql db = new MySql();
        String sql = "SELECT diskused,maxdisk from files,user where username='"+username+"'";
        ResultSet rs=null;
        try {
            db.insert(sql);
            rs =db.pst.executeQuery();
            if(rs.next()){
                if(size+rs.getDouble("diskused")>rs.getDouble("maxdisk")){
                    return -1;
                }else {
                    return 1;
                }
            }else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 999;
        }finally {
            db.close(rs);
        }
    }

}
