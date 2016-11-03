package cat.code.netdisk;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by zinc on 2016/10/21.
 *
 * @author Image
 */

@WebServlet(name="/UploadServlet",urlPatterns = "/UploadServlet")
@MultipartConfig

public class Upload extends HttpServlet{
    ConfigLoader CL= new ConfigLoader();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding( "utf-8" );
        response.setContentType("charset=utf-8");
        String dir=request.getParameter("dir");
        HttpSession session=request.getSession();
        Part part = request.getPart("myFile");
        String filename = java.net.URLDecoder.decode((String)request.getParameter("filename"), "UTF-8");;
        byte[] str = StringEscapeUtils.unescapeHtml3(filename).getBytes();
        //filename = str.toString();
        if(filename!=null){
            if(dir!=null)dir+="/";
            else dir="";
            String path=CL.GetValueByKey("basepath")+"WEB-INF/files/"+
                    session.getAttribute("username")+
                    "/"+dir+filename;
            part.write(path);
        }
        response.sendRedirect(request.getHeader("Referer"));
    }

}
