package com.secacon.secasign.extension;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Test
@Retention(RetentionPolicy.RUNTIME)
public @interface SdkTestRequirements {

    boolean isLoginAvailable() default false;

    boolean isArchivingAvailable() default false;

    boolean isSigningAvailable() default false;
}
