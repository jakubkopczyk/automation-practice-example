package com.jakubkopczyk.cucumber.framework.pageobjects;

import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartSummaryPage extends BasePage {

    @FindBy(how = How.XPATH, using = "//span[@class='navigation_page']")
    public WebElement navigationTopLabelHeader;

    @FindBy(how = How.XPATH, using = "(//span[contains(text(),'Proceed to checkout')]//i[@class='icon-chevron-right right'])[2]")
    public WebElement proceedToCheckoutButton;

    @FindBy(how = How.XPATH, using = "//button[@class='button btn btn-default button-medium']//span")
    public WebElement iConfirmMyOrderButton;

    @FindBy(how = How.XPATH, using = "//td[@id='total_product']")
    public WebElement totalProductsPrice;

    @FindBy(how = How.XPATH, using = "//td[@id='total_shipping']")
    public WebElement totalOrderShipping;

    @FindBy(how = How.XPATH, using = "//td[@id='total_price_without_tax']")
    public WebElement totalOrderPriceWithoutTax;

    @FindBy(how = How.XPATH, using = "//td[@id='total_tax']")
    public WebElement totalOrderTax;

    @FindBy(how = How.XPATH, using = "//span[@id='total_price']")
    public WebElement totalOrderPriceWithTax;

    @FindBy(how = How.XPATH, using = "//ul[@id='address_delivery']//li[@class='address_firstname address_lastname']")
    public WebElement readCustomerFirstLastName;

    @FindBy(how = How.XPATH, using = "//ul[@id='address_delivery']//li[@class='address_company']")
    public WebElement readCustomerCompanyName;

    @FindBy(how = How.XPATH, using = "//ul[@id='address_delivery']//li[@class='address_address1 address_address2']")
    public WebElement readCustomerAddress;

    @FindBy(how = How.XPATH, using = "//ul[@id='address_delivery']//li[@class='address_country_name']")
    public WebElement readCustomerCountry;

    @FindBy(how = How.XPATH, using = "//ul[@id='address_delivery']//li[@class='address_phone_mobile']")
    public WebElement readCustomerMobilePhone;

    @FindBy(how = How.XPATH, using = "//textarea[@name='message']")
    public WebElement orderCommentInput;

    @FindBy(how = How.XPATH, using = "//td[@class='delivery_option_radio']//input")
    public WebElement myCarrierRadioButton;

    @FindBy(how = How.XPATH, using = "//div[@class='delivery_option_price']")
    public WebElement readMyCarrierPrice;

    @FindBy(how = How.XPATH, using = "//input[@name='cgv']")
    public WebElement tosCheckbox;

    @FindBy(how = How.XPATH, using = "//a[@class='bankwire']")
    public WebElement bankWirePaymentBox;

    @FindBy(how = How.XPATH, using = "//a[@class='cheque']")
    public WebElement chequePaymentBox;

    @FindBy(how = How.XPATH, using = "//p[@class='alert alert-success']")
    public WebElement paymentByCheckSuccessful;

    @FindBy(how = How.XPATH, using = "//p[@class='cheque-indent']//strong")
    public WebElement paymentByBankWireSuccessful;

    @Autowired
    public ShoppingCartSummaryPage(WebDriverWrapper driverWrapper) {
        super(driverWrapper);
    }
}