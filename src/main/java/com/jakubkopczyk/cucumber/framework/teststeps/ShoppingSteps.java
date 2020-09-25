package com.jakubkopczyk.cucumber.framework.teststeps;


import com.github.javafaker.Faker;
import com.jakubkopczyk.cucumber.framework.config.PropertiesLoader;
import com.jakubkopczyk.cucumber.framework.helpers.Waiters;
import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import com.jakubkopczyk.cucumber.framework.pageobjects.HomePage;
import com.jakubkopczyk.cucumber.framework.pageobjects.ProductDetailsPage;
import com.jakubkopczyk.cucumber.framework.pageobjects.ShoppingCartSummaryPage;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;


public class ShoppingSteps implements En {
    @Autowired
    protected WebDriverWrapper driverWrapper;
    @Autowired
    protected HomePage homePage;
    @Autowired
    protected ProductDetailsPage productDetailsPage;
    @Autowired
    protected ShoppingCartSummaryPage shoppingCartSummaryPage;
    @Autowired
    protected PropertiesLoader propertiesLoader;
    @Autowired
    protected Waiters waiters;
    protected static final DecimalFormat DOLLAR_DECIMAL_FORMAT = new DecimalFormat(
            "$#0.00", new DecimalFormatSymbols(Locale.US));
    protected static Faker faker = new Faker(Locale.US);

    public ShoppingSteps() {
        And("I click on {string} button from sub menu",(String subMenuCategory)->{
            switch (subMenuCategory.toLowerCase()) {
                case "women":
                    homePage.clickElement(homePage.subMenuWomen);
                    break;
                case "dress":
                    homePage.clickElement(homePage.subMenuDresses);
                    break;
                case "t-shirts":
                    homePage.clickElement(homePage.subMenuTshirts);
                    break;
                default:
                    throw new IllegalStateException(String.format("Input error - ", subMenuCategory.toUpperCase()));
            }
            Assert.assertEquals(homePage.subMenuChosenCategory.getText().replaceAll
                    ("\\s+", "").toLowerCase(), subMenuCategory.toLowerCase(), "Value error");
        });
        And("I click on following product {string}", (String productName)->{
            homePage.saveGlobalVariable("cartProductName", productName);
            WebElement whichProductToClick = driverWrapper.webDriver.findElement(new By.ByXPath
                    ("//div[@id='center_column']//a[@class='product-name' and contains(text(),'" + productName + "')]"));
            homePage.clickElement(whichProductToClick);

            Assert.assertEquals(productDetailsPage.productName.getText().toLowerCase(), productName.toLowerCase(), "Value error");
        });
        And("I choose following details of my order", (DataTable dataTable)->{
            List<List<String>> data = dataTable.asLists();
            final String productQuantity = data.get(1).get(0);
            final String productSize = data.get(1).get(1);
            final String productColor = data.get(1).get(2);
            productDetailsPage.quantityInput.clear();


            productDetailsPage.sendText(productDetailsPage.quantityInput, productQuantity);
            productDetailsPage.selectFromDropdownByText(productSize, productDetailsPage.sizeDropdown);
            switch (productColor.toLowerCase()) {
                case "orange":
                    productDetailsPage.clickElement(productDetailsPage.orangeColorButton);
                    break;
                case "blue":
                    productDetailsPage.clickElement(productDetailsPage.blueColorButton);
                    break;
                case "white":
                    productDetailsPage.clickElement(productDetailsPage.whiteColorButton);
                    break;
                case "black":
                    productDetailsPage.clickElement(productDetailsPage.blackColorButton);
                    break;
                case "beige":
                    productDetailsPage.clickElement(productDetailsPage.beigeColorButton);
                    break;
                case "pink":
                    productDetailsPage.clickElement(productDetailsPage.pinkColorButton);
                    break;
                case "green":
                    productDetailsPage.clickElement(productDetailsPage.greenColorButton);
                    break;
                case "yellow":
                    productDetailsPage.clickElement(productDetailsPage.yellowColorButton);
                    break;
                default:
                    throw new IllegalStateException(String.format("Input error - ", productColor.toUpperCase()));
            }

            productDetailsPage.saveGlobalVariable("productUnitPrice",String.valueOf(Double.parseDouble(productDetailsPage.productPrice.getText().replaceAll("[^0-9.]", ""))));
            productDetailsPage.saveGlobalVariable("productQuantity",String.valueOf(Double.parseDouble(productQuantity)));
            productDetailsPage.saveGlobalVariable("productSize",productSize);
            productDetailsPage.saveGlobalVariable("productColor",productColor);


            Assert.assertEquals(productDetailsPage.readSizeDropdown.getText().toLowerCase(),
                    productSize.toLowerCase(), "Size error");
            Assert.assertEquals(productDetailsPage.readChosenColor.getAttribute("title").toLowerCase(),
                    productColor.toLowerCase(), "Color error");
        });
        When("I click on Add To Cart button", ()->{
            productDetailsPage.clickElement(productDetailsPage.addToCartButton);
        });
        And("I can see modal where I am able to see detailed data about my purchase", ()->{
            orderCalculations();

            Assert.assertTrue(productDetailsPage.isDisplayed(productDetailsPage.popupPaneProductDetails),
                    "Product details popup wasn't displayed");
            Assert.assertTrue(productDetailsPage.isDisplayed(productDetailsPage.popupPaneAddedSuccessfully),
                    "Product added successfully header wasn't displayed");

            Assert.assertEquals(productDetailsPage.popupPaneCartProductName.getText().toLowerCase(),
                    productDetailsPage.getGlobalVariableValue("cartProductName").toLowerCase(), "Value error");
            Assert.assertEquals(Double.parseDouble(productDetailsPage.popupPaneCartProductQuantity.getText()),
                    Double.parseDouble(productDetailsPage.getGlobalVariableValue("productQuantity")), "Value error");
            Assert.assertEquals(productDetailsPage.popupPaneCartProductPrice.getText().replaceAll("[^$0-9.]", ""),
                    DOLLAR_DECIMAL_FORMAT.format(Double.parseDouble(productDetailsPage.getGlobalVariableValue("cartProductPrice"))), "Value error");

            Assert.assertEquals(productDetailsPage.popupPaneCartTotalProductsPrice.getText().replaceAll("[^$0-9.]", ""),
                    DOLLAR_DECIMAL_FORMAT.format(Double.parseDouble(productDetailsPage.getGlobalVariableValue("cartTotalProductsPrice"))), "Value error");
            Assert.assertEquals(productDetailsPage.popupPaneCartTotalShippingPrice.getText().replaceAll("[^$0-9.]", ""),
                    DOLLAR_DECIMAL_FORMAT.format(2.00), "Value error");
            Assert.assertEquals(productDetailsPage.popupPaneCartTotalPrice.getText().replaceAll("[^$0-9.]", ""),
                    DOLLAR_DECIMAL_FORMAT.format(Double.parseDouble(productDetailsPage.getGlobalVariableValue("cartTotalPrice"))), "Value error");
        });
        And("I click on Proceed To Checkout button \\(from modal)",()->{
            productDetailsPage.clickElement(productDetailsPage.proceedToCheckoutButton);
        });
        And("I can see Shopping-Cart {string} form with valid information", (String shoppingSummaryTab)->{

            waiters.waitForVisibilityOf(shoppingCartSummaryPage.navigationTopLabelHeader);
            final String navigationTopLabelHeaderText = shoppingCartSummaryPage.navigationTopLabelHeader.getText();

            switch (shoppingSummaryTab.toLowerCase()) {
                case "your shopping cart":

                    Assert.assertEquals(navigationTopLabelHeaderText.toLowerCase(),
                            "Your shopping cart".toLowerCase(), "Value error");

                    Assert.assertEquals(shoppingCartSummaryPage.totalProductsPrice.getText().replaceAll("[^$0-9.]", ""),
                            DOLLAR_DECIMAL_FORMAT.format(Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("cartTotalProductsPrice"))), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.totalOrderShipping.getText().replaceAll("[^$0-9.]", ""),
                            DOLLAR_DECIMAL_FORMAT.format(2.00), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.totalOrderPriceWithoutTax.getText().replaceAll("[^$0-9.]", ""),
                            DOLLAR_DECIMAL_FORMAT.format(Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("totalOrderPriceWithoutTax"))), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.totalOrderTax.getText().replaceAll("[^$0-9.]", ""),
                            DOLLAR_DECIMAL_FORMAT.format(Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("totalOrderTax"))), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.totalOrderPriceWithTax.getText().replaceAll("[^$0-9.]", ""),
                            DOLLAR_DECIMAL_FORMAT.format(Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("totalOrderPriceWithTax"))), "Value error");
                    break;
                case "addresses":

