package com.jakubkopczyk.cucumber.framework.teststeps;


import com.google.common.collect.Ordering;
import com.jakubkopczyk.cucumber.framework.helpers.Waiters;
import com.jakubkopczyk.cucumber.framework.helpers.WebDriverWrapper;
import com.jakubkopczyk.cucumber.framework.pageobjects.HomePage;
import com.jakubkopczyk.cucumber.framework.pageobjects.SearchPage;
import cucumber.api.java8.En;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class SearchPageSteps implements En {
    @Autowired
    protected WebDriverWrapper driverWrapper;
    @Autowired
    protected HomePage homePage;
    @Autowired
    protected SearchPage searchPage;
    @Autowired
    Waiters waiters;

    public SearchPageSteps() {
        When("I search for phrase {string}", (String searchPhrase) -> {
            searchPage.sendText(homePage.searchBoxInput, searchPhrase);
            Assert.assertEquals(homePage.searchBoxInput.getAttribute("value").toLowerCase(),
                    searchPhrase.toLowerCase(), "Value error");
        });
        And("I click on search icon", () -> {
            searchPage.clickElement(homePage.searchBoxSubmit);
        });
        Then("I can see numbers of results equals to {string}", (String expectedCountOfResults) -> {
            waiters.waitForVisibilityOf(searchPage.searchResultsNumber);
            final String actualCountOfResults = searchPage.searchResultsNumber.getText().replaceAll("[^\\d]", "");
            if (expectedCountOfResults.equals("0")) {
                Assert.assertTrue(searchPage.isDisplayed(searchPage.noResultsWereFoundHeader),
                        String.format("Value error - No results were found"));
            }
            Assert.assertEquals(actualCountOfResults, expectedCountOfResults, "Incorrect elements of the results. Actual: " +
                    actualCountOfResults + " Expected: " + expectedCountOfResults);
        });
        And("I can see that every results which have been found contains phrase {string}", (String searchPhrase) -> {
            String[] listOfSearchedPhrases = searchPhrase.toLowerCase().split("[\\s]");

            if (!searchPage.noResultsWereFoundHeader.isDisplayed()) {
                for (WebElement productName : searchPage.productNames) {
                    for (String singlePhrase : listOfSearchedPhrases) {

                        Assert.assertTrue(productName.getText().toLowerCase().contains(singlePhrase.toLowerCase()), "Search error");
                    }
                }
            } else {
                Assert.assertTrue(searchPage.isDisplayed(searchPage.noResultsWereFoundHeader),
                        "Value error - No result");
            }
        });
        Then("I select from Dropdown Sort by {string}", (String sortBy) -> {
            switch (sortBy.toLowerCase()) {
                case "price: lowest first":
                    searchPage.selectFromDropdownByValue("price:asc", searchPage.sortByDropdown);
                    break;
                case "price: highest first":
                    searchPage.selectFromDropdownByValue("price:desc", searchPage.sortByDropdown);
                    break;
                case "product name: a to z":
                    searchPage.selectFromDropdownByValue("name:asc", searchPage.sortByDropdown);
                    break;
                case "product name: z to a":
                    searchPage.selectFromDropdownByValue("name:desc", searchPage.sortByDropdown);
                    break;
                default:
                    throw new IllegalStateException("Input error -" + sortBy.toUpperCase());
            }
            Assert.assertEquals(searchPage.readSortByDropdown.getText().toLowerCase(), sortBy.toLowerCase(), "Value error");
        });
        And("I can see that results are correctly sorted by {string}", (String sortedBy) -> {
            List<String> arrayList = new ArrayList<>();
            switch (sortedBy.toLowerCase()) {
                case "price: lowest first":
                    for (WebElement productPrices : searchPage.productPrices) {
                        arrayList.add(productPrices.getText().replaceAll("[^$0-9.]", ""));
                    }
                    List<String> lowestPriceList = Ordering.natural().sortedCopy(arrayList);
                    Assert.assertEquals(arrayList, lowestPriceList, "Incorrectly sorted by - " + sortedBy);
                    break;
                case "price: highest first":
                    for (WebElement productPrices : searchPage.productPrices) {
                        arrayList.add(productPrices.getText().replaceAll("[^$0-9.]", ""));
                    }
                    List<String> highestPriceList = Ordering.natural().reverse().sortedCopy(arrayList);
                    Assert.assertEquals(arrayList, highestPriceList, "Incorrectly sorted by - " + sortedBy);
                    break;
                case "product name: a to z":
                    for (WebElement productName : searchPage.productNames) {
                        arrayList.add(productName.getText());
                    }
                    List<String> sortedNames = Ordering.natural().sortedCopy(arrayList);
                    Assert.assertEquals(arrayList, sortedNames, "Incorrectly sorted by - " + sortedBy);
                    break;
                case "product name: z to a":
                    for (WebElement productName : searchPage.productNames) {
                        arrayList.add(productName.getText());
                    }
                    List<String> reverseSortedNames = Ordering.natural().reverse().sortedCopy(arrayList);
                    Assert.assertEquals(arrayList, reverseSortedNames, "Incorrectly sorted by - " + sortedBy);
                    break;
                default:
                    throw new IllegalStateException("Incorrect sort value - " + sortedBy.toUpperCase());
            }
        });
    }
}
