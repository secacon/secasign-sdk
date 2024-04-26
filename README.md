# Secasign SDK

## Description

This project provides the Java-SDK for Secasign. The layout is as following:

* `src/main/java` contains the implementation of the Java-SDK HTTP-client.
* `src/test/java` contains several unit tests. Each unit test represents a use-case, e.g. login and signing a PDF-document with a visual signature.
* `data` contains the resources, e.g. PDF-documents and the graphical signature used for signing, which can be changed. This directory is also used to store signed documents.

## Getting started

Getting started requires the following steps:

1.) Secacon provides a PDF-documentation together with this SDK.

2.) The developer has to copy the template file from `src/test/java/com/secacon/secasign/sdk/ValuesExample.java` to `src/test/java/com/secacon/secasign/sdk/Values.java`. The file `ValuesExample.java` serves as an example template whereas `Values.java` contains the values used for the tests.

3.) The developer has to fill in all values into `Values.java` provided by Secacon. Depending on the customer and his use cases, not all values will be provided. Only change the values you were provided. The unit tests are modular and tests that require values that are not configured (null) will be skipped. Example: Setting the login-in and sign documents values will only execute the login test and the signing tests - but not other tests.

3.) After creating and saving the file, execute the unit tests. 

## Notes

* This Java-SDK requires Java 8 or higher.

* The Java HTTP-client only depends on the Jackson object mapper and uses the legacy Java HTTP-client. It is up to the customer to modify the client, e.g. port it to the new HTTP client in Java 11+ or replace the JSON library.

* This SDK only provides an HTTP-client with several examples. Please study the PDF-documentation carefully because there all domain knowledge and possible settings are described (Especially for PDF signing and archiving).

## Contact

Feel free to contact Simon Wächter (simon.waechter@secacon.com) in case of problems or questions.
