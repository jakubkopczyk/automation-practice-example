package com.jakubkopczyk.cucumber.framework.teststeps;

import com.github.javafaker.Faker;
import com.jakubkopczyk.cucumber.framework.pageobjects.CustomerServicePage;
import com.jakubkopczyk.cucumber.framework.pageobjects.HomePage;
import cucumber.api.java8.En;
import net.andreinc.mockneat.MockNeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.io.File;
import java.nio.file.Paths;
import java.util.Locale;


public class CustomerServicePageSteps implements En {
    @Autowired
    protected HomePage homePage;
    @Autowired
    protected CustomerServicePage customerServicePage;
    protected static Faker faker = new Faker(Locale.US);
    protected static MockNeat mockNeat = MockNeat.secure();

    CustomerServicePageSteps() {
        When("I click on Contact Us button", () -> {
            homePage.clickElement(homePage.contactUsButton);
        });
        Then("I can see Contact Us form", () -> {
            Assert.assertTrue(customerServicePage.isDisplayed(customerServicePage.contactUsPanel),
                    "Contact Us form is not displayed");
        });
        Given("I am on Customer Service Contact Us page form", () -> {
            Assert.assertTrue(customerServicePage.isDisplayed(customerServicePage.contactUsHeader),
                    "Customer Service Contact Us page form is not displayed");
        });
        When("I choose Subject Heading {string}", (String subjectHeading) -> {
            customerServicePage.selectFromDropdownByText(subjectHeading, customerServicePage.subjectHeadingDropdown);
            Assert.assertEquals(customerServicePage.readSubjectHeading.getText().toLowerCase(),
                    subjectHeading.toLowerCase(), "Value error");
        });
        And("I write an email address in contact us page", () -> {
            final String userValidEmailAddress = mockNeat.emails().val();
            customerServicePage.sendText(customerServicePage.emailAddressInput, userValidEmailAddress);
            Assert.assertEquals(customerServicePage.emailAddressInput.getAttribute("value").toLowerCase(),
                    userValidEmailAddress.toLowerCase(), "Value error");
        });
        And("I write an invalid email address in contact us page", () -> {
            final String userInvalidEmailAddress = "Abc.example.com";
            customerServicePage.sendText(customerServicePage.emailAddressInput, userInvalidEmailAddress);
            Assert.assertEquals(customerServicePage.emailAddressInput.getAttribute("value").toLowerCase(),
                    userInvalidEmailAddress.toLowerCase(), "Value error");
        });
        And("I write order reference", () -> {
            final String orderReference = faker.code().isbn10();

            customerServicePage.sendText(customerServicePage.orderReferenceInput, orderReference);
            Assert.assertEquals(customerServicePage.orderReferenceInput.getAttribute("value").toLowerCase(),
                    orderReference.toLowerCase(), "Value error");
        });
        And("I write message", () -> {
            final String message = faker.chuckNorris().fact();
            customerServicePage.sendText(customerServicePage.messageTextArea, message);
            Assert.assertEquals(customerServicePage.messageTextArea.getAttribute("value").toLowerCase(),
                    message.toLowerCase(), "Value error");
        });
        And("I choose file to attach", () -> {
            final String fileName = "testPhoto.jpg";
            String path = getCurrentPath()
                    + File.separator
                    + "src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "images"
                    + File.separator;
            path += fileName;
            customerServicePage.attachFileInput.sendKeys(path);
            Assert.assertEquals(customerServicePage.readFileName.getText().toLowerCase(), fileName.toLowerCase(), "Value error");
        });
        Then("I click Send button", () -> {
            customerServicePage.clickElement(customerServicePage.sendButton);
        });
        And("I can see success message {string}", (String successMessage) -> {
            Assert.assertTrue(customerServicePage.contactUsSuccessMessage.getText().toLowerCase().contains
                    (successMessage.toLowerCase()), "Message didn't contain" + successMessage.toUpperCase());
        });
        And("I can see error message {string}", (String errorMessage) -> {
            Assert.assertTrue(customerServicePage.contactUsErrorMessage.getText().toLowerCase().contains
                    (errorMessage.toLowerCase()), "Message didn't contain" + errorMessage.toUpperCase());
        });
        When("I don't choose Subject Heading", () -> {
        });
        And("I don't write an email address", () -> {
        });
        And("I don't write message", () -> {
        });
    }

    public static String getCurrentPath() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }
}