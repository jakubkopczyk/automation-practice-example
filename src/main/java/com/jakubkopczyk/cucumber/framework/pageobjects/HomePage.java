package com.jakubkopczyk.cucumber.framework.pageobjects;

import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomePage extends BasePage {

    @FindBy(how = How.XPATH, using = "//a[@class='account']//span")
    public WebElement currentLoggedUserName;

    @FindBy(how = How.XPATH, using = "//a[@class='login']")
    public WebElement signInButton;

    @FindBy(how = How.XPATH, using = "//div[@id='contact-link']")
    public WebElement contactUsButton;

    @FindBy(how = How.XPATH, using = "//input[@class='search_query form-control ac_input']")
    public WebElement searchBoxInput;

    @FindBy(how = How.XPATH, using = "//form[@id='searchbox']//button[@type='submit']")
    public WebElement searchBoxSubmit;

    @FindBy(how = How.XPATH, using = "//li//a[@title='Women' and not(img)]")
    public WebElement subMenuWomen;

    @FindBy(how = How.XPATH, using = "(//li//a[@title='Dresses' and not(img)])[2]")
    public WebElement subMenuDresses;

    @FindBy(how = How.XPATH, using = "(//li//a[@title='T-shirts' and not(img)])[2]")
    public WebElement subMenuTshirts;

    @FindBy(how = How.XPATH, using = "//span[@class='cat-name']")
    public WebElement subMenuChosenCategory;

    @Autowired
    public HomePage(WebDriverWrapper driverWrapper) {
        super(driverWrapper);
    }
}