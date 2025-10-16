# Secasign SDK

## Description

This project provides the SDK for Secasign with two available implementations:

* A Java 17+ implementation with Jackson in the `java` directory
* A pure C#/.NET 8 implementation in the `csharp` directory

Notes:

* Both implementations provide a.) an HTTP client and b.) several test cases to cover all important uses cases. For configuration, the file `configuration.properties` in the project directory is used. By default, all test cases will be skipped and it is up to the developer to configure the values in this properties file (Secacon will provide these required values for the developer).
* The Java client is tested with Java 17+, but it can be backported to Java 8 (Switch back to an older Gradle and Jackson version).

## Getting started

For getting started, follow these steps:

1.) Copy the `configuration-template.properties` to `configuration.properties`.

2.) Edit this `configuration.properties` and add all values provided by Secacon.

3.) Open your desired implementation in IntelliJ IDEA or Visual Studio/Rider.

4.) Build the project.

5.) Run the relevant test cases provided by Secacon.

6.) Get familiar with the test cases. Change the code or copy it to create an own implementation.

## Test cases

The test cases are structured after the Secasign OpenAPI tags (https://secasign.secacon.com/swagger-ui/index.html):

* Authentication: A test to log in and obtain a JWT-token.
* Document: Two tests to get document information and search and download them.
* Sign: Three tests to sign-only and sign/archive documents.

## Notes

This SDK only provides an HTTP client with several test cases serving as example. Please study the documentation (https://documentation.secacon.com/) carefully because there all domain knowledge and possible settings are described (Especially for PDF signing and archiving).

## Contact

Feel free to contact Simon Wächter (simon.waechter@secacon.com) in case of problems or questions.
