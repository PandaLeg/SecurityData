package com.lab.rsa.service;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.Key;

@Component
public class RSAEncryptDecrypt {
    public static byte[] encrypt(String original, Key publicKey) {
        if (original != null && publicKey != null) {
            byte[] bs = original.getBytes();
            byte[] encData = convert(bs, publicKey, Cipher.ENCRYPT_MODE);
            return encData;
        }
        return null;
    }
    public static byte[] decrypt(byte[] encrypted, Key privateKey) {
        if (encrypted != null && privateKey != null) {
            byte[] decData = convert(encrypted, privateKey, Cipher.DECRYPT_MODE);
            return decData;
        }
        return null;
    }
    private static byte[] convert(byte[] data, Key key, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(RSAConstants.ALGORITHM);
            cipher.init(mode, key);
            byte[] newData = cipher.doFinal(data);
            return newData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
