package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddEventDurationButton implements Pressable {
    TranslateService translateService;
    final String CHANGE_LESSON_DURATION = "change-lesson-duration";
    final String NEXT = "next";

    public InlineScheduleAddEventDurationButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(CHANGE_LESSON_DURATION),
                        Command.EDIT_EVENT_DURATION.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(NEXT), Command.SAVE_EVENT_DAY.getName())
                .endRow()
                .build();
    }
}
