Feature: Test online calculator scenarios
Scenario Outline: Test subtraction, division and CE functionalities
Given Open chrome browser and start application
When I enter following values and press CE button <value1> and <value2> with operator <operator>
Then I should be able to see <expected>
Examples:
	| value1 | value2 | operator | expected |
	| 2      | 2      | /        | 1        |
	| 2      | 2      | +        | 4        |
	| 2      | 2      | -        | 0        |