package cat.code.netdisk.admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
        rank = (int) session.getAttribute("rank");
    }
    public int isAdmin(){
        if(rank==0) return 1;
        else return 0;
    }
}
