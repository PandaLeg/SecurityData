package com.lab.rsa.service;

import com.lab.rsa.model.ChatMessage;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;

@Service
public class ChatService {
    KeyPair keyPair;

    public KeyPair rsa() {
        // Генерируем пару ключ
        keyPair = RSAKeyPair.keyPairRSA();
//        // Возвращаем публичный ключ
//        Key publicKey = keyPair.getPublic();
//        // Возвращаем приватный ключ
//        Key privateKey = keyPair.getPrivate();
//        System.out.println("Encrypt Start");
//        System.out.println("Original: " + username);
//        // Шифруем
//        byte[] encrypted = RSAEncryptDecrypt.encrypt(username, privateKey);
//        System.out.println("Encrypted: " + new String(encrypted));
//        System.out.println("Encrypt End");
//        System.out.println();
//        System.out.println("Decrypt Start");
//        byte[] decrypted = RSAEncryptDecrypt.decrypt(encrypted, publicKey);
//        System.out.println("Decrypted: " + new String(decrypted));
//        System.out.println("Decrypted matches Original: " + Arrays.equals(decrypted, username.getBytes()));
//        System.out.println("Decrypt End");
        return keyPair;
    }

    public String convert(Key key, ChatMessage chatMessage) {
        String decoded;
        try {
            Cipher cipher = Cipher.getInstance(RSAConstants.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decoded = new String(cipher.doFinal(Base64.decodeBase64(chatMessage.getContent())));
            return decoded;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
