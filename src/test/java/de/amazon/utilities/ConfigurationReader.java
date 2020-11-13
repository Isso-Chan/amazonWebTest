package de.amazon.utilities;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * reads the properties from the file, configuration.properties
 */
public class ConfigurationReader {

    private static Properties properties;

    // try the properties file, readable or not
    static {
        try {
            String path = "configuration.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get property from configuration properties file (properties) depend on the keyName
     *
     * @param keyName
     * @return
     */
    public static String get(String keyName) {
        return properties.getProperty(keyName);
    }

}
