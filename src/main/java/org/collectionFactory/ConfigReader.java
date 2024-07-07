package org.collectionFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {
    private static final String CONFIG_FILE_PATH = ".env";
    private static final Map<String, String> properties = new HashMap<>();

    static {
        loadConfig();
    }

    private static void loadConfig() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            prop.load(fis);
            for (String key : prop.stringPropertyNames()) {
                properties.put(key, prop.getProperty(key));
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.get(key);
    }

    // Optional: Add a method to retrieve all properties as a map
    public static Map<String, String> getAllProperties() {
        return properties;
    }
}


