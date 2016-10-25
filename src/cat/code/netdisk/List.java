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

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException{

    }
    public void setList(HttpSession session){
        String username= (String) session.getAttribute("username");
        String filedir=this.getClass().getClassLoader().getResource("../../files/"
                +username).getPath();
        ArrayList<String> dirlist = new ArrayList<>();
        ArrayList<String> filelist = new ArrayList<>();
        File f = new File(filedir);
        File[] files = f.listFiles();
        for (int i=0;i<files.length;i++){
            if(files[i].isDirectory()){
                dirlist.add(files[i].getName());
            }else if(files[i].isFile()){
                filelist.add(files[i].getName());
            }
        }
        setSession(session,dirlist,filelist);
    }

    public void setSession(HttpSession session,ArrayList dirlist,ArrayList filelist){
        session.setAttribute("dirlist",dirlist);
        session.setAttribute("filelist",filelist);

    }
}
