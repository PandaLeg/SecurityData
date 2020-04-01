package com.lab.rsa.controller;

import com.lab.rsa.model.ChatMessage;
import com.lab.rsa.service.ChatService;
import com.lab.rsa.service.RSAEncryptDecrypt;
import com.lab.rsa.service.RSAKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import javax.xml.bind.DatatypeConverter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

// В данном контроллере присутствуют методы, отвечающие за получение сообщения от одного клиента
// и трансляцию его всем остальным. Добавление пользователя и его сообщения транслируются всем, кто подключен к чату
@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private KeyPair keyPair = RSAKeyPair.keyPairRSA();

    public ChatController() throws NoSuchAlgorithmException {
    }

    /**
     * В конфигурации было указано, что все сообщения от клиентов, направленные по адресу,
     * начинающемуся с /app, будут перенаправлены в соответствующие методы. Имелись в виду как раз методы,
     * аннотированные @MessageMapping.
     * Например, сообщение, направленное по адресу /app/chat.sendMessage будет перенаправлено в метод sendMessage().
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
//        System.out.println("Message: " + chatMessage.getContent());
//        System.out.println("Public key: " + keyPair.getPublic());
//        System.out.println("Message: " + keyPair.getPrivate());
        byte[] encrypted = RSAEncryptDecrypt.encrypt(chatMessage.getContent(), keyPair.getPublic());
        System.out.println("Encrypted message: " + new String(encrypted));
        byte[] decrypted = RSAEncryptDecrypt.decrypt(encrypted, keyPair.getPrivate());
        System.out.println("Decrypted message: " + new String(decrypted));
        chatMessage.setContent(new String(decrypted));
        return chatMessage;
    }

    // В данном методе обрабывается вход в чат
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Добавить пользователя в веб сокет версию
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        ChatMessage ch = new ChatMessage();
        ch.setType(ChatMessage.MessageType.SERVER);
        ch.setSender("server");
        ch.setContent(DatatypeConverter.printBase64Binary(keyPair.getPublic().getEncoded()));
        byte[] encrypted = RSAEncryptDecrypt.encrypt(ch.getSender(), keyPair.getPublic());
        System.out.println("Encrypt: " + new String(encrypted));
        byte[] decrypted = RSAEncryptDecrypt.decrypt(encrypted, keyPair.getPrivate());
        System.out.println("Username: " + new String(decrypted));
        messagingTemplate.convertAndSend("/topic/public", ch);
        return chatMessage;
    }
}
