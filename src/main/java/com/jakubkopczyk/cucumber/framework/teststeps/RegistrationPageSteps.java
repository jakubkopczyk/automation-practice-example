package com.jakubkopczyk.cucumber.framework.teststeps;

import com.github.javafaker.Faker;
import com.jakubkopczyk.cucumber.framework.config.PropertiesLoader;
import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import com.jakubkopczyk.cucumber.framework.pageobjects.AccountDetailsPage;
import com.jakubkopczyk.cucumber.framework.pageobjects.AuthenticationPage;
import com.jakubkopczyk.cucumber.framework.pageobjects.HomePage;
import com.jakubkopczyk.cucumber.framework.pageobjects.RegistrationPage;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import net.andreinc.mockneat.MockNeat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Locale;

public class RegistrationPageSteps implements En {

    @Autowired
    protected WebDriverWrapper driverWrapper;

    @Autowired
    protected PropertiesLoader propertiesLoader;
    @Autowired
    private HomePage homePage;
    @Autowired
    protected RegistrationPage registrationPage;
    @Autowired
    protected AuthenticationPage authenticationPage;
    @Autowired
    protected AccountDetailsPage accountDetailsPage;
    protected static Faker faker = new Faker(Locale.US);
    protected static MockNeat mockNeat = MockNeat.secure();
    public RegistrationPageSteps() {
        When("^I click on Sign in button$", () -> {
            homePage.clickElement(homePage.signInButton);
        });
        When("I write an email address", ()->{
            authenticationPage.saveGlobalVariable("generatedEmail", faker.random().hex(5) + mockNeat.emails().get());
            authenticationPage.sendText(authenticationPage.createAnAccountEmailInput,
                    authenticationPage.getGlobalVariableValue("generatedEmail"));
            Assert.assertEquals(authenticationPage.createAnAccountEmailInput.getAttribute("value").toLowerCase(),
                    authenticationPage.getGlobalVariableValue("generatedEmail").toLowerCase(),
                    "Provided emails were not the same");
        });
        When("I write an invalid email address", ()->{
            String fakeEmail = "Abc.example.com";
            authenticationPage.sendText(authenticationPage.createAnAccountEmailInput,fakeEmail);
            Assert.assertEquals(authenticationPage.createAnAccountEmailInput.getAttribute("value").toLowerCase(),
                    fakeEmail.toLowerCase(), "Field does not have the email");
        });
        When("I write an email address which is already in database", ()->{
            final String registeredEmailAddress = propertiesLoader.getDefaultCustomerUsername();
            authenticationPage.sendText(authenticationPage.createAnAccountEmailInput, registeredEmailAddress);
            Assert.assertEquals(authenticationPage.createAnAccountEmailInput.getAttribute("value").toLowerCase(),
                    registeredEmailAddress.toLowerCase(), "Value is not correct");
        });
        And("I click on Create An Account button", ()->{
            authenticationPage.clickElement(authenticationPage.createAnAccountButton);
        });
        Then("I can see registration page form", ()->{
            Assert.assertTrue(authenticationPage.isDisplayed(authenticationPage.createAccountPanel),
                    "Registration page form is not visible");
        });
        Then("I can see account creation page form", ()->{
            Assert.assertTrue(authenticationPage.isDisplayed(registrationPage.accountCreationPanel),
                    "Account creation page form is not displayed");
        });
        And("I write following data to registration form",(DataTable dataTable)->{
            List<List<String>> data = dataTable.asLists();
            final String userFirstName = data.get(1).get(0);
            final String userLastName = data.get(1).get(1);
            final String userPassword = data.get(1).get(2);
            final String userAddress = data.get(1).get(3);
            final String userCity = data.get(1).get(4);
            final String userState = data.get(1).get(5);
            final String userPostalCode = data.get(1).get(6);
            final String userCountry = data.get(1).get(7);
            final String userMobilePhone = data.get(1).get(8);

            registrationPage.sendText(registrationPage.firstNameInput, userFirstName);
            Assert.assertEquals(registrationPage.firstNameInput.getAttribute("value").toLowerCase(),
                    userFirstName.toLowerCase(), "Value error");

            registrationPage.sendText(registrationPage.lastNameInput, userLastName);
            Assert.assertEquals(registrationPage.lastNameInput.getAttribute("value").toLowerCase(),
                    userLastName.toLowerCase(), "Value error");

            registrationPage.sendText(registrationPage.passwordInput, userPassword);
            Assert.assertEquals(registrationPage.passwordInput.getAttribute("value").toLowerCase(),
                    userPassword.toLowerCase(), "Value error");

            registrationPage.sendText(registrationPage.addressInput, userAddress);
            Assert.assertEquals(registrationPage.addressInput.getAttribute("value").toLowerCase(),
                    userAddress.toLowerCase(), "Value error");

            registrationPage.sendText(registrationPage.cityInput, userCity);
            Assert.assertEquals(registrationPage.cityInput.getAttribute("value").toLowerCase(),
                    userCity.toLowerCase(), "Value error");

            registrationPage.selectFromDropdownByText(userState, registrationPage.stateDropDown);
            Assert.assertEquals(registrationPage.readStateDropdown.getText().toLowerCase(),
                    userState.toLowerCase(), "Value error");

            registrationPage.sendText(registrationPage.postalCodeInput, userPostalCode);
            Assert.assertEquals(registrationPage.postalCodeInput.getAttribute("value").toLowerCase(),
                    userPostalCode.toLowerCase(), "Value error");

            registrationPage.selectFromDropdownByText(userCountry, registrationPage.countryDropDown);
            Assert.assertEquals(registrationPage.readCountryDropdown.getText().toLowerCase(),
                    userCountry.toLowerCase(), "Value error");

            registrationPage.sendText(registrationPage.mobilePhoneInput, userMobilePhone);
            Assert.assertEquals(registrationPage.mobilePhoneInput.getAttribute("value").toLowerCase(),
                    userMobilePhone.toLowerCase(), "Value error");
        });
        And("I choose gender",()->{
            final int randomNumber = registrationPage.getRandomIntValue(2, 1);
            if (randomNumber == 1) {
                registrationPage.clickElement(registrationPage.mrButton);
                
                Assert.assertTrue(registrationPage.mrButton.isSelected());
            } else {
                registrationPage.clickElement(registrationPage.mrsButton);
                Assert.assertTrue(registrationPage.mrsButton.isSelected());
            }
        });
        And("I write my first name", ()->{
            final String userFirstName = mockNeat.names().first().val();
            registrationPage.sendText(registrationPage.firstNameInput, userFirstName);
            Assert.assertEquals(registrationPage.firstNameInput.getAttribute("value").toLowerCase(),
                    userFirstName.toLowerCase(), "Value error");
        });
        And("I write my last name",()->{
            final String userLastName = mockNeat.names().last().val();
            registrationPage.sendText(registrationPage.lastNameInput, userLastName);
            Assert.assertEquals(registrationPage.lastNameInput.getAttribute("value").toLowerCase(),
                    userLastName.toLowerCase(), "Value error");
        });
        And("I check if email is already written and valid",()->{
            Assert.assertEquals(registrationPage.emailInput.getAttribute("value").toLowerCase(),
                    registrationPage.getGlobalVariableValue("generatedEmail").toLowerCase(), "VALUE_ERROR");
        });
        And("I clear my email address", ()->{
            registrationPage.clickElement(registrationPage.emailInput);
            registrationPage.emailInput.clear();
        });
        And("I write password",()->{
            final String userPassword = mockNeat.passwords().medium().val();
            registrationPage.sendText(registrationPage.passwordInput, userPassword);
            Assert.assertEquals(registrationPage.passwordInput.getAttribute("value").toLowerCase(),
                    userPassword.toLowerCase(), "VALUE_ERROR");
        });
        And("I choose date of birth", ()->{
            final int day = registrationPage.getRandomIntValue(28, 1);
            final int month = registrationPage.getRandomIntValue(12, 1);
            final int year = registrationPage.getRandomIntValue(2019, 1900);
            registrationPage.selectFromDropdownByValue(Integer.toString(day), registrationPage.dayOfBirth);
            registrationPage.selectFromDropdownByValue(Integer.toString(month), registrationPage.monthOfBirth);
            registrationPage.selectFromDropdownByValue(Integer.toString(year), registrationPage.yearOfBirth);
            Assert.assertEquals(registrationPage.readDayOfBirth.getText().replaceAll("[^\\d]", ""),
                    Integer.toString(day), "VALUE_ERROR");
            Assert.assertEquals(registrationPage.readMonthOfBirth.getText().replaceAll("\\s+", "").toLowerCase(),
                    DateTime.now().withMonthOfYear(month).toString("MMMM", Locale.ENGLISH).toLowerCase(), "VALUE_ERROR");
            Assert.assertEquals(registrationPage.readYearOfBirth.getText().replaceAll("[^\\d]", ""),
                    Integer.toString(year), "VALUE_ERROR");
        });
        And("I sign in to receive newsletter and special offers", ()->{
            int tempRandomValue = registrationPage.getRandomIntValue(3, 3);
            if (tempRandomValue == 1) {
                if (!registrationPage.newsletterCheckbox.isSelected()) {
                    registrationPage.newsletterCheckbox.click();
                }
                Assert.assertTrue(registrationPage.newsletterCheckbox.isSelected());
            } else if (tempRandomValue == 2) {
                if (!registrationPage.specialOffersCheckbox.isSelected()) {
                    registrationPage.specialOffersCheckbox.click();
                }
                Assert.assertTrue(registrationPage.specialOffersCheckbox.isSelected());
            } else {
                if (!registrationPage.newsletterCheckbox.isSelected() && !registrationPage.specialOffersCheckbox.isSelected()) {
                    registrationPage.newsletterCheckbox.click();
                    registrationPage.specialOffersCheckbox.click();
                }
                Assert.assertTrue(registrationPage.newsletterCheckbox.isSelected() &&
                        registrationPage.specialOffersCheckbox.isSelected());
            }
        });
        And("I check if my first & last name are already written and are correct", ()->{
            Assert.assertEquals(registrationPage.firstNameInput.getAttribute("value").toLowerCase(),
                    registrationPage.assertFirstNameInput.getAttribute("value").toLowerCase(), "VALUE_ERROR");
            Assert.assertEquals(registrationPage.lastNameInput.getAttribute("value").toLowerCase(),
                    registrationPage.assertLastNameInput.getAttribute("value").toLowerCase(), "VALUE_ERROR");
        });
        And("I write company name",()->{
            final String companyName = mockNeat.departments().val();
            registrationPage.sendText(registrationPage.companyInput, companyName);
            Assert.assertEquals(registrationPage.companyInput.getAttribute("value").toLowerCase(),
                    companyName.toLowerCase(), "VALUE_ERROR");
        });
        And("I write my addresses",()->{
            final String userAddress = faker.address().streetName();
            final String userSecondAddress = faker.address().secondaryAddress() + faker.address().buildingNumber();
            registrationPage.sendText(registrationPage.addressInput, userAddress);
            registrationPage.sendText(registrationPage.addressSecondInput, userSecondAddress);
            Assert.assertEquals(registrationPage.addressInput.getAttribute("value").toLowerCase(),
                    userAddress.toLowerCase(), "VALUE_ERROR");
            Assert.assertEquals(registrationPage.addressSecondInput.getAttribute("value").toLowerCase(),
                    userSecondAddress.toLowerCase(), "VALUE_ERROR");
        });
        And("I write my address", ()->{
            final String userAddress = faker.address().streetName() + faker.address().buildingNumber();
            registrationPage.sendText(registrationPage.addressInput, userAddress);
            Assert.assertEquals(registrationPage.addressInput.getAttribute("value").toLowerCase(),
                    userAddress.toLowerCase(), "Value_Error");
        });
        And("I choose country {string}",(String country)->{
            final String defaultCountry = propertiesLoader.getDefaultCustomerCountry();
            if (registrationPage.readCountryDropdown.getText().toLowerCase().equals(defaultCountry.toLowerCase()) &&
                    !registrationPage.readCountryDropdown.getText().toLowerCase().equals(country.toLowerCase())) {
                registrationPage.selectFromDropdownByText(country, registrationPage.countryDropDown);
            }
            Assert.assertEquals(registrationPage.readCountryDropdown.getText().toLowerCase(),
                    country.toLowerCase(), "Value_Error");
        });
        And("I write city name",()->{
            final String userCity = mockNeat.cities().us().val();
            registrationPage.sendText(registrationPage.cityInput, userCity);
            Assert.assertEquals(registrationPage.cityInput.getAttribute("value").toLowerCase(),
                    userCity.toLowerCase(), "Value_Error");
        });
        And("I choose state",()->{
            final String userState = faker.address().state();
            registrationPage.selectFromDropdownByText(userState, registrationPage.stateDropDown);
            Assert.assertEquals(registrationPage.readStateDropdown.getText().toLowerCase(),
                    userState.toLowerCase(), "Value_Error");
        });
        And("I write postal code",()->{
            final String userPostalCode = StringUtils.left(faker.address().zipCode(), 5);

            registrationPage.sendText(registrationPage.postalCodeInput, userPostalCode);

            Assert.assertEquals(registrationPage.postalCodeInput.getAttribute("value").toLowerCase(),
                    userPostalCode.toLowerCase(), "Value_Error");
        });
        And("I write additional information",()->{
            final String userAdditionalInformation = faker.chuckNorris().fact();

            registrationPage.sendText(registrationPage.additionalInformationBox, userAdditionalInformation);

            Assert.assertEquals(registrationPage.additionalInformationBox.getAttribute("value").toLowerCase(),
                    userAdditionalInformation.toLowerCase(), "Value_Error");
        });
        And("I write home phone",()->{
            final String userPhoneNumber = faker.phoneNumber().cellPhone();
            registrationPage.sendText(registrationPage.homePhoneInput, userPhoneNumber);
            Assert.assertEquals(registrationPage.homePhoneInput.getAttribute("value").toLowerCase(),
                    userPhoneNumber.toLowerCase(), "Value_Error");
        });
        And("I write mobile phone",()->{
            final String userMobilePhone = faker.phoneNumber().cellPhone();

            registrationPage.sendText(registrationPage.mobilePhoneInput, userMobilePhone);

            Assert.assertEquals(registrationPage.mobilePhoneInput.getAttribute("value").toLowerCase(),
                    userMobilePhone.toLowerCase(), "Value_Error");
        });
        And("I write my address alias",()->{
            final String userAddressAlias = mockNeat.emails().val();

            registrationPage.addressAliasInput.clear();
            registrationPage.sendText(registrationPage.addressAliasInput, userAddressAlias);
            
            Assert.assertEquals(registrationPage.addressAliasInput.getAttribute("value").toLowerCase(),
                    userAddressAlias.toLowerCase(), "Value_Error");
        });
        And("I clear my email address alias",()->{
            registrationPage.clickElement(registrationPage.addressAliasInput);
            registrationPage.addressAliasInput.clear();
        });
        And("I click on Register button",()->{
            registrationPage.clickElement(registrationPage.registerButton);
        });
        Then("I can see welcome message",()->{
            Assert.assertTrue(registrationPage.isDisplayed(accountDetailsPage.myAccountDetailsDashboard));
            Assert.assertEquals(accountDetailsPage.myAccountDetailsDashboard.getText().toLowerCase(),
                    "Welcome to your account. Here you can manage all of your personal information and orders."
                            .toLowerCase(), "Value_Error");
        });
        Then("I can see create an account error",()->{
            Assert.assertTrue(registrationPage.isDisplayed(authenticationPage.createAnAccountError),
                     "Create an account error header");
        });
        Then("I can see registration error",()->{
            Assert.assertTrue(registrationPage.isDisplayed(registrationPage.registerError),
                    "Registration error header");
        });
        Then("I can see warning message about missing {string} input",(String stringName)->{
            switch (stringName.toLowerCase()) {
                case "first name":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("firstname is required.".toLowerCase()), "Message didn't contain firstname is required.");
                    break;
                case "last name":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("lastname is required.".toLowerCase()), "Message didn't contain lastname is required.");
                    break;
                case "email address":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("email is required.".toLowerCase()), "Message didn't contain email is required.");
                    break;
                case "password":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("passwd is required.".toLowerCase()), "Message didn't contain passwd is required");
                    break;
                case "address":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("address1 is required.".toLowerCase()), "Message didn't contain address1 is required.");
                    break;
                case "city":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("city is required.".toLowerCase()), "Message didn't contain city is required.");
                    break;
                case "state":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("This country requires you to choose a State.".toLowerCase()), "Message didn't contain This country requires you to choose a State.");
                    break;
                case "postal code":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("The Zip/Postal code you've entered is invalid. It must follow this format: 00000".toLowerCase()), "Message didn't contain The Zip/Postal code you've entered is invalid. It must follow this format: 00000");
                    break;
                case "country":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("Country is invalid".toLowerCase()), "Message didn't contain Country is invalid");
                    break;
                case "mobile phone":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("You must register at least one phone number.".toLowerCase()), "Message didn't contain You must register at least one phone number.");
                    break;
                case "email address alias":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("alias is required.".toLowerCase()), "Message didn't contain alias is required.");
                    break;
                case "one element":
                    Assert.assertTrue(registrationPage.registerError.getText().toLowerCase().contains
                            ("There is 1 error".toLowerCase()), "Message didn't contain There is 1 error");
                    break;
                default:
                    throw new IllegalStateException("Provided value is incorrect: "+ stringName.toUpperCase());
            }
        });
    }
}