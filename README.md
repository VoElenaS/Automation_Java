Stomanager Project Automation The Stomanager Project is a warehouse and order management system that I automated from scratch. 
I created a testing framework using various design techniques such as 
boundary value analysis, equivalence classes, decision tables, state transitions testing, and error guessing (exploratory testing).

The automation framework was designed with clarity, where each API service is separated for better maintainability. 
Each service has its own data generator, and I used base classes for common utilities to avoid code repetition.

Key Technologies Used: JUnit 5: For writing and running tests.

RestAssured: For API testing.

Selenium: For end-to-end UI testing.

Lombok: To reduce boilerplate code.

Builder Pattern: For creating complex objects.

TestProperties: For handling test configurations.

UserQueries: For interacting with test data during automation.

Structure: The testing framework ensures that each API service is isolated and easy to test. Each service has:

A separate request and response model.

Custom data generators.

Shared base classes for common functionality.
