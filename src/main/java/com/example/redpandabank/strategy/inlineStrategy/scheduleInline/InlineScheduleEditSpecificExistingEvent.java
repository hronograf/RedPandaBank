package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.schedule.InlineScheduleEditEventFieldButton;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonScheduleService;
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
public class InlineScheduleEditSpecificExistingEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final LessonScheduleService lessonScheduleService;
    final InlineScheduleEditEventFieldButton inlineScheduleEditEventFieldButton;
    final InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder;

    public InlineScheduleEditSpecificExistingEvent(LessonService lessonService,
                                                   LessonScheduleService lessonScheduleService,
                                                   InlineScheduleEditEventFieldButton inlineScheduleEditEventFieldButton,
                                                   InlineKeyboardMarkupBuilderImpl inlineKeyboardMarkupBuilder) {
        this.lessonService = lessonService;
        this.lessonScheduleService = lessonScheduleService;
        this.inlineScheduleEditEventFieldButton = inlineScheduleEditEventFieldButton;
        this.inlineKeyboardMarkupBuilder = inlineKeyboardMarkupBuilder;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        String title = parseTitle(update.getCallbackQuery().getData());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        InlineKeyboardMarkup inline = inlineScheduleEditEventFieldButton.getInline(lesson);
        String content = "Какое поле в уроке <i>\"" + lesson.getTitle() + "\"</i> ты хочешь исправить?";
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, content);
    }

    private String parseTitle(String command) {
        String[] split = command.split(LessonService.COLON_SEPARATOR);
        return split[1];
    }
}
