package com.secacon.secasignbox.sdk;

import java.util.UUID;

// TODO: Change the values bellow and delete this TODO
public class Values {

    public static final String URL = "https://secasignbox-development.secacon.com";

    public static final String USERNAME = "CHANGE_ME";

    public static final String PASSWORD = "CHANGE_ME";

    public static final UUID SIGNING_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    static {
        // Ensure the username and password were changed
        if (USERNAME.equals("CHANGE_ME") || PASSWORD.equals("CHANGE_ME") || SIGNING_ID.toString().equals("00000000-0000-0000-0000-000000000000")) {
            throw new RuntimeException("Please read the README.md and change the values in src/test/java/com/secacon/secasignbox/sdk/Values.java!");
        }
    }
}
