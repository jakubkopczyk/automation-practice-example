package com.jakubkopczyk.cucumber.framework.helpers;

import com.jakubkopczyk.cucumber.framework.config.PropertiesLoader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.time.Duration;

@Component
public class WebDriverWrapper {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverWrapper.class);

    public final WebDriver webDriver;
    public final WebDriverWait wait;
    private PropertiesLoader propertiesLoader;
    private String startingUrl;
    private final long clickWaitMilliseconds;

    @Autowired
    public WebDriverWrapper(WebDriver driver, PropertiesLoader propertiesLoader) {
        logger.info("Creating WebDriverWrapper...");
        this.propertiesLoader = propertiesLoader;
        this.webDriver = driver;
        this.wait = new WebDriverWait(this.webDriver, 10);
        wait.pollingEvery(Duration.ofSeconds(2));

        // amount of time (in milliseconds) to wait for browser clicks to happen, before page refresh logic
        // this is a mandatory delay, to accommodate any browser/environment/network latency
        this.clickWaitMilliseconds = Long.parseLong(this.propertiesLoader.getClickWait());

        // ensure all previous sessions are invalidated
        this.webDriver.manage().deleteAllCookies();

        // ensure the starting url is set
        this.startingUrl = this.propertiesLoader.getStartingUrl();
    }

    /**
     * Maximize browser's window (in initial step).
     */
    public void maximizeBrowser() {
        webDriver.manage().window().maximize();
    }

    /**
     * Browse to a page relative to the environment home.
     *
     * @param relativePath The relative path, must start with "/"
     */
    public void browseTo(String relativePath) {
        // to handle retrying logins, clear the browser cookies between retries
        webDriver.manage().deleteAllCookies();

        logger.debug("Browsing to {}", relativePath);
        webDriver.get(relativePath);
        waitForFullPageLoad();
    }

    public String getCurrentUrl() {
        return this.webDriver.getCurrentUrl();
    }

    /**
     * Wait for the web page to fully re-load, and any onload javascript events to complete.
     * Failure to do this between page transitions can result in intermittent failures, such as
     * Selenium still being on the previous page, or failure to find page element in the new page.
     */
    public void waitForFullPageLoad() {
        logger.info("Waiting for page to load...");

        // initial wait (in milliseconds) to give the selenium web driver time to tell the web browser to
        // submit the current page
        try {
            Thread.sleep(clickWaitMilliseconds);

        } catch (InterruptedException ex) {
            // called if trying to shutdown the test suite
            String message = "Wait for web browser to submit current page was interrupted";
            logger.error(message, ex);

            // propagate a fatal error so testsuite shuts down
            throw new RuntimeException(message, ex);
        }
        logger.info("Initial wait completed...");
        debugCurrentPage();
    }

    public byte[] takeScreenshot() {
        try {
            logger.info("Taking screenshot of current window as PNG");
            return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);

        } catch (WebDriverException ex) {
            logger.error("Error taking screenshot: " + ex.getMessage());
            return null;

        } catch (ClassCastException ex) {
            logger.error("WebDriver does not support taking screenshots");
            return null;
        }
    }

    public void timeWait(Integer time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ex) {
            // called if trying to shutdown the test suite
            String message = "Wait for the web browser was interrupted";
            logger.error(message, ex);
            // propagate a fatal error so testsuite shuts down
            throw new RuntimeException(message, ex);
        }
    }

    public void reset() {
        logger.info("WebDriverWrapper.reset");
        webDriver.manage().deleteAllCookies();
    }
    public void closeTabs() {
        String originalHandle = webDriver.getWindowHandle();
        for (String handle : webDriver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                webDriver.switchTo().window(handle);
                webDriver.close();
            }
        }
        webDriver.switchTo().window(originalHandle);
    }

    /**
     * Called when Spring is about to shutdown the current application, which is at the end of each feature.
     */
    @PreDestroy
    public void preDestroy() {
        logger.info("WebDriverWrapper.preDestroy...");
        webDriver.quit();
    }

    private void debugCurrentPage() {
        logger.debug("** At page {} - \"{}\" **", webDriver.getCurrentUrl(), webDriver.getTitle());
    }
}