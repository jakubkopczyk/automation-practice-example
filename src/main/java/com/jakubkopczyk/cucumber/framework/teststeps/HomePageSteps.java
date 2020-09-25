package com.jakubkopczyk.cucumber.framework.teststeps;

import com.jakubkopczyk.cucumber.framework.pageobjects.AccountDetailsPage;
import com.jakubkopczyk.cucumber.framework.pageobjects.AuthenticationPage;
import com.jakubkopczyk.cucumber.framework.pageobjects.HomePage;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

public class HomePageSteps extends BaseAnnotations implements En {

    @Autowired
    protected HomePage homePage;
    @Autowired
    protected AuthenticationPage authenticationPage;
    @Autowired
    protected AccountDetailsPage accountDetailsPage;

    public HomePageSteps() {
        When("^I open home page$", () -> {
            final String expectedPageURL = "http://automationpractice.com/index.php";
            homePage.browseTo(propertiesLoader.getStartingUrl());
            Assert.assertEquals(driverWrapper.getCurrentUrl(), expectedPageURL, "Page is not loaded");
        });
        Given("I can see automationpractice.com website", ()-> {
            Assert.assertTrue(homePage.isPageReady(), "Page is not ready");
        });
        When("I am logged as customer {string} using {string} password", (String email, String password)->{
            homePage.clickElement(homePage.signInButton);
            authenticationPage.sendText(authenticationPage.registeredEmailInput,email);
            authenticationPage.sendText(authenticationPage.registeredPasswordInput, password);
            authenticationPage.clickElement(authenticationPage.registeredSignInButton);
            Assert.assertEquals(homePage.currentLoggedUserName.getText().toLowerCase(),
                    propertiesLoader.getDefaultCustomerFirstLastName().toLowerCase(), "User is not logged in");

        });
        Given("I am on MyAccount details page", ()->{
            Assert.assertTrue(accountDetailsPage.isDisplayed(accountDetailsPage.myAccountDetailsDashboard));
            Assert.assertEquals(accountDetailsPage.myAccountDetailsDashboard.getText().toLowerCase(),
                    "Welcome to your account. Here you can manage all of your personal information and orders.".toLowerCase(),
                    "MyAccount details page is not displayed");
        });
    }
}