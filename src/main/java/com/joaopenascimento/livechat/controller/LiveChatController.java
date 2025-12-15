package com.joaopenascimento.livechat.controller;

import com.joaopenascimento.livechat.domain.ChatInput;
import com.joaopenascimento.livechat.domain.ChatOutput;
import com.joaopenascimento.livechat.domain.PrivateChatInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class LiveChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/new-message")
    @SendTo("/topics/livechat")
    public ChatOutput newMessage(ChatInput input) {
        return new ChatOutput(HtmlUtils.htmlEscape(input.user() + ": " + input.message()));
    };

    @MessageMapping("/private-message")
    public void sendPrivateMessage(@Payload PrivateChatInput input, Principal principal) {
        String sender = principal.getName();

        messagingTemplate.convertAndSendToUser(
                input.recipient(),
                "/queue/private",
                new ChatOutput(HtmlUtils.htmlEscape("[Privado de " + sender + "]: " + input.message()))
        );

        messagingTemplate.convertAndSendToUser(
                sender,
                "/queue/private",
                new ChatOutput(HtmlUtils.htmlEscape("[Privado para " + input.recipient() + "]: " + input.message()))
        );
    }
}
