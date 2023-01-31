package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.util.Separator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleAddDaySpecificEventStartTimeButton implements
        PressableWithArgument<ReplyKeyboard, Lesson> {
    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Add another lesson start", Command.ADD_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button("Add another day for this time", Command.ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName())
                .endRow()
                .row()
                .button("I finish!", Command.TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
