package com.elvira.core.config;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class ConfigLoader {

    private static final String DEFAULT_FILE = "application.properties";
    private static final String ENV = System.getProperty("env", "local");

    private static final Properties PROPERTIES = load();

    private ConfigLoader() {
    }

    private static Properties load() {

        try {

            Properties properties = new Properties();

            // 1. load default
            try (InputStream defaultStream =
                         ConfigLoader.class.getClassLoader()
                                 .getResourceAsStream(DEFAULT_FILE)) {

                if (defaultStream != null) {
                    properties.load(defaultStream);
                }
            }

            // 2. load env-specific
            String envFile = "application-" + ENV + ".properties";

            try (InputStream envStream =
                         ConfigLoader.class.getClassLoader()
                                 .getResourceAsStream(envFile)) {

                if (envStream != null) {
                    properties.load(envStream);
                }
            }

            return properties;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static String get(String key) {

        // System property override
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            return systemValue;
        }

        String value = PROPERTIES.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Property not found: " + key
            );
        }

        return value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static String getOrDefault(String key, String defaultValue) {
        return Objects.requireNonNullElse(
                System.getProperty(key),
                PROPERTIES.getProperty(key, defaultValue)
        );
    }
}
