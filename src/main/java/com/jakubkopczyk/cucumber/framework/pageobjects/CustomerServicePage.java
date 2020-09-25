package com.jakubkopczyk.cucumber.framework.pageobjects;

import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerServicePage extends BasePage {

    @FindBy(how = How.XPATH, using = "//h1[@class='page-heading bottom-indent']")
    public WebElement contactUsHeader;

    @FindBy(how = How.XPATH, using = "//form[@class='contact-form-box']")
    public WebElement contactUsPanel;

    @FindBy(how = How.XPATH, using = "//div[@class='alert alert-danger']//li")
    public WebElement contactUsErrorMessage;

    @FindBy(how = How.XPATH, using = "//p[@class='alert alert-success']")
    public WebElement contactUsSuccessMessage;

    @FindBy(how = How.XPATH, using = "//div[@class='col-xs-12 col-md-3']//select[@id='id_contact']")
    public WebElement subjectHeadingDropdown;

    @FindBy(how = How.XPATH, using = "//div[@class='col-xs-12 col-md-3']//div[@class='form-group selector1']//span")
    public WebElement readSubjectHeading;

    @FindBy(how = How.XPATH, using = "//div[@class='col-xs-12 col-md-3']//input[@id='email']")
    public WebElement emailAddressInput;

    @FindBy(how = How.XPATH, using = "//div[@class='col-xs-12 col-md-3']//input[@id='id_order']")
    public WebElement orderReferenceInput;

    @FindBy(how = How.XPATH, using = "//div[@class='col-xs-12 col-md-3']//input[@type='file']")
    public WebElement attachFileInput;

    @FindBy(how = How.XPATH, using = "//div[@class='col-xs-12 col-md-3']//span[@class='filename']")
    public WebElement readFileName;

    @FindBy(how = How.XPATH, using = "//div[@class='col-xs-12 col-md-9']//textarea[@class='form-control']")
    public WebElement messageTextArea;

    @FindBy(how = How.XPATH, using = "//div[@class='submit']//button")
    public WebElement sendButton;

    @Autowired
    public CustomerServicePage(WebDriverWrapper driverWrapper) {
        super(driverWrapper);
    }
}