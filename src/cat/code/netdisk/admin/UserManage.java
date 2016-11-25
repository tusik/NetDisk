package cat.code.netdisk.admin;

import cat.code.netdisk.MySql;
import cat.code.netdisk.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by zinc on 2016/11/17.
 *
 * @author Image
 */
@WebServlet(name = "UserManage",urlPatterns = "/admin/UserManage")
public class UserManage extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        IsAdmin ia = new IsAdmin(request);
        if(request.getParameter("del_id")!=null) {
            int del_id= Integer.parseInt(request.getParameter("del_id"));
            new User(del_id).del();
        }
        if(ia.isAdmin()==1){

            request.setAttribute("users",getUser());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/UserManage.jsp");
            dispatcher.forward(request, response);
        }
    }
    public ArrayList getUser(){
        String selectuser = "SELECT * FROM user";
        MySql db = new MySql();
        ArrayList <User> users=new ArrayList<>();
        db.insert(selectuser);
        try {
            ResultSet rs = db.pst.executeQuery();
            while(rs.next()){
                users.add(new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"),
                        rs.getInt("rank"),rs.getString("regdate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
