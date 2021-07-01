Feature: Registration

  Background:
    Given Opened home page

  Scenario Outline: Registration with incorrect data
    Given Opened registration page
    When Customer in registration enter email '<email key>'
    And Customer in registration enter first name '<first name key>'
    And Customer in registration enter last name '<last name key>'
    And Customer in registration enter password '<password key>'
    And Try to register
    Then Following registration errors shows '<email error key>', '<first name error key>', '<last name error key>', '<password error key>'

    Examples:
      | email key            | first name key     | last name key     | password key       | email error key            | first name error key         | last name error key         | password error key            |
      | empty.string         | empty.string       | empty.string      | empty.string       | no.email.register.error    | no.first.name.register.error | no.last.name.register.error | no.password.register.error    |
      | without.at.email     | default.first.name | empty.string      | too.short.password | wrong.email.register.error | empty.string                 | no.last.name.register.error | short.password.register.error |
      | without.domain.email | empty.string       | default.last.name | minimal.password   | wrong.email.register.error | no.first.name.register.error | empty.string                | empty.string                  |