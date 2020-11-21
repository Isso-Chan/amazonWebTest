@all @signInAll
Feature: Sign-In functionality

  Background:
    Given I am on the homepage
    And I click sign-in button

  @signIn @smoke
  Scenario: Sign In verification- Happy Path
    When I enter my username as "username"
    And I enter my password as "password"
    Then I login my account

  @invalidUsername
  Scenario Outline: Sign in with wrong username-correct password
    When I enter my username as "<username>"
    Then I get error message on Email
    Examples:
      | username         |
      | istop@gmail.com  |
      | istop@gmail.     |
      | istop45@gmail.de |

  @invalidPassword
  Scenario: Sign-in fuctionality with correct username-Wrong password
    When I enter my username as "username"
    And I enter my password as "Germany123"
    Then I get error message on password

  @NURegister
  Scenario: New user login with VALID credentials
    When I as a new user click the button to create an account
    And I enter name as "Michael"
    And I enter email as "david_ursala@mail.de"
    And I enter password as "michael123"
    And I enter password again "michael123"
    Then I see OTP verification page

  @NUInvalid
  Scenario Outline: New user login with IN_VALID credentials
    When I as a new user click the button to create an account
    And I enter name as "<name>"
    And I enter email as "<email>"
    And I enter password as "<password>"
    And I enter password again "<verify password>"
    Then I get these error messages "<nameError>"  "<emailError>" "<passwordError>" "<passwardCheckError>"
    Examples:
      | name    | email                | password | verify password | nameError | emailError | passwordError | passwardCheckError |


      |         | david_ursala@mail.de | qwerty   | qwerty          | missing   |            |               |                    |
      | Michael |                      | qwerty   | qwerty          |           | missing    |               |                    |
      | Michael | david_ursala@mail    | qwerty   | qwerty          |           | invalid    |               |                    |
      | Michael | david_ursala@mail.de |          | qwerty          |           |            | missing       | NO Warning!!!      |
      | Michael | david_ursala@mail.de | qwerty   |                 |           |            |               | missing            |
      | Michael | david_ursala@mail.de | qwert    | qwerty          |           |            | invalid       | mismatch           |
      | Michael | david_ursala@mail.de |          | qwerty          |           |            | missing       |                    |
      | Michael | david_ursala@mail.de | qwert    | qwert           |           |            | invalid       | invalid            |
      |         |                      |          |                 | missing   | missing    | missing       | NO Warning!!!      |




