package cat.code.netdisk;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zinc on 2016/10/25.
 *
 * @author Image
 */
@WebServlet(name = "Analysis" , urlPatterns = "/Analysis")
public class Analysis extends HttpServlet{
    public void doGet(HttpServletRequest request , HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request , HttpServletResponse response)
            throws IOException {
        String fromURL = String.valueOf(request.getRemoteAddr()+request.getRequestURI());
        if(fromURL==null)fromURL="aaat";
        response.getWriter().write(fromURL);
    }
}
