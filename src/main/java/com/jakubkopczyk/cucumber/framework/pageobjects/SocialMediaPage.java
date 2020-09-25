package com.jakubkopczyk.cucumber.framework.pageobjects;

import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.stereotype.Component;

@Component
public class SocialMediaPage extends BasePage {

    @FindBy(how = How.XPATH, using = "//*[@class=\"facebook\"]")
    public WebElement facebookButton;

    @FindBy(how = How.XPATH, using = "//*[@class=\"twitter\"]")
    public WebElement twitterButton;

    @FindBy(how = How.XPATH, using = "//*[@class=\"youtube\"]")
    public WebElement youtubeButton;

    @FindBy(how = How.XPATH, using = "//*[@class=\"google-plus\"]")
    public WebElement googleButton;

    public SocialMediaPage(WebDriverWrapper driverWrapper) {
        super(driverWrapper);
    }
}