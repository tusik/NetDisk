package cat.code.netdisk;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zinc on 2016/10/23.
 *
 * @author Image
 */
@WebServlet(name = "Panle" , urlPatterns = "/Panel")
public class Panel extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        IsLogin il=new IsLogin();
        //if(il.check(request)){

        //}else{
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().write(" <script type=\"text/javascript\" >alert(\"请先登陆\");</script>\n");
        //}
    }
}
