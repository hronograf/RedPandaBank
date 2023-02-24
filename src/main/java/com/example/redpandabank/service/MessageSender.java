package com.example.redpandabank.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface MessageSender {
    void sendMessageViaURL(Long chatId, String content);

    SendMessage sendMessageWithInline(Long chatId, String content, ReplyKeyboard keyboard);

    SendMessage sendMessageWithReply(Long chatId, String content, ReplyKeyboard keyboard);

    SendMessage sendMessage(Long chatId, String content);
}