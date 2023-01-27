package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineAddEventByWeekday  implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Monday", "/scheduleMonday")
                .extraButton("Tuesday", "/scheduleTuesday")
                .endRow()
                .row()
                .button("Wednesday", "/scheduleWednesday")
                .extraButton("Thursday", "/scheduleThursday")
                .endRow()
                .row()
                .button("Friday", "/scheduleThursday")
                .extraButton("Saturday", "/scheduleSaturday")
                .endRow()
                .build();
    }
}
