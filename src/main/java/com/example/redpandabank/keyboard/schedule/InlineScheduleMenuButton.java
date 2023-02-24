package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleMenuButton implements Pressable {
    final TranslateService translateService;
    final String SHOW_SCHEDULE_FOR = "show-schedule-for";
    final String EDIT_SCHEDULE = "edit-schedule";

    public InlineScheduleMenuButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(SHOW_SCHEDULE_FOR),
                        Command.CHOOSE_EVENT_BY_DAY.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(EDIT_SCHEDULE),
                        Command.EDIT_SCHEDULE.getName())
                .endRow()
                .build();
    }
}
