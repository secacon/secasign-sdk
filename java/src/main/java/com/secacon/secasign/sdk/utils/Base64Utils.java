package com.secacon.secasign.sdk.utils;

import java.util.Base64;
import java.util.regex.Pattern;

public class Base64Utils {

    // Private constructor
    private Base64Utils() {
    }

    public static boolean isBase64String(String value) {
        String regex = "([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }

    public static String encodeBytes(byte[] value) {
        return Base64.getEncoder().encodeToString(value);
    }

    public static byte[] decodeString(String value) {
        if (!isBase64String(value)) {
            throw new RuntimeException("Der übergebene Wert ist nicht Base64 enkodiert.");
        }
        return Base64.getDecoder().decode(value);
    }
}
