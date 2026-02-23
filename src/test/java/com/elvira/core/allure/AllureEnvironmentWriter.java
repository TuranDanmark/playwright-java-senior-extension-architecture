package com.elvira.core.allure;

import java.io.File;
import java.io.PrintWriter;

public class AllureEnvironmentWriter {

    static void writeEnvironmentInfo() {
    try {
        File resultsDir = new File("target/allure-results");
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }

        File envFile = new File(resultsDir, "environment.properties");

        try (PrintWriter writer = new PrintWriter(envFile)) {
            writer.println("Browser=Chromium");
            writer.println("Headless=" + System.getProperty("headless", "false"));
            writer.println("OS=" + System.getProperty("os.name"));
            writer.println("Java=" + System.getProperty("java.version"));
            writer.println("Environment=" + System.getProperty("env", "local"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    
    }
}