Feature: Title of your feature
  I want to use this template for my feature file

  @Pic2WordApiCall
  Scenario Outline: This scenario will test the Apis from Registering to Payments
    Given I want to write test Apis for <deviceId>
    When I send <email> <androidId> <operator> <deviceToken> <appVersion> <AndroidVersion>
    Then I verify the <status>

    Examples: 
      | deviceId          | email                | androidId          | operator | deviceToken  | appVersion | androidVersion | status |
      | "356478044941632" | "testdb12@gmail.com" | "3f8l4573725e9a56" | "airtel" | "djbmmnEtMQ" | "0.5"      | "7.0"          | "true" |
