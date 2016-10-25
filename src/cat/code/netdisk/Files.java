package cat.code.netdisk;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zinc on 2016/10/25.
 *
 * @author Image
 */
@WebServlet(name = "Files" , urlPatterns = "/Files")
public class Files extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        String url=request.getParameter("url");
        String s[]=url.split("/");
        String username=s[0];
        HttpSession session=request.getSession();
        if(username.equals(session.getAttribute("username"))){
            String dir=new String();
            if(s.length>0)
                for(int i=1;i<s.length;i++)dir=dir+"/"+s[i];
            response.getWriter().write(dir);
        }

    }
}
