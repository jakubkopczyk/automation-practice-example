# automation-practice-example
Project is created using Java 8, Spring Boot, Selenium Webdrived and Cucumber
To run the project:
1. Choose a tag from feature files
* Supported tags: `@authentication`,`@customerService`
, `@registration`, `@searching`, `@shopping`, `@socialMedia`, `@validateRegistration`
2. Run tests using `mvn test -Dcucumber.options="--tags @TAG"`
3. To generate the report use `mvn cluecumber-report:reporting`