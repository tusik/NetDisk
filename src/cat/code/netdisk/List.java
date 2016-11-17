package cat.code.netdisk;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zinc on 2016/10/24.
 *
 * @author Image
 */
@WebServlet(name="List",urlPatterns = "/List")
public class List extends HttpServlet{
    ConfigLoader CL =new ConfigLoader();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException{

    }
    public void setList(HttpServletRequest request,HttpServletResponse response,String dir)
            throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding( "utf-8" );
        response.setContentType("charset=utf-8");
        HttpSession session = request.getSession();
        String username= (String) session.getAttribute("username");
        String filedir=this.getClass().getClassLoader().getResource("../../WEB-INF/files/"
                +username+"/").getPath();
        filedir=filedir+dir;
        ArrayList<String> dirlist = new ArrayList<>();
        ArrayList<String> filelist = new ArrayList<>();
        File f = new File(filedir);
        File[] files = f.listFiles();
        if(files.length>0)
        for (int i=0;i<files.length;i++){
            if(files[i].isDirectory()){
                dirlist.add(files[i].getName());
            }else if(files[i].isFile()){
                filelist.add(files[i].getName());
            }
        }
        setRequest(request,dirlist,filelist);
    }
    public void setListManage(HttpServletRequest request,HttpServletResponse response,String dir)
            throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding( "utf-8" );
        response.setContentType("charset=utf-8");
        String filedir=CL.GetValueByKey("basepath")+"WEB-INF/files/";
        if(dir==null)dir="";
        filedir=filedir+dir;
        ArrayList<String> dirlist = new ArrayList<>();
        ArrayList<String> filelist = new ArrayList<>();
        File f = new File(filedir);
        File[] files = f.listFiles();
        response.getWriter().write(filedir);

        if(files.length>0)
            for (int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    dirlist.add(files[i].getName());
                }else if(files[i].isFile()){
                    filelist.add(files[i].getName());
                }
            }
        setRequest(request,dirlist,filelist);
    }

    public void setRequest(HttpServletRequest request,ArrayList dirlist,ArrayList filelist){
        request.setAttribute("dirlist",dirlist);
        request.setAttribute("filelist",filelist);

    }
}
