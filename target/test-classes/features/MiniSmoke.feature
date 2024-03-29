#  ...TASK...
#  As a new Amazon user, I want to buy the cheapest Snickers and Skittles on the page.
#  Add the cheapest ones to Basket and proceed to Checkout

Feature: The cheapest product searching and checkout process
  As a new Amazon user,
  I want to search for the cheapest Snickers and Skittles on the page,
  so that I can save money by buying them

  # Normally, background or given scenarios should be short. But it depends on the user story and Task :)
  # According to Test Scenarios, we can add all common steps under the background
  Background:
    Given I am on the homepage
    And The language as "English - EN" is selected
    And Search department as "Grocery" is selected
    And I search the product "Snickers"
    And I sort the products by "Price: Low to High"
    And I add the cheapest "Snickers" to the basket
    And Search department as "Grocery" is selected
    And I search the product "Skittles"
    And I sort the products by "Price: Low to High"
    And I add the cheapest "Skittles" to the basket
    And I navigate to basket page

  # Expected result is PASS
  @all @basket
  Scenario: Verify that the basket calculates the result correctly
    Then I see that the basket calculates the result correctly

# Expected result is intentionally wrong entered to make the case FAIL
  @all @checkout
  Scenario: Verify that new user gets redirected to login page
    When I proceed to checkout
    Then I get redirected to the "Amazon Registration" page




