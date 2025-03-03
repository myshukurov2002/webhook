package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/")
public class WebhookController {

    @PostMapping
    public SendMessage onWebhookUpdateReceived(@RequestBody Update update) {

        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        System.out.println("✅ Message: " + text);

        return new SendMessage(chatId.toString(), "Your Message: " + text);
    }
}