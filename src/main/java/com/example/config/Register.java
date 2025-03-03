package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;

@Configuration
@RequiredArgsConstructor
public class Register {

    @Value("${telegram.bot.token}")
    private String BOT_TOKEN;

    @Value("${telegram.bot.webhook-url}")
    private String BOT_WEBHOOK_URL;

    private final RestTemplate restTemplate;

    public void registerWebhook() {
        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/setWebhook?url=" + BOT_WEBHOOK_URL;

        try {

            ApiResponse<?> response = restTemplate
                    .postForObject(url, null, ApiResponse.class);

            if (response != null && response.getOk()) {
                System.out.println("✅ Webhook successfully installed: " + BOT_WEBHOOK_URL);
            } else {
                System.out.println("❌ Error installing webhook: " +
                        (response != null ? response.getErrorDescription() : "Unknown error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
