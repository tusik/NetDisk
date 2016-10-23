package cat.code.netdisk;

/**
 * Created by zinc on 2016/10/2.
 */
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.URL;
import java.util.Properties;

public class ConfigLoader extends HttpServlet{


    public String GetValueByKey(String key)  {
        ConfigLoader CL=new ConfigLoader();
            Properties pps = new Properties();
            try {
                InputStream in = new BufferedInputStream(new FileInputStream((
                        this.getClass().getClassLoader().getResource("../.properties").getPath())));
                pps.load(in);
                String value = pps.getProperty(key);
                return value;
            }catch (IOException e) {
                e.printStackTrace();
                return null;
          }
    }


}
