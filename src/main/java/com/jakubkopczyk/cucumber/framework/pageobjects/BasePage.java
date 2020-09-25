package com.jakubkopczyk.cucumber.framework.pageobjects;

import com.jakubkopczyk.cucumber.framework.config.PropertiesLoader;
import com.jakubkopczyk.cucumber.framework.config.ScenarioSession;
import com.jakubkopczyk.cucumber.framework.helpers.Waiters;
import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class BasePage {

    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    @Autowired
    protected WebDriverWrapper driverWrapper;
    @Autowired
    protected PropertiesLoader propertiesLoader;
    @Autowired
    protected ScenarioSession scenarioSession;
    @Autowired
    protected Waiters waiters;

    @Autowired
    public BasePage(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
        PageFactory.initElements(driverWrapper.webDriver, this);
    }
    public void browseTo(String URL){
        driverWrapper.browseTo(URL);
    }
    public void clickElement(WebElement webElement) {
        waiters.waitForElementToBeEnabled(webElement);
        try {
            webElement.click();
        } catch (ElementNotInteractableException e) {
            logger.error(String.format("Couldn't click on element \"%S\"!", webElement), e);
        }
    }

    public void scrollIntoElement(WebElement element) {
        ((JavascriptExecutor) driverWrapper.webDriver).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public void scrollIntoElement(By locator) {
        ((JavascriptExecutor) driverWrapper.webDriver).executeScript("arguments[0].scrollIntoView(false);", driverWrapper.webDriver.findElement(locator));
    }

    protected List<WebElement> filterOutInvisibleObjects(List<WebElement> allObjects) {
        return allObjects.stream().filter(object -> object.isDisplayed()).collect(Collectors.toList());
    }

    public boolean isDisplayed(By locator, SearchContext container) {
        waiters.waitForVisibilityOf(locator, container);
        List<WebElement> elements = container.findElements(locator);

        boolean isDisplayed = filterOutInvisibleObjects(elements).size() > 0;
        if (isDisplayed) scrollIntoElement(locator);

        return isDisplayed;
    }

    public boolean isDisplayed(WebElement element) {
        boolean isElementDisplayed = waiters.waitForVisibilityOf(element);
        if (isElementDisplayed) scrollIntoElement(element);
        return isElementDisplayed;
    }

    public boolean isPageReady() {
        try {
            driverWrapper.wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (WebDriverException e) {
            logger.error("Error in loading page");
            return false;
        }
        return true;
    }

    public void sendText(WebElement webElement, String text) {
        waiters.waitForElementToBeEnabled(webElement);
        try {
            webElement.sendKeys(text);
        } catch (ElementNotInteractableException e) {
            logger.error(String.format("Couldn't send \"%S\" on element \"%S\"!", text, webElement), e);
        }
    }

    public void selectFromDropdownByValue(String textValue, WebElement webElement) {
        try {
            Select dropdown = new Select(webElement);
            dropdown.selectByValue(textValue);
        } catch (ElementNotSelectableException e) {
            logger.error("Couldn't select "+ textValue +  "from element "+webElement, textValue, webElement);
        }
    }

    public void selectFromDropdownByText(String textValue, WebElement webElement) {
        try {
            Select dropdown = new Select(webElement);
            dropdown.selectByVisibleText(textValue);
        } catch (ElementNotSelectableException e) {
            logger.error(String.format("Couldn't select \"%S\" from element \"%S\"!", textValue, webElement));
        }
    }

    public int getRandomIntValue(int max, int min) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public void saveGlobalVariable(String key, String value) {
        scenarioSession.writeTestVariableToReport(key, value);
        propertiesLoader.getExamplesMap().put(key, value);
    }

    public String getGlobalVariableValue(String key) {
        return propertiesLoader.getExamplesMap().getOrDefault(key, key);
    }
}