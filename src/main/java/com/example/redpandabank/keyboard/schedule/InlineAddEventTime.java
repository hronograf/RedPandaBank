package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineAddEventTime {
    public InlineKeyboardMarkup getInline() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Добавить еще день и время", Command.SAVE_EVENT_DAY.getName())
                .endRow()
                .row()
                .button("Готово!", Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
