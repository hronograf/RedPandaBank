package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@PackagePrivate
@Component
public class InlineEditScheduleEvenTeacherName implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;

    public InlineEditScheduleEvenTeacherName(LessonService lessonService,
                                             ChildService childService) {
        this.lessonService = lessonService;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String title = parseData(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        Child child = childService.findByUserId(childId);
        child.setState(State.EDIT_SPECIFIC_EVENT_TEACHER_NAME.getState()
                + LessonService.COLON_SEPARATOR + lesson.getTitle());
        child.setIsSkip(false);
        childService.create(child);
        String response = "Можешь ввести новое имя учителя для урока <i>\"" + lesson.getTitle() + "\"</i> !";
        return new MessageSenderImpl().sendEditMessage(childId, messageId, response);
    }

    private String parseData(String command) {
        return command.split(LessonService.COLON_SEPARATOR)[1];
    }
}
