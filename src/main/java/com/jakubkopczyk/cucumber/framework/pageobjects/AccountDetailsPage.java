package com.jakubkopczyk.cucumber.framework.pageobjects;

import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDetailsPage extends BasePage {

    @FindBy(how = How.XPATH, using = "//p[@class='info-account']")
    public WebElement myAccountDetailsDashboard;

    @Autowired
    public AccountDetailsPage(WebDriverWrapper driverWrapper) {
        super(driverWrapper);
    }
}