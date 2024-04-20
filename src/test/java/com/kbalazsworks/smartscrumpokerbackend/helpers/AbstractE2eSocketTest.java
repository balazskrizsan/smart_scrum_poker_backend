package com.kbalazsworks.smartscrumpokerbackend.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

abstract public class AbstractE2eSocketTest extends AbstractIntegrationTest
{
    @Autowired
    private InsecureKeyStoreService insecureKeyStoreService;

    private StompSession stompSession = null;

    @After
    public void e2eAfter()
    {
        if (null != stompSession)
        {
            stompSession.disconnect();
        }
    }

    protected <T> StompFrameHandler buildStompFrameHandler(
        CompletableFuture<T> completableFuture,
        Class<T> payloadType
    )
    {
        return new StompFrameHandler()
        {
            @Override
            public Type getPayloadType(StompHeaders headers)
            {
                return payloadType;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload)
            {
                completableFuture.complete((T) payload);
            }
        };
    }

    protected StompSession getStompSession() throws Exception
    {
        StandardWebSocketClient client = new StandardWebSocketClient();
        client.getUserProperties().clear();
        client.getUserProperties().put(
            "org.apache.tomcat.websocket.SSL_CONTEXT",
            insecureKeyStoreService.getSslContext()
        );

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        var converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(converter);

        stompSession = stompClient.connectAsync(
            applicationProperties.getServerSocketFullHost(),
            new StompSessionHandlerAdapter()
            {
            }
        ).get(10, SECONDS);

        return stompSession;
    }
}
