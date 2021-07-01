Feature: Manage bag

  Background:
    Given Opened home page

  Scenario Outline: Add product to the bag
    Given Opened first product page for search term '<search term key>'
    When Customer select any options of product
    And Customer add product to the bag
    And Open bag page
    Then Product is show in the bag

    Examples:
      | search term key            |
      | default.search.term        |
      | multiple.words.search.term |

  Scenario Outline: Cannot add product to the bag without selected options
    Given Opened first product page for search term '<search term key>'
    When Customer add product to the bag
    Then Not selected options error showed

    Examples:
      | search term key            |
      | default.search.term        |
      | multiple.words.search.term |

  Scenario Outline: Change of product quantity in bag change total price
    Given First product for search term '<search term key>' added to the bag
    And Opened bag page
    When Customer change quantity of product to "<quantity key>"
    Then Total price for basket will be recalculated

    Examples:
      | search term key            | quantity key                |
      | default.search.term        | default.change.quantity     |
      | multiple.words.search.term | alternative.change.quantity |

  Scenario Outline: Removing product from the bag change total price
    Given Added to bag first products for search terms
      | default.search.term        | alternative.search.term |
    And Opened bag page
    When Remove "<product number key>" product from list
    Then Total price for basket will be recalculated

    Examples:
      | product number key            |
      | first.product.on.list.number  |
      | second.product.on.list.number |