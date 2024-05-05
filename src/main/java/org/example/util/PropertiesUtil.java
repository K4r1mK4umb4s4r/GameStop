package org.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private final Properties properties = new Properties();
    public PropertiesUtil(String fileName) {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
