package com.jakubkopczyk.cucumber.framework.teststeps;


import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import com.jakubkopczyk.cucumber.framework.pageobjects.SocialMediaPage;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


public class SocialMediaPageSteps implements En {
    @Autowired
    protected WebDriverWrapper driverWrapper;
    @Autowired
    protected SocialMediaPage socialMediaPage;
    SocialMediaPageSteps(){
        When("I scroll the website until I can see {string} logo",(String logoName)->{
            switch (logoName.toLowerCase()) {
                case "facebook":
                    socialMediaPage.scrollIntoElement(socialMediaPage.facebookButton);
                    Assert.assertTrue(socialMediaPage.isDisplayed(socialMediaPage.facebookButton),
                            logoName.toUpperCase() + " wasn't displayed");
                    break;
                case "twitter":
                    socialMediaPage.scrollIntoElement(socialMediaPage.twitterButton);
                    Assert.assertTrue(socialMediaPage.isDisplayed(socialMediaPage.twitterButton),
                            logoName.toUpperCase() + " wasn't displayed");
                    break;
                case "youtube":
                    socialMediaPage.scrollIntoElement(socialMediaPage.youtubeButton);
                    Assert.assertTrue(socialMediaPage.isDisplayed(socialMediaPage.youtubeButton),
                            logoName.toUpperCase() + " wasn't displayed");
                    break;
                case "google":
                    socialMediaPage.scrollIntoElement(socialMediaPage.googleButton);
                    Assert.assertTrue(socialMediaPage.isDisplayed(socialMediaPage.googleButton),
                            logoName.toUpperCase() + " wasn't displayed");
                    break;
                default:
                    throw new IllegalStateException("Invalid input - " + logoName.toUpperCase());
            }
        });
        And("I click on {string} logo button", (String logoName)->{
            switch (logoName.toLowerCase()) {
                case "facebook":
                    socialMediaPage.clickElement(socialMediaPage.facebookButton);
                    break;
                case "twitter":
                    socialMediaPage.clickElement(socialMediaPage.twitterButton);
                    break;
                case "youtube":
                    socialMediaPage.clickElement(socialMediaPage.youtubeButton);
                    break;
                case "google":
                    socialMediaPage.clickElement(socialMediaPage.googleButton);
                    break;
                default:
                    throw new IllegalStateException("Invalid input - " + logoName.toUpperCase());
            }
        });
        Then("I am redirected to Selenium {string} profile", (String logoName)->{
            List<String> browserTabs = new ArrayList<>(driverWrapper.webDriver.getWindowHandles());

            driverWrapper.webDriver.switchTo().window(browserTabs.get(1));

            Assert.assertTrue(driverWrapper.webDriver.getCurrentUrl().contains(logoName.toLowerCase() + ".com"),
                    "Page didn't contain " + logoName.toUpperCase());

        });
    }
}