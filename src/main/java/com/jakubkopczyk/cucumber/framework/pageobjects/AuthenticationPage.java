package com.jakubkopczyk.cucumber.framework.pageobjects;

import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationPage extends BasePage {

    @FindBy(how = How.XPATH, using = "//form[@id='create-account_form']")
    public WebElement createAccountPanel;

    @FindBy(how = How.XPATH, using = "//input[@id='email_create']")
    public WebElement createAnAccountEmailInput;

    @FindBy(how = How.XPATH, using = "//button[@id='SubmitCreate']")
    public WebElement createAnAccountButton;

    @FindBy(how = How.XPATH, using = "//div[@id='create_account_error']")
    public WebElement createAnAccountError;

    @FindBy(how = How.XPATH, using = "//form[@id='login_form']")
    public WebElement registeredPanel;

    @FindBy(how = How.XPATH, using = "//div[@class='alert alert-danger']")
    public WebElement registeredLoginError;

    @FindBy(how = How.XPATH, using = "//input[@id='email']")
    public WebElement registeredEmailInput;

    @FindBy(how = How.XPATH, using = "//input[@id='passwd']")
    public WebElement registeredPasswordInput;

    @FindBy(how = How.XPATH, using = "//button[@id='SubmitLogin']")
    public WebElement registeredSignInButton;

    @Autowired
    public AuthenticationPage(WebDriverWrapper driverWrapper) {
        super(driverWrapper);
    }
}