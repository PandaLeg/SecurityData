package com.lab.rsa.service;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;
import java.util.Arrays;

@Service
public class ChatService {

    public void rsa(String usernameFromSession){
        String username = usernameFromSession;
        // Генерируем ключ
        KeyPair keyPair = RSAKeyPair.keyPairRSA();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        System.out.println("Encrypt Start");
        System.out.println("Original: " + username);
        // Шифруем
        byte[] encrypted = RSAEncryptDecrypt.encrypt(username, privateKey);
        System.out.println("Encrypted: " + new String(encrypted));
        System.out.println("Encrypt End");
        System.out.println();
        System.out.println("Decrypt Start");
        byte[] decrypted = RSAEncryptDecrypt.decrypt(encrypted, publicKey);
        System.out.println("Decrypted: " + new String(decrypted));
        System.out.println("Decrypted matches Original: " + Arrays.equals(decrypted, username.getBytes()));
        System.out.println("Decrypt End");
    }
}
