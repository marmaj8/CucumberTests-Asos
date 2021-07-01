Feature: Manage recently browsed

  Scenario: Recently browsed page are listed in product page with LIFO sort
    Given Opened home page
    When User enter multiple products from search
      | default.search.term     | alternative.search.term    | multiple.words.search.term |
    Then Recently entered products should be on the list


  Scenario: Clearing of recently browsed list
    Given Opened home page
    When User enter multiple products from search
      | default.search.term     | alternative.search.term    | multiple.words.search.term |
    And Click clear recently browsed buttons
    Then List of recently entered products should be cleared