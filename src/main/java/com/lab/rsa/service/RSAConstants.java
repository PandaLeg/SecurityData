package com.lab.rsa.service;

import org.springframework.stereotype.Component;

@Component
public final class RSAConstants {
    private RSAConstants() {
    }

    public static final String ALGORITHM = "RSA";
    public static final int ALGORITHM_BITS = 2048;
}
