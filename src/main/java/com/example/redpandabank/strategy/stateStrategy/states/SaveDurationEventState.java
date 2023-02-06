package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineScheduleAddEventDurationButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class SaveDurationEventState implements StateHandler<Update>, CommandCheckable {
    final static String NUMBERS_ONLY = "\\W";
    final static String PLUG = "";
    Long userId;
    String duration;
    final ChildService childService;
    final LessonService lessonService;
    final InlineScheduleAddEventDurationButton inlineScheduleAddEventDurationButton;
    final TranslateService translateService;
    final String DURATION_FOR_LESSON = "duration-for-lesson";
    final String LESSON_DURATION_INSTALLED_CHECK = "lesson-duration-installed-check";

    public SaveDurationEventState(ChildService childService, LessonService lessonService,
                                  InlineScheduleAddEventDurationButton inlineScheduleAddEventDurationButton, TranslateService translateService) {

        this.childService = childService;
        this.lessonService = lessonService;
        this.inlineScheduleAddEventDurationButton = inlineScheduleAddEventDurationButton;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        duration = UpdateInfo.getText(update);
        Child child = childService.findByUserId(userId);
        if (checkCommand(duration, child)) {
            List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
            Lesson lesson = lessons.get(lessons.size() - 1);
            lesson.setDuration(Integer.parseInt(duration.replaceAll(NUMBERS_ONLY, PLUG)));
            lesson.setLessonId(lesson.getLessonId());
            lessonService.create(lesson);
            child.setState(State.NO_STATE.getState());
            childService.create(child);
            InlineKeyboardMarkup keyboard = inlineScheduleAddEventDurationButton.getKeyboard();
            String response = translateService.getBySlug(DURATION_FOR_LESSON)
                    + "\"<i>" + lesson.getTitle() + "\"</i> "
                    + translateService.getBySlug(LESSON_DURATION_INSTALLED_CHECK);
            return new MessageSenderImpl().sendMessageWithInline(userId, response, keyboard);
        }
        return  goBackToTelegramBot(child, childService, telegramBot, update);
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }
}
