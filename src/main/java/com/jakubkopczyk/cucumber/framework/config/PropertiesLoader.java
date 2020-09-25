package com.jakubkopczyk.cucumber.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.util.TreeMap;

@Configuration
@PropertySource("file:configs/configuration.properties")
public class PropertiesLoader {

    @Value("${browser}")
    private String browser;

    @Value("${takeScreenshots}")
    private String takeScreenshots;

    @Value("${clickWait}")
    private String clickWait;

    @Value(("${headless}"))
    private String headless;

    @Value("${startingUrl}")
    private String startingUrl;

    @Value("${chromeVersion}")
    private String chromeVersion;

    @Value("${firefoxVersion}")
    private String firefoxVersion;

    @Value("${edgeVersion}")
    private String edgeVersion;

    @Value("${defaultCustomerUsername}")
    private String defaultCustomerUsername;

    @Value("${defaultCustomerCountry}")
    private String defaultCustomerCountry;

    @Value("${defaultCustomerFirstLastName}")
    private String defaultCustomerFirstLastName;

    @Value("${defaultCustomerCompanyName}")
    private String defaultCustomerCompanyName;

    @Value("${defaultCustomerAddress}")
    private String defaultCustomerAddress;

    @Value("${defaultCustomerMobilePhone}")
    private String defaultCustomerMobilePhone;

    private Map<String, String> examplesMap = new TreeMap<>();

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getTakeScreenshots() {
        return takeScreenshots;
    }

    public void setTakeScreenshots(String takeScreenshots) {
        this.takeScreenshots = takeScreenshots;
    }

    public String getClickWait() {
        return clickWait;
    }

    public void setClickWait(String clickWait) {
        this.clickWait = clickWait;
    }

    public String getHeadless() {
        return headless;
    }

    public void setHeadless(String headless) {
        this.headless = headless;
    }

    public String getStartingUrl() {
        return startingUrl;
    }

    public void setStartingUrl(String startingUrl) {
        this.startingUrl = startingUrl;
    }

    public String getChromeVersion() {
        return chromeVersion;
    }

    public void setChromeVersion(String chromeVersion) {
        this.chromeVersion = chromeVersion;
    }

    public String getFirefoxVersion() {
        return firefoxVersion;
    }

    public void setFirefoxVersion(String firefoxVersion) {
        this.firefoxVersion = firefoxVersion;
    }

    public Map<String, String> getExamplesMap() {
        return examplesMap;
    }

    public void setExamplesMap(Map<String, String> examplesMap) {
        this.examplesMap = examplesMap;
    }

    public String getEdgeVersion() {
        return edgeVersion;
    }

    public void setEdgeVersion(String edgeVersion) {
        this.edgeVersion = edgeVersion;
    }

    public String getDefaultCustomerUsername() {
        return defaultCustomerUsername;
    }

    public void setDefaultCustomerUsername(String defaultCustomerUsername) {
        this.defaultCustomerUsername = defaultCustomerUsername;
    }

    public String getDefaultCustomerCountry() {
        return defaultCustomerCountry;
    }

    public void setDefaultCustomerCountry(String defaultCustomerCountry) {
        this.defaultCustomerCountry = defaultCustomerCountry;
    }

    public String getDefaultCustomerFirstLastName() {
        return defaultCustomerFirstLastName;
    }

    public void setDefaultCustomerFirstLastName(String defaultCustomerFirstLastName) {
        this.defaultCustomerFirstLastName = defaultCustomerFirstLastName;
    }

    public String getDefaultCustomerCompanyName() {
        return defaultCustomerCompanyName;
    }

    public void setDefaultCustomerCompanyName(String defaultCustomerCompanyName) {
        this.defaultCustomerCompanyName = defaultCustomerCompanyName;
    }

    public String getDefaultCustomerAddress() {
        return defaultCustomerAddress;
    }

    public void setDefaultCustomerAddress(String defaultCustomerAddress) {
        this.defaultCustomerAddress = defaultCustomerAddress;
    }

    public String getDefaultCustomerMobilePhone() {
        return defaultCustomerMobilePhone;
    }

    public void setDefaultCustomerMobilePhone(String defaultCustomerMobilePhone) {
        this.defaultCustomerMobilePhone = defaultCustomerMobilePhone;
    }
}