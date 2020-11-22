@all
  Feature: Checkout

    Background:
      Given I signed in my Amazon account
      And I am in basket page
      And I proceed to checkout

    @checkout
    Scenario: Verify address, payment and send costs
      Then I see that all requirements about delivery are shown