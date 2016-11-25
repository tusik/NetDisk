package cat.code.netdisk.admin;

import cat.code.netdisk.MySql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zinc on 2016/11/25.
 *
 * @author Image
 */
public class ConfigFromSQL {
    public static String GetValues(String val){
        String sql = "SELECT cvalue FROM config WHERE cfield=?";
        MySql db = new MySql(sql,val);
        ResultSet rs=null;
        try {
            rs=db.pst.executeQuery();
            while (rs.next()){
                return rs.getString("cvalue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }finally {
            db.close(rs);
        }
        return "empty";
    }
}
