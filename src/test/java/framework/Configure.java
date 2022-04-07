package framework;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

@Log4j2
public class Configure {
    private static Properties properties;
    public static String RESOURCE_PATH;
    public static String BROWSER;

    static {
        try {
            RESOURCE_PATH = new File("src/test/resources").getAbsolutePath();
            properties = new Properties();
            loadProperties(RESOURCE_PATH + "/application.properties");
            if (System.getProperty("browser") == null) {
                if (getAppProp("browser") == null) {
                    throw new Exception("Не задан параметр browser");
                } else BROWSER = getAppProp("browser").toLowerCase();
            } else
                BROWSER = System.getProperty("browser").toLowerCase();
            log.info("SET browser = " + BROWSER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadProperties(String file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            properties.load(fileInputStream);
        } catch (Exception e) {
            log.warn("Can't load environment properties file : " + e.getMessage());
        }
    }

    public static String getAppProp(String propertyKey) {
        return properties.getProperty(propertyKey);
    }
}
