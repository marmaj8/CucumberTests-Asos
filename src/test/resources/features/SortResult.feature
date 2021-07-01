Feature: Search results sorting

  Background:
    Given Opened home page

  Scenario Outline: Filtering of search results by price
    Given Opened search result for '<search term key>'
    When Customer select sorting by price '<sort option key>'
    Then Search result are sorted

    Examples:
      | search term key            | sort option key          |
      | default.search.term        | sort.by.price.ascending  |
      | multiple.words.search.term | sort.by.price.descending |