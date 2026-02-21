package com.elvira.core.browser;

import com.microsoft.playwright.*;
import com.elvira.core.config.FrameworkConfig;

import java.nio.file.Paths;

public class BrowserSession {

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;

    public BrowserSession() {

        playwright = Playwright.create();
        browser = BrowserTypeResolver.launch(playwright);

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1920, 1080)
                        .setRecordVideoDir(Paths.get("target/videos"))
        );

        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        page = context.newPage();
        page.navigate(FrameworkConfig.baseUrl());
        page.setDefaultTimeout(FrameworkConfig.timeout());
    }

    public Page page() {
        return page;
    }

    public BrowserContext context() {
        return context;
    }

    public void close() {
        context.close();
        browser.close();
        playwright.close();
    }
}
