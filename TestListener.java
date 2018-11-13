package com.seerene.configuration;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.io.IOException;

import static com.seerene.driver.Driver.getDriver;
import static com.seerene.utils.Utils.attachConsoleLog;
import static com.seerene.utils.Utils.attachScreenshot;
import static com.seerene.utils.Utils.getBrowserLogs;
import static com.seerene.utils.Utils.getProperty;
import static com.seerene.utils.Utils.makeScreenshot;

public class TestListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestMethod(TestContext testContext) throws IOException {
        if (testContext.getTestException() != null) {
            String screenshotName = testContext.getTestMethod().getName() + ".png";
            makeScreenshot(screenshotName);
            attachScreenshot(screenshotName);
        }
        attachConsoleLog(getBrowserLogs());
    }

    @Override
    public void beforeTestMethod(TestContext testContext) {
        getDriver().manage().deleteAllCookies();
        getDriver().get(getProperty("platform.url"));
    }

    @Override
    public void afterTestClass(TestContext testContext) {
        if (getDriver() != null) {
            getDriver().close();
            getDriver().quit();
        }
    }
}
