package com.elvira.core.browser;

import com.elvira.core.config.FrameworkConfig;
import com.microsoft.playwright.*;

public class BrowserTypeResolver {

    public static Browser launch(Playwright playwright) {

        BrowserType.LaunchOptions options =
                new BrowserType.LaunchOptions()
                        .setHeadless(FrameworkConfig.headless());

        return switch (FrameworkConfig.browser()) {

            case "firefox" ->
                    playwright.firefox().launch(options);

            case "webkit" ->
                    playwright.webkit().launch(options);

            default ->
                    playwright.chromium().launch(options);
        };
    }
}
