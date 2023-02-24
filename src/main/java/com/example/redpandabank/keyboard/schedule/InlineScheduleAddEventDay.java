package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddEventDay implements Pressable {
    final TranslateService translateService;
    final String MONDAY = "monday";
    final String TUESDAY = "tuesday";
    final String WEDNESDAY = "wednesday";
    final String THURSDAY = "thursday";
    final String FRIDAY = "friday";
    final String SATURDAY = "saturday";

    public InlineScheduleAddEventDay(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(MONDAY),
                        Command.SAVE_EVENT_DAY.getName() + WeekDay.MONDAY.getDay())
                .extraButton(translateService.getBySlug(TUESDAY),
                        Command.SAVE_EVENT_DAY.getName() + WeekDay.TUESDAY.getDay())
                .endRow()
                .row()
                .button(translateService.getBySlug(WEDNESDAY),
                        Command.SAVE_EVENT_DAY.getName() + WeekDay.WEDNESDAY.getDay())
                .extraButton(translateService.getBySlug(THURSDAY),
                        Command.SAVE_EVENT_DAY.getName() + WeekDay.THURSDAY.getDay())
                .endRow()
                .row()
                .button(translateService.getBySlug(FRIDAY),
                        Command.SAVE_EVENT_DAY.getName() + WeekDay.FRIDAY.getDay())
                .extraButton(translateService.getBySlug(SATURDAY),
                        Command.SAVE_EVENT_DAY.getName() + WeekDay.SATURDAY.getDay())
                .endRow()
                .build();
    }
}