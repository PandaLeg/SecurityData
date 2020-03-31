package com.lab.rsa.model;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.KeyPair;


@Service
public class ChatMessage {
    private MessageType type;
    private String sender;
    private String protection;
    private String content;
    private KeyPair keyPair;


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        SERVER
    }


    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProtection() {
        return protection;
    }

    public void setProtection(String protection) {
        this.protection = protection;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }
}
