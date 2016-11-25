package cat.code.netdisk.admin;

import cat.code.netdisk.MySql;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/11/19.
 *
 * @author Image
 */
public class IsAdmin extends HttpServlet{
    private int rank;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    public IsAdmin(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        if(session.getAttribute("rank")!=null){
            rank = (int) session.getAttribute("rank");
        }
    }
    public int isAdmin(){
        if(rank==0) return 1;
        else return 0;
    }
    public void getUsed(HttpServletRequest request){
        String sql ="SELECT SUM(diskused) from files";
        MySql db = new MySql();
        db.insert(sql);
        ResultSet rs= null;
        try {
            rs = db.pst.executeQuery();
            if(rs.next()){
                HttpSession session = request.getSession();
                session.setAttribute("allused",rs.getDouble(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(rs);
        }
    }
}
