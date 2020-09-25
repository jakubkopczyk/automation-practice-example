package com.jakubkopczyk.cucumber.framework.teststeps;

import com.jakubkopczyk.cucumber.framework.config.PropertiesLoader;
import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import com.jakubkopczyk.cucumber.framework.pageobjects.AuthenticationPage;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

public class AuthenticationPageSteps implements En {
    @Autowired
    protected WebDriverWrapper driverWrapper;
    @Autowired
    protected PropertiesLoader propertiesLoader;
    @Autowired
    protected AuthenticationPage authenticationPage;
    public AuthenticationPageSteps(){
        Given("I can see login form", ()->{
            Assert.assertTrue(authenticationPage.isDisplayed(authenticationPage.registeredPanel),
                    "Login form is not displayed");
        });
        And("I enter login {string}", (String login) ->{
            authenticationPage.sendText(authenticationPage.registeredEmailInput,login);
            Assert.assertEquals(authenticationPage.registeredEmailInput.getAttribute("value").toLowerCase(),
                    login.toLowerCase(), "Email is not provided");
        });
        And("I enter password {string}", (String password)->{
            authenticationPage.sendText(authenticationPage.registeredPasswordInput, password);
            Assert.assertEquals(authenticationPage.registeredPasswordInput.getAttribute("value").toLowerCase(),
                    password.toLowerCase(), "Password is not provided");
        });
        And("I click on Submit button", ()->{
            authenticationPage.clickElement(authenticationPage.registeredSignInButton);
        });
        Then("I can see warning message with include {string}", (String warningMessage)->{
            Assert.assertTrue(authenticationPage.registeredLoginError.getText().toLowerCase().contains(warningMessage.toLowerCase()),
                    "Warning message didn't contain " + warningMessage.toUpperCase());
        });
    }
}