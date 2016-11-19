package cat.code.netdisk.admin;

import cat.code.netdisk.IsLogin;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * Created by zinc on 2016/11/17.
 *
 * @author Image
 */
@WebServlet( name = "ManageIndex",urlPatterns = "/admin")
public class Index extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request,response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        File file = new File(getServletContext().getRealPath("/")+"Init.jsp");
        if(file.exists()){
            response.sendRedirect("Init.jsp");
        }else {
            HttpSession session = request.getSession(true);
            IsLogin il=new IsLogin();
            int login=il.isLogin(session,request,response);
            if(login==1||login==3){
                IsAdmin ia=new IsAdmin(request);
                if(ia.isAdmin()==1){
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/Index.jsp");
                    dispatcher.forward(request, response);
                }else {
                    response.sendRedirect("/index.jsp");
                }
            }else {
                response.sendRedirect("/index.jsp");
            }
        }
    }
}
