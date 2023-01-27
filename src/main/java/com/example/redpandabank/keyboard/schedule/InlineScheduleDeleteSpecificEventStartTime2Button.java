package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleDeleteSpecificEventStartTime2Button implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Удалить еще одно начало урока", Command.DELETE_SPECIFIC_EVENT_START_TIME.getName())
                .endRow()
                .row()
                .button("Я закончил!", Command.TO_MAIN_MENU.getName())
                .endRow()
                .build();    }
}
