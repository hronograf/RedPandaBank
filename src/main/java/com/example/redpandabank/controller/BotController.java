package com.example.redpandabank.controller;

import com.example.redpandabank.service.TelegramBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class BotController {
    private final TelegramBot telegramBot;

    public BotController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping
    public BotApiMethod onUpdateReceivedParent(@RequestBody Update update) {
        return telegramBot. onWebhookUpdateReceived(update);
    }
}