                    Assert.assertEquals(navigationTopLabelHeaderText.toLowerCase(),
                            "Addresses".toLowerCase(), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.readCustomerFirstLastName.getText().toLowerCase(),
                            propertiesLoader.getDefaultCustomerFirstLastName().toLowerCase(), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.readCustomerCompanyName.getText().toLowerCase(),
                            propertiesLoader.getDefaultCustomerCompanyName().toLowerCase(), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.readCustomerAddress.getText().toLowerCase(),
                            propertiesLoader.getDefaultCustomerAddress().toLowerCase(), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.readCustomerCountry.getText().toLowerCase(),
                            propertiesLoader.getDefaultCustomerCountry().toLowerCase(), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.readCustomerMobilePhone.getText().toLowerCase(),
                            propertiesLoader.getDefaultCustomerMobilePhone().toLowerCase(), "Value error");
                    break;
                case "shipping":

                    Assert.assertEquals(navigationTopLabelHeaderText.toLowerCase(),
                            "Shipping".toLowerCase(), "Value error");
                    break;
                case "your payment method":

                    Assert.assertEquals(navigationTopLabelHeaderText.toLowerCase(),
                            "Your payment method".toLowerCase(), "Value error");
                    Assert.assertEquals(shoppingCartSummaryPage.totalOrderPriceWithTax.getText().replaceAll("[^$0-9.]", ""),
                            DOLLAR_DECIMAL_FORMAT.format(Double.parseDouble(shoppingCartSummaryPage.
                                    getGlobalVariableValue("totalOrderPriceWithTax"))), "Value error");
                    break;
                case "order confirmation":

