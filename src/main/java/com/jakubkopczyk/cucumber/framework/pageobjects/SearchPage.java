package com.jakubkopczyk.cucumber.framework.pageobjects;

import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchPage extends BasePage {

    @FindBy(how = How.XPATH, using = "//span[@class='heading-counter']")
    public WebElement searchResultsNumber;

    @FindBy(how = How.XPATH, using = "//div[@id='center_column']//p")
    public WebElement noResultsWereFoundHeader;

    @FindBy(how = How.XPATH, using = "//select[@id='selectProductSort']")
    public WebElement sortByDropdown;

    @FindBy(how = How.XPATH, using = "//div[@id='uniform-selectProductSort']//span")
    public WebElement readSortByDropdown;

    @FindBy(how = How.XPATH, using = "//div[@class='right-block']//a[@class='product-name']")
    public List<WebElement> productNames;

    @FindBy(how = How.XPATH, using = "//div[@class='right-block']//div[@class='content_price']//span[@class='price product-price']")
    public List<WebElement> productPrices;

    @Autowired
    public SearchPage(WebDriverWrapper driverWrapper) {
        super(driverWrapper);
    }
}