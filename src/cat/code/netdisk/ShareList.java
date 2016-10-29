package cat.code.netdisk;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by zinc on 2016/10/29.
 *
 * @author Image
 */

@WebServlet(name = "ShareList" , urlPatterns = "/Sharelist")
public class ShareList extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        String username = (String) session.getAttribute("username");
        request.setAttribute("codelist",getCodeList(username));
        request.setAttribute("namelist",getNameList(username));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Sharelist.jsp");
        dispatcher.forward(request, response);
    }
    public ArrayList getCodeList(String username){
        MySql db = new MySql();
        ArrayList list = new ArrayList();
        String sql = "SELECT code FROM `share` WHERE username='"+username+"'";
        db.insert(sql);
        try {
            ResultSet rs =db.pst.executeQuery();
            while(rs.next()){
                list.add(rs.getString("code"));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            db.close();
        }
    }
    public ArrayList getNameList(String username){
        MySql db = new MySql();
        ArrayList list = new ArrayList();
        String sql = "SELECT path FROM `share` WHERE username='"+username+"'";
        db.insert(sql);
        try {
            ResultSet rs =db.pst.executeQuery();
            while(rs.next()){
                String name =rs.getString("path");
                String[] tmp = name.split("/");
                list.add(tmp[tmp.length-1]);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            db.close();
        }
    }
}
