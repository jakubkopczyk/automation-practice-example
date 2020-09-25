package com.jakubkopczyk.cucumber.framework.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class CustomExpectedConditions {

    public static ExpectedCondition<WebElement> visibilityOfAnyElementLocatedBy(final By locator, SearchContext container) {
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                List<WebElement> elements = container.findElements(locator);

                for (WebElement element : elements) {
                    if (element.isDisplayed()) return element;
                }
                return null;
            }

            public String toString() {
                return "visibility of element located by " + locator;
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeNotToBe(final WebElement element, final String attribute, final boolean value) {
        return attributeNotToBe(element, attribute, String.valueOf(value));
    }

    public static ExpectedCondition<Boolean> attributeNotToBe(final WebElement element, final String attribute, final String value) {
        return new ExpectedCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(WebDriver driver) {
                currentValue = element.getAttribute(attribute);
                if (currentValue == null || currentValue.isEmpty()) {
                    currentValue = element.getCssValue(attribute);
                }
                return !value.equals(currentValue);
            }

            @Override
            public String toString() {
                return String.format(attribute + " not to be \"%s\". Current " + attribute + ": \"%s\"", value,
                        currentValue);
            }
        };
    }
}
