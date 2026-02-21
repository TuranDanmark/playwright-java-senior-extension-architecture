package com.elvira.core.config;

public class FrameworkConfig {

    public static String browser() {
        return System.getProperty("browser", "chromium");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }

    public static String baseUrl() {
        return System.getProperty("baseUrl", "https://demo.playwright.dev/todomvc");
    }

    public static int timeout() {
        return Integer.parseInt(System.getProperty("timeout", "30000"));
    }

}