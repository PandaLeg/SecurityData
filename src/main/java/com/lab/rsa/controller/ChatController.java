package com.lab.rsa.controller;

import com.lab.rsa.model.ChatMessage;
import com.lab.rsa.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

// В данном контроллере присутствуют методы, отвечающие за получение сообщения от одного клиента
// и трансляцию его всем остальным. Добавление пользователя и его сообщения транслируются всем, кто подключен к чату
@Controller
public class ChatController {
    @Autowired
    ChatService chatService;

    /**
     * В конфигурации было указано, что все сообщения от клиентов, направленные по адресу,
     * начинающемуся с /app, будут перенаправлены в соответствующие методы. Имелись в виду как раз методы,
     * аннотированные @MessageMapping.
     * Например, сообщение, направленное по адресу /app/chat.sendMessage будет перенаправлено в метод sendMessage().
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }

    // В данном методе обрабывается вход в чат
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor){
        // Добавить пользователя в веб сокет версию
        chatService.rsa(chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
