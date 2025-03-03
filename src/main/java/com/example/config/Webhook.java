package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.WebhookBot;

@Component
public abstract class Webhook implements WebhookBot {

    @Value("${telegram.bot.token}")
    private String BOT_TOKEN;

    @Value("${telegram.bot.username}")
    private String BOT_USERNAME;

    @Value("${telegram.bot.webhook-url}")
    private String WEBHOOK_URL;


    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotPath() {
        return WEBHOOK_URL;
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }
}
