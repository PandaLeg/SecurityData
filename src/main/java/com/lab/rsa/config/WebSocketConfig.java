package com.lab.rsa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// Аннотация @EnableWebSocketMessageBroker включает Websocket сервер.
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * В данном методе мы регистрируем конечную точку, которую клиенты будут использовать,
     чтобы подключиться к нашему Websocket-серверу
     * SockJS - для браузеров не поддерживающих WebSocket
     * STOMP – это Simple Text Oriented Messaging Protocol. Это протокол обмена сообщениями,
     задающий формат и правила обмена.
    */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    /**
     * В данном методе, происходит настройка брокера сообщений, который будет использоваться для направления сообщений
     от одного клиента к другому
     * В первой строке, сообщения чей адрес(куда отправлены) начинается с /app, должны быть направлены в метод,
     занимающиеся обработкой
     * Во второй строке, сообщения, чей адрес начинается с /topic, должны быть направлены в брокер сообщений
     Брокер перенаправляет сообщения всем клиентам, подписанным на тему
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}
