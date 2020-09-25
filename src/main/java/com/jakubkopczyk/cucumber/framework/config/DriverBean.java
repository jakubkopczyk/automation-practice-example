package com.jakubkopczyk.cucumber.framework.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;

@Configuration
public class DriverBean {

    private final PropertiesLoader propertiesLoader;
    private static final Logger logger = LoggerFactory.getLogger(DriverBean.class);

    @Autowired
    public DriverBean(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    @Bean(name = "driver")
    public WebDriver getWebDriver() throws Exception {
        logger.info("Creating new driver...");

        WebDriver driver = null;
        String browser = propertiesLoader.getBrowser().toLowerCase();
        String headless = propertiesLoader.getHeadless().toLowerCase();

        ChromeOptions chromeOptions = new ChromeOptions();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        EdgeOptions edgeOptions = new EdgeOptions();
        LoggingPreferences loggingPreferences = new LoggingPreferences();

        // logging turned off completely
        loggingPreferences.enable(LogType.BROWSER, Level.OFF);
        loggingPreferences.enable(LogType.PERFORMANCE, Level.OFF);
        loggingPreferences.enable(LogType.PROFILER, Level.OFF);
        loggingPreferences.enable(LogType.SERVER, Level.OFF);

        // logging enabled
        loggingPreferences.enable(LogType.DRIVER, Level.WARNING);
        loggingPreferences.enable(LogType.CLIENT, Level.WARNING);

        switch (browser) {
            case "firefox":
                logger.info("Creating new firefox driver");
                String firefoxVersion = propertiesLoader.getFirefoxVersion();
                if (firefoxVersion.equals("")) {
                    WebDriverManager.firefoxdriver().setup();
                } else {
                    WebDriverManager.firefoxdriver().browserVersion(firefoxVersion).setup();
                }
                if ("true".equals(headless)) {
                    firefoxOptions.addArguments("--headless");
                }
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                firefoxOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
                firefoxOptions.setCapability(ChromeOptions.CAPABILITY, firefoxOptions);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
                logger.info("Creating new chrome driver");
                String chromeVersion = propertiesLoader.getChromeVersion();
                if (chromeVersion.equals("")) {
                    WebDriverManager.chromedriver().setup();
                } else {
                    WebDriverManager.chromedriver().browserVersion(chromeVersion).setup();
                }
                if ("true".equals(headless)) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
                chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "edge":
                logger.info("Creating new edge driver");
                String edgeVersion = propertiesLoader.getEdgeVersion();
                if (edgeVersion.equals("")) {
                    WebDriverManager.edgedriver().setup();
                } else {
                    WebDriverManager.edgedriver().browserVersion(edgeVersion).setup();
                }
                edgeOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
                driver = new EdgeDriver(edgeOptions);
                break;
        }

        if (driver == null) {
            throw new Exception("Invalid driver entry in properties file");
        }

        return driver;
    }
}