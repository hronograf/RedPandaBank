package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.schedule.InlineScheduleEditSpecificEventStartTimeButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditEventLessonStartTime implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final InlineScheduleEditSpecificEventStartTimeButton
            inlineScheduleEditSpecificEventStartTimeButton;
    final TranslateService translateService;
    final String WHAT_TIME_FOR_LESSON = "what-time-for-lesson";
    final String YOU_WANT_TO_CHANGE = "you-want-to-change";

    public InlineScheduleEditEventLessonStartTime(LessonService lessonService,
                                                  ChildService childService,
                                                  InlineScheduleEditSpecificEventStartTimeButton
                                                          inlineScheduleEditSpecificEventStartTimeButton,
                                                  TranslateService translateService) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.inlineScheduleEditSpecificEventStartTimeButton
                = inlineScheduleEditSpecificEventStartTimeButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        String title = parseData(UpdateInfo.getData(update));
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        List<LocalTime> timeList = lesson.getLessonSchedules().stream()
                .map(LessonSchedule::getLessonStartTime)
                .collect(Collectors.toList());
        InlineKeyboardMarkupBuilderImpl builder = InlineKeyboardMarkupBuilderImpl.create();
        for (LocalTime localTime : timeList) {
            builder.row()
                    .button(localTime.toString(),
                            Command.EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION.getName()
                            + Separator.COLON_SEPARATOR + localTime.toString())
                    .endRow();
        }

        InlineKeyboardMarkup inline = builder.build();
        Child child = childService.findByUserId(childId);
        child.setState(State.EDIT_SPECIFIC_EVENT_START_TIME.getState());
        child.setIsSkip(false);
        childService.create(child);
        String content = translateService.getBySlug(WHAT_TIME_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i> "
                + translateService.getBySlug(YOU_WANT_TO_CHANGE);
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, content);
    }

    private String parseData(String command) {
        return command.split(Separator.COLON_SEPARATOR)[1];
    }
}
