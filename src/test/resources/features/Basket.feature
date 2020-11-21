@all @basket
Feature: Basket add/remove functionality

  Background:
    Given I am on the homepage
    And I click sign-in button
    When I enter my username as "username"
    And I enter my password as "password"

    @remove
  Scenario:  Remove Item from basket
    When I remove an item from basket
    Then I see that the amount of items in basket changes

    @change
  Scenario:  Increase item number in basket
    When I change the amount of any item in basket
    Then I see that the amount of items in basket changes