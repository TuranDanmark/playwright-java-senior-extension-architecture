package com.elvira.core.allure;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureAttachmentService {

    public static void attachScreenshot(Page page) {
        try {
            byte[] screenshot = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );

            Allure.addAttachment(
                    "Screenshot",
                    new ByteArrayInputStream(screenshot)
            );
        } catch (Exception ignored) {}
    }

    public static void attachTrace(Path tracePath) {
        try {
            Allure.addAttachment(
                    "Trace",
                    Files.newInputStream(tracePath)
            );
        } catch (Exception ignored) {}
    }
}
