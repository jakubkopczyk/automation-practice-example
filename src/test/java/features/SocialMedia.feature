@socialMedia
Feature: As a user I would like to be redirected to social media pages from automationpractice.com

  Background: Navigate to automationpractice.com website
    Given I open home page

  Scenario Outline: I click on social media "<platform>" logo
    Given I can see automationpractice.com website
    When I scroll the website until I can see "<platform>" logo
    And I click on "<platform>" logo button
    Then I am redirected to Selenium "<platform>" profile

    Examples: SCENARIO OUTLINE DATA
      | platform |
      | Facebook |
      | Twitter  |
      | YouTube  |
      | Google   |