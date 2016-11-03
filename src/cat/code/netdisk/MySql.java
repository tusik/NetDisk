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

    public MySql() {
        super();
    }

    public void insert(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}