package com.lab.rsa.service;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Component
public class RSAKeyPair {
    public static KeyPair keyPairRSA() {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance(RSAConstants.ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (generator != null) {
            generator.initialize(RSAConstants.ALGORITHM_BITS);
            return generator.genKeyPair();
        }
        return null;
    }
}
