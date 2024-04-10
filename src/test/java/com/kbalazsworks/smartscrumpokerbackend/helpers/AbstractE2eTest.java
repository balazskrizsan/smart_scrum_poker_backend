package com.kbalazsworks.smartscrumpokerbackend.helpers;

import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.junit.After;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import static java.util.concurrent.TimeUnit.SECONDS;

abstract public class AbstractE2eTest extends AbstractIntegrationTest
{
    private StompSession stompSession = null;

    protected StompSession getStompSession() throws Exception
    {
        StandardWebSocketClient client = new StandardWebSocketClient();
        client.getUserProperties().clear();
        client.getUserProperties().put("org.apache.tomcat.websocket.SSL_CONTEXT", getSslContext());

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompSession = stompClient.connectAsync(
            "wss://localhost:9999/ws",
            new StompSessionHandlerAdapter()
            {
            }
        ).get(10, SECONDS);

        return stompSession;
    }

    @After
    public void e2eAfter()
    {
        if (null != stompSession)
        {
            stompSession.disconnect();
        }
    }

    private KeyStore keyStore(String file, char[] password) throws Exception
    {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        File key = ResourceUtils.getFile(file);
        try (InputStream in = new FileInputStream(key))
        {
            keyStore.load(in, password);
        }

        return keyStore;
    }

    private SSLContext getSslContext() throws Exception
    {
        char[] password = "password".toCharArray();

        return SSLContextBuilder
            .create()
            .loadKeyMaterial(
                keyStore(applicationProperties.siteP12KeyStoreFilePath(), password),
                password
            )
            .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
    }
}
