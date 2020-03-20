package clientlogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Cookies {


    private static Properties props = null;
    private static final String DIR_PATH = System.getProperty("user.home") + "/.ss13";
    private static final String FILE_PATH = DIR_PATH + "/cookies.properties";

    public static String getCookie(String key) {
        if (props == null) {
            loadProperties();

        }
        return props.getProperty(key);
    }


    public static void setCookie(String key, String value) {
        if (props == null) {
            loadProperties();
        }
        props.setProperty(key, value);
        try {
            props.store(new FileWriter(new File(FILE_PATH)), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void loadProperties() {
        File file = new File(DIR_PATH);
        if (!file.exists()) {
                boolean succ = file.mkdirs();
                if (!succ) {
                    System.err.println("Could not create .ss13 directory.");
                    return;
                }
        }

        props = new Properties();
        File propFile = new File(FILE_PATH);
        if (propFile.exists()) {
            try {

                props.load(new FileInputStream(propFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
