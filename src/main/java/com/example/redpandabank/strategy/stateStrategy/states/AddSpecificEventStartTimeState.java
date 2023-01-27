package com.example.redpandabank.strategy.stateStrategy.states;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineAddSpecificEventStartTimeButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.*;
import com.example.redpandabank.strategy.stateStrategy.CommandCheckable;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalTime;
import java.util.List;

@PackagePrivate
@Component
public class AddSpecificEventStartTimeState implements StateHandler<Update>, CommandCheckable {
    Long userId;
    LocalTime localTime;
    final ChildService childService;
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineAddSpecificEventStartTimeButton startTimeButton;

    public AddSpecificEventStartTimeState(ChildService childService, LessonService lessonService,
                                          LessonScheduleService lessonScheduleService,
                                          InlineAddSpecificEventStartTimeButton startTimeButton) {
        this.childService = childService;
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.startTimeButton = startTimeButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update, TelegramBot telegramBot) {
        userId = UpdateInfo.getUserId(update);
        localTime = parseTime(UpdateInfo.getText(update));
        Child child = childService.findByUserId(userId);
        if (checkCommand(localTime.toString(), child)) {
            String title = parseTitleFromState(child.getState());
            LocalTime localTime = parseTime(update.getMessage().getText());
            Lesson lesson = lessonService.findLessonByTitle(userId, title);
            List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
            LessonSchedule lessonSchedule = new LessonSchedule();
            lessonSchedule.setLessonStartTime(localTime);
            lessonSchedule.setChildId(userId);
            lessonSchedules.add(lessonSchedule);
            lessonScheduleService.create(lessonSchedule);
            lessonService.create(lesson);
            child.setState(State.NO_STATE.getState());
            child.setIsSkip(false);
            childService.create(child);
            String response = "Новое время уже добавили для урока <i>\"" + lesson.getTitle() + "\"</i>,"
                    + " а напомни мне для какого дня недели это время?";
            InlineKeyboardMarkup inline = startTimeButton.getInline();
            return new MessageSenderImpl().sendMessageWithInline(userId, response, inline);
        } else {
            return  goBackToTelegramBot(child, childService, telegramBot, update);
        }
    }

    @Override
    public boolean checkCommand(String command, Child child) {
        return CommandCheckable.super.checkCommand(command, child);
    }

    private LocalTime parseTime(String text) {
        //TODO пропусти регексом, что бы проходили только цифры
        String[] response = text.split(":");
        return LocalTime.of(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }

    private String parseTitleFromState(String name) {
        return name.split(LessonService.COLON_SEPARATOR)[1];
    }

}
