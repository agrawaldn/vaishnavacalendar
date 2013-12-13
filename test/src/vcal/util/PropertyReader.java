package vcal.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private Properties prop;
    private static PropertyReader instance = null;

    private PropertyReader() throws FileNotFoundException, IOException {
        prop = new Properties();
        prop.load(new FileInputStream("config.properties"));
    }

    public static PropertyReader getInstance() {
        if (instance == null) {
            try {
                instance = new PropertyReader();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
