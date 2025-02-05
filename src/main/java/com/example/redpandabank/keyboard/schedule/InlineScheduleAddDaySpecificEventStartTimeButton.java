package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddDaySpecificEventStartTimeButton
        implements PressableWithArgument<ReplyKeyboard, Lesson> {
    TranslateService translateService;
    static final String ADD_LESSON_START = "add-lesson-start";
    static final String ADD_ANOTHER_DAY = "add-another-day";
    static final String FINISH = "finish";

    public InlineScheduleAddDaySpecificEventStartTimeButton(
            TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(ADD_LESSON_START),
                        Command.ADD_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button(translateService.getBySlug(ADD_ANOTHER_DAY),
                        Command.ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(FINISH), Command.TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
