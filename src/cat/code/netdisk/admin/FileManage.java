package cat.code.netdisk.admin;

import cat.code.netdisk.List;

import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "FileManage",urlPatterns = "/admin/FileManage")
public class FileManage extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String dir=request.getParameter("dir");
        new List().setListManage(request,response,dir);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/FileManage.jsp");
        dispatcher.forward(request, response);
    }
}