                    Assert.assertEquals(navigationTopLabelHeaderText.toLowerCase(),
                            "Order confirmation".toLowerCase(), "Value error");
                    if (shoppingCartSummaryPage.getGlobalVariableValue("paymentType").toLowerCase().equals("pay by check")) {
                        Assert.assertTrue(shoppingCartSummaryPage.isDisplayed(shoppingCartSummaryPage.paymentByCheckSuccessful),
                                 "Order confirmation header wasn't displayed");
                    } else if (shoppingCartSummaryPage.getGlobalVariableValue("paymentType").toLowerCase().equals("pay by bank wire")) {
                        Assert.assertTrue(shoppingCartSummaryPage.isDisplayed(shoppingCartSummaryPage.paymentByBankWireSuccessful),
                                "Order confirmation header wasn't displayed");
                    } else {
                        Assert.fail(shoppingCartSummaryPage.getGlobalVariableValue("paymentType") + "\u001B[31m" + "Something went wrong! Check your payment type." + "\u001B[0m");
                    }
                    break;
                default:
                    throw new IllegalStateException(String.format("Input error - ", shoppingSummaryTab.toUpperCase()));
            }
        });
        And("I click on Proceed To Checkout button \\(from shopping-cart)", ()->{
            shoppingCartSummaryPage.clickElement(shoppingCartSummaryPage.proceedToCheckoutButton);
        });
        And("I write comment about my order", ()->{

            final String orderComment = faker.backToTheFuture().quote();
            shoppingCartSummaryPage.sendText(shoppingCartSummaryPage.orderCommentInput, orderComment);
            Assert.assertEquals(shoppingCartSummaryPage.orderCommentInput.getAttribute("value").toLowerCase(),
                    orderComment.toLowerCase(), "Value error");
        });
        And("I choose shipping option {string}", (String shippingOption)->{

            switch (shippingOption.toLowerCase()) {
                case "my carrier":
                    if (!shoppingCartSummaryPage.myCarrierRadioButton.isSelected()) {
                        shoppingCartSummaryPage.clickElement(shoppingCartSummaryPage.myCarrierRadioButton);
                    }
                    Assert.assertTrue(shoppingCartSummaryPage.myCarrierRadioButton.isSelected());
                    break;
                case "":
                    break;
                default:
                    throw new IllegalStateException(String.format("Input error - ", shippingOption.toUpperCase()));
            }

            Assert.assertEquals(Double.parseDouble(shoppingCartSummaryPage.readMyCarrierPrice.getText()
                    .replaceAll("[^0-9.]", "")), 2.00, "Value error");
        });
        And("I click on Terms of Service checkbox",()->{

            if (!shoppingCartSummaryPage.tosCheckbox.isSelected()) {
                shoppingCartSummaryPage.tosCheckbox.click();
            } else {
            }
            Assert.assertTrue(shoppingCartSummaryPage.tosCheckbox.isSelected());
        });
        Then("I choose payment method", (DataTable dataTable)->{

            List<List<String>> data = dataTable.asLists();
            shoppingCartSummaryPage.saveGlobalVariable("paymentType", data.get(1).get(0));


            switch (shoppingCartSummaryPage.getGlobalVariableValue("paymentType").toLowerCase()) {
                case "pay by check":
                    shoppingCartSummaryPage.clickElement(shoppingCartSummaryPage.chequePaymentBox);
                    break;
                case "pay by bank wire":
                    shoppingCartSummaryPage.clickElement(shoppingCartSummaryPage.bankWirePaymentBox);
                    break;
                default:
                    throw new IllegalStateException(String.format("Input error - ",
                            shoppingCartSummaryPage.getGlobalVariableValue("paymentType").toUpperCase()));
            }
        });
        And("I click on I Confirm My Order button", ()->{
            shoppingCartSummaryPage.clickElement(shoppingCartSummaryPage.iConfirmMyOrderButton);
        });
    }

    private void orderCalculations() {
        shoppingCartSummaryPage.saveGlobalVariable("cartProductPrice", String.valueOf(
                Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("productUnitPrice")) *
                        Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("productQuantity"))));
        if(shoppingCartSummaryPage.getGlobalVariableValue("cartTotalProductsPrice").equals("cartTotalProductsPrice")){
            shoppingCartSummaryPage.saveGlobalVariable("cartTotalProductsPrice", String.valueOf(
                    0.00 + Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("cartProductPrice"))));
        } else{
            shoppingCartSummaryPage.saveGlobalVariable("cartTotalProductsPrice", String.valueOf(
                    Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("cartTotalProductsPrice")) +
                            Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("cartProductPrice"))));
        }
        shoppingCartSummaryPage.saveGlobalVariable("cartTotalPrice", String.valueOf(
                Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("cartTotalProductsPrice")) +
                        2.00));
        shoppingCartSummaryPage.saveGlobalVariable("totalOrderPriceWithoutTax", String.valueOf(
                Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("cartTotalProductsPrice")) +
                        2.00));
        shoppingCartSummaryPage.saveGlobalVariable("totalOrderTax", String.valueOf(
                Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("totalOrderPriceWithoutTax")) *
                        0.04));
        shoppingCartSummaryPage.saveGlobalVariable("totalOrderPriceWithTax", String.valueOf(
                Double.parseDouble(shoppingCartSummaryPage.getGlobalVariableValue("totalOrderPriceWithoutTax")) *
                        (1 + 0.04)));
    }
}