package itsecurity.group5.common.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHelper {
    public static void loadProperties() {
        try {
            Properties properties = new Properties();
            InputStream stream = PropertyHelper.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(stream);
            stream.close();

            for (Object key : properties.keySet()) {
                System.setProperty(key.toString(), properties.getProperty(key.toString()));
            }
        } catch (IOException e) {
            System.out.println("Error loading properties");
            System.exit(-1);
        }
    }
}
