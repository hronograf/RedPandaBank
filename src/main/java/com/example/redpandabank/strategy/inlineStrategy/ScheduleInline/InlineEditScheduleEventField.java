package com.example.redpandabank.strategy.inlineStrategy.ScheduleInline;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.schedule.InlineEditScheduleEventFieldButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@PackagePrivate
@Component
public class InlineEditScheduleEventField implements InlineHandler<Update> {
    final LessonService lessonService;
    final InlineEditScheduleEventFieldButton inlineEditScheduleEventFieldButton;
    final InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder;
    final static String SEPARATOR = ":";


    public InlineEditScheduleEventField(LessonService lessonService,
                                        InlineEditScheduleEventFieldButton inlineEditScheduleEventFieldButton,
                                        InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder) {
        this.lessonService = lessonService;
        this.inlineEditScheduleEventFieldButton = inlineEditScheduleEventFieldButton;
        this.inlineKeyboardMarkupBuilder = inlineKeyboardMarkupBuilder;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        String title = parseTitle(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        InlineKeyboardMarkup inline = inlineEditScheduleEventFieldButton.getInline(lesson);
        String content = "Какое поле ты хочешь исправить?";
        return new MessageSenderImpl().sendMessageViaEditMessageTextWithInline(childId, messageId, inline, content);
    }

    private String parseTitle(String command) {
        String[] split = command.split(SEPARATOR);
        return split[1];
    }
}
