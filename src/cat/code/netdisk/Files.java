package cat.code.netdisk;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
    private int view;
    String url;
    String id;
    String path;
    String username = null;
    String filedir = null;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding( "utf-8" );
        response.setContentType("charset=utf-8");
        if(request.getParameter("view")!=null)
            view=Integer.parseInt(request.getParameter("view"));
        url=request.getParameter("url");
        id=request.getParameter("id");
        path = null;

        if(url==null&&id!=null){
            //分享文件下载
            getShareFile(request,response);
        }else {
           getUserFile(request,response);
        }

    }
    public void downloadLocal(HttpServletRequest request,HttpServletResponse response,
                              String filepath,String fileName)
            throws IOException, ServletException {
        //下载本地文件
        //读到流中
        InputStream inStream  =   new  FileInputStream( filepath ); //文件的存放路径
        //设置输出的格式
        if(view!=1){
            response.reset();
            response.setContentType("bin");
            response.addHeader( "Content-Disposition" , "attachment; filename=\"" +fileName+ "\"");
        }else if(view==1){
            response.reset();
            response.setContentType("");

        }
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
    public void getShareFile(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {
        String sql = "SELECT path,username FROM `share` WHERE code=?";
        MySql db = new MySql(sql,id);
        ResultSet rs=null;
        try {
            rs =db.pst.executeQuery();
            if(rs.next()){
                path =rs.getString("path");
                username=rs.getString("username");
                filedir=CL.GetValueByKey("basepath")+"WEB-INF/files/"
                        +username+path;
            }
            File file = new File(filedir);
            String s[]=path.split("/");
            if(file.exists()){
                sql="UPDATE share set downcount=downcount+1 WHERE code=?";
                MySql db1 = new MySql(sql,id);
                db1.pst.execute();
                db1.close();
                //下载文件
                downloadLocal(request,response,filedir,s[s.length-1]);

            }else {
                response.setStatus(404);
                response.sendRedirect("/WEB-INF/404.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(rs);
        }
    }
    public void getUserFile(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {
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
                downloadLocal(request,response,filedir,s[s.length-1]);
            }else {
                //response.getWriter().write(filedir);
                response.setStatus(404);
                response.sendRedirect("/WEB-INF/404.jsp");

            }
        }
    }
}
