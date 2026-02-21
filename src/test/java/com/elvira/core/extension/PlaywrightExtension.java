package com.elvira.core.extension;

import com.elvira.core.browser.BrowserSession;
import com.microsoft.playwright.Tracing;
import com.elvira.core.allure.AllureAttachmentService;
import com.elvira.core.allure.AllureEnvironmentWriter;

import org.junit.jupiter.api.extension.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightExtension implements
        BeforeAllCallback,
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create("playwright");

    @Override
    public void beforeAll(ExtensionContext context) {
    AllureEnvironmentWriter.write();
}

    @Override
    public void beforeEach(ExtensionContext context) {

        BrowserSession session = new BrowserSession();

        context.getStore(NAMESPACE)
                .put(context.getUniqueId(), session);
    }

    @Override
    public void afterEach(ExtensionContext context) {

        BrowserSession session = getSession(context);

        try {

            if (context.getExecutionException().isPresent()) {

                AllureAttachmentService
                        .attachScreenshot(session.page());

                Path tracePath = Paths.get(
                        "target/traces",
                        context.getDisplayName() + ".zip"
                );

                Files.createDirectories(tracePath.getParent());

                session.context().tracing().stop(
                        new Tracing.StopOptions()
                                .setPath(tracePath)
                );

                AllureAttachmentService.attachTrace(tracePath);

            } else {
                session.context().tracing().stop();
            }

        } catch (Exception ignored) {
        }

        session.close();
    }

    private BrowserSession getSession(ExtensionContext context) {
        return context.getStore(NAMESPACE)
                .get(context.getUniqueId(), BrowserSession.class);
    }

    @Override
    public boolean supportsParameter(
            ParameterContext parameterContext,
            ExtensionContext extensionContext) {

        return parameterContext.getParameter()
                .getType()
                .equals(com.microsoft.playwright.Page.class);
    }

    @Override
    public Object resolveParameter(
            ParameterContext parameterContext,
            ExtensionContext extensionContext) {

        return getSession(extensionContext).page();
    }

}
