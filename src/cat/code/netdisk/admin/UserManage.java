package cat.code.netdisk.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    }
}
