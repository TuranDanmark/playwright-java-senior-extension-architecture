package com.elvira.core.browser;

import com.microsoft.playwright.*;

public class PlaywrightManager {

    private static Playwright playwright;
    private static Browser browser;

    private PlaywrightManager() {
        // prevent instantiation
    }

    public static Playwright getPlaywright() {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        return playwright;
    }

    public static Browser getBrowser() {
        if (browser == null) {
            browser = getBrowser();
        }
        return browser;
    }


    public static void shutdown() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
