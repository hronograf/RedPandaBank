package com.example.redpandabank.strategy.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
  SendMessage handle(Update update);
}