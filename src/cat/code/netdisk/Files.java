package cat.code.netdisk;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by zinc on 2016/10/25.
 *
 * @author Image
 */
@WebServlet(name = "GetFiles" , urlPatterns = "/GetFiles")
public class Files extends HttpServlet {
    ConfigLoader CL = new ConfigLoader();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        String url=request.getParameter("url");
        String id=request.getParameter("id");
        String path = null;
        String username = null;
        String filedir = null;

        if(url==null&&id!=null){
            MySql db = new MySql();
            ArrayList list = new ArrayList();
            String sql = "SELECT path,username FROM `share` WHERE code='"+id+"'";
            db.insert(sql);
            try {
                ResultSet rs =db.pst.executeQuery();
                if(rs.next()){
                    path =rs.getString("path");
                    username=rs.getString("username");
                    filedir=CL.GetValueByKey("basepath")+"WEB-INF/files/"
                            +username+path;
                }
                File file = new File(filedir);
                String s[]=path.split("/");
                if(file.exists()){
                    //下载文件
                    downloadLocal(response,filedir,s[s.length-1]);
                }else {
                    response.setStatus(404);
                    response.sendRedirect("/WEB-INF/404.jsp");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                db.close();
            }
        }else {
            String s[]=url.split("/");
            username=s[0];
            filedir="null";
            HttpSession session=request.getSession();
            //判断session中的用户名是否和url中的相同
            if(username.equals(session.getAttribute("username"))){
                String dir=new String();
                if(s.length>0)
                    for(int i=1;i<s.length;i++)dir=dir+"/"+s[i];
                try{
                    //获取真实文件地址
                    filedir=CL.GetValueByKey("basepath")+"WEB-INF/files/"
                            +username+"/"+dir;
                }catch (Exception e){
                    e.printStackTrace();
                }
                File file = new File(filedir);
                if(file.exists()){
                    //下载文件
                    downloadLocal(response,filedir,s[s.length-1]);
                }else {
                    response.setStatus(404);
                    response.sendRedirect("/WEB-INF/404.jsp");
                }
            }
        }

    }
    public void downloadLocal(HttpServletResponse response,String filepath,String fileName)
            throws  FileNotFoundException {
        //下载本地文件
        //读到流中
        InputStream inStream = new FileInputStream(filepath); //文件的存放路径
        //设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader( "Content-Disposition" , "attachment; filename=\""
                +fileName+ "\"");
        //循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try{
            while((len = inStream.read(b))> 0)
                response.getOutputStream().write(b,0,len);
            inStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
