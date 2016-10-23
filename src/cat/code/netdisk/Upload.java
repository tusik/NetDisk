package cat.code.netdisk;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Created by zinc on 2016/10/21.
 *
 * @author Image
 */

@WebServlet(name="/UploadServlet",urlPatterns = "/UploadServlet")
@MultipartConfig

public class Upload extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        request.setCharacterEncoding("utf-8");
        Part part = request.getPart("myFile");
        String filename = getFilename(part);
        part.write("");
    }
    public String getFilename(Part part){
        if(part==null){return null;}
        String filename = part.getHeader("content-disposition");
        if(isBlank(filename)){
            return null;
        }
        return "a";
    }
    public static boolean isBlank(String str){
        int strlen = str.length();
        if(str==null||strlen==0){
            return true;
        }
        for(int i=0;i<strlen;i++){
            if(!Character.isWhitespace(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
