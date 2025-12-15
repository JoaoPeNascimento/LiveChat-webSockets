package com.joaopenascimento.livechat.config;

import com.sun.security.auth.UserPrincipal;
import org.jspecify.annotations.Nullable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        String uri = request.getURI().toString();
        String username = "anonimo_" + UUID.randomUUID().toString().substring(0, 5);

        if (uri.contains("user=")) {
            try {
                username = uri.split("user=")[1];
            } catch (Exception e) {
                // fallback para anonimo
            }
        }

        return new UserPrincipal(username);
    }
}
