package cat.code.netdisk;

import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/10/29.
 *
 * @author Image
 */
@WebServlet(name = "Share" , urlPatterns = "/Share")

public class FileShares extends HttpServlet{
    String username;
    String path;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        IsLogin il=new IsLogin();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding( "utf-8" );
        response.setContentType("charset=utf-8");
        HttpSession session = request.getSession();
        String del = request.getParameter("del");
        int login=il.isLogin(session,request,response);
        if(login==1||login==3) {
            username = request.getParameter("user");
            path = request.getParameter("path");
            if(del!=null){
                response.getWriter().write(del);
                if(del.equals("1")) {
                    String id = request.getParameter("id");
                    canelShare(id);
                }
            } else {
                fileShare(response);
            }

            //response.sendRedirect(request.getHeader("Referer"));
        }else {
            response.getWriter().write(login);
            //response.sendRedirect(request.getHeader("/"));

        }
    }
    public void fileShare(HttpServletResponse response)
            throws IOException {
        MySql db = new MySql();
        String id= RandomStringUtils.randomAlphanumeric(6);
        String check0 = "SELECT code FROM `share` WHERE path='"+path+"'";
        db.insert(check0);
        ResultSet rs;
        try {
            rs=db.pst.executeQuery();
            if(rs.next()){

            }else {
                String check = "SELECT code FROM `share` WHERE code='"+id+"'";
                db.insert(check);
                    rs = db.pst.executeQuery();
                    while(rs.next()){
                        id= RandomStringUtils.randomAlphanumeric(6);
                        check = "SELECT code FROM `share` WHERE code='"+id+"'";
                        db.insert(check);
                        rs = db.pst.executeQuery();
                    }

                String sql = "INSERT INTO `share` (code,path,username)VALUES('"+
                        id+"','"+path+"','"+username+"')";
                db.insert(sql);
                db.pst.executeUpdate();
                response.getWriter().write(path);
            }
        } catch (SQLException e) {
            response.getWriter().write(e.toString());
        }finally {
            db.close();
        }
    }
    public void canelShare(String id){
        MySql db = new MySql();
        String sql ="DELETE FROM `share` WHERE code='"+id+"'";
        db.insert(sql);
        try {
            db.pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
