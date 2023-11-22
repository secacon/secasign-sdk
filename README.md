# Secasign-Box SDK

## Description

This project provides the Java-SDK for the Secasign-Box. The layout is as following:

* `src/main/java` contains the implementation of the Java-SDK HTTP-client.
* `src/test/java` contains several unit tests. Each unit test represents a use-case, e.g. login and signing a PDF-document with a visual signature.
* `data` contains the resources, e.g. PDF-documents and the graphical signature used for signing, which can be changed. This directory is also used to store signed documents.

## Getting started

Getting started requires the following steps:

1.) Secacon provides a PDF-documentation together with this SDK.

2.) This PDF-documentation contains all credentials and values used to execute these tests (They are customer specific). The developer has to create the template file `src/test/java/com/secacon/secasignbox/sdk/Values.java` (This file does not exist and not creating it will result in a compilation failure):

```java
package com.secacon.secasignbox.sdk;

import java.util.UUID;

public class Values {

    // Values used to log-in
    public static final String URL = "https://secasignbox-development.secacon.com"; // No trailing slash!
    public static final String USERNAME = null;
    public static final String PASSWORD = null;

    // Value used to sign documents
    public static final UUID SIGNING_ID = null;

    // Value used to sign and archive documents
    public static final UUID PROCESSING_RULE_ID = null;
}
```


Depending on the customer and his use case, not all values will be provided. Only change the values you were provided. The unit tests are modular and tests that require values that are not configured (null) will be skipped. Example: Setting the login-in and sign documents values will only execute the login test and the signing tests - but not other tests.

3.) After creating and saving the file, execute the unit tests. 
Please note that Java 11+ is required.

## Notes

* This Java-SDK requires Java 11 or higher.

* The Java HTTP-client only depends on the Jackson object mapper and new Java HTTP-client introduced in Java 11. It is up to the customer to modify the client, e.g. port it back to an older Java version or replace the JSON library.

* This SDK only provides an HTTP-client with several examples. Please study the PDF-documentation carefully because there all domain knowledge and possible settings are described (Especially for PDF signing and archiving).

## Contact

Feel free to contact Simon Wächter (simon.waechter@secacon.com) in case of problems or questions.
