package cat.code.netdisk;

import java.sql.*;

/**
 * Created by zinc on 2016/10/22.
 *
 * @author Image
 */
public class MySql {
    ConfigLoader CL = new ConfigLoader();
    public final String url = "jdbc:mysql://localhost/" +
            CL.GetValueByKey("dbname")+"?useUnicode=true&characterEncoding=utf-8";
    public final String name = "com.mysql.jdbc.Driver";
    public final String user = CL.GetValueByKey("dbuser");
    public final String password = CL.GetValueByKey("dbpw");
    public Connection conn = null;
    public PreparedStatement pst = null;
    String sql = null;

    public MySql() {}
    public MySql(String sql,String[] vals) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
            for(int i=0;i<=vals.length;i++){
                pst.setString(i+1,vals[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public MySql(String sql,String val) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
            pst.setString(1,val);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }//
    }
    public void insert(String sql,String[] vals) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
            for(int i=0;i<vals.length;i++){
                pst.setString(i+1,vals[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insert(String sql,String val) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
            pst.setString(1,val);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if(this.conn!=null){
                this.conn.close();
            }
            if(this.pst!=null){
                this.pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void close(ResultSet rs) {
        close();
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}