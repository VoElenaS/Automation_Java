package org.example.utils;

import java.io.IOException;
import java.util.Properties;

public class TestProperties {
    public static Properties properties = new Properties();

    static {
        try {
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static void loadProperties() throws IOException {
        properties.load(TestProperties.class.getClassLoader().getResourceAsStream("endpoints.properties"));
        properties.load(TestProperties.class.getClassLoader().getResourceAsStream("test_env.properties"));
    }

    public static void main(String[] args) throws IOException {
        loadProperties();
        System.out.println(properties);
    }
}
