package com.jakubkopczyk.cucumber.framework.helpers;

import com.jakubkopczyk.cucumber.framework.pageobjects.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Waiters extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(Waiters.class);

    @Autowired
    public Waiters(WebDriverWrapper driverWrapper) {
        super(driverWrapper);
    }

    public boolean waitForVisibilityOf(By selector, SearchContext container) {
        try {
            WebElement element = driverWrapper.wait.until(CustomExpectedConditions.visibilityOfAnyElementLocatedBy(selector, container));
            return element.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean waitForVisibilityOf(WebElement element) {
        try {
            driverWrapper.wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean waitForElementToBeEnabled(WebElement element) {
        try {
            driverWrapper.wait.until(ExpectedConditions.elementToBeClickable(element));
            driverWrapper.wait.until(CustomExpectedConditions.attributeNotToBe(element, "disabled", true));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}