Feature: Orange HRM application cases
  @Orange
  Scenario Outline: Login with Valid credentials and verify the homePage contains <tab>
    Given user is on login page
    When user enters <userName> and <password> and clicks on Login buttton
    And user validates the Home Page Title
    Then user validates the Homapage contains <tab>

    Examples:
      | userName | password | tab                               |
      | Admin    | admin123 | Employees on Leave Today          |
      | Admin    | admin123 | Buzz Latest Posts                 |
      | Admin    | admin123 | Employee Distribution by Sub Unit |
      | Admin    | admin123 | Employee Distribution by Sub Unit |