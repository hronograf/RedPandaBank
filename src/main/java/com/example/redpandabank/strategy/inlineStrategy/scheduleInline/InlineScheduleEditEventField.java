package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleEditEventField implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final String EDIT_EVENT_FIELD = "edit-event-field";

    public InlineScheduleEditEventField(LessonService lessonService,
                                        ChildService childService,
                                        TranslateService translateService) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        Long lessonId = parseId(UpdateInfo.getData(update));
        Lesson lesson = lessonService.getById(lessonId);
        Child child = childService.findByUserId(childId);
        child.setState(State.EDIT_SPECIFIC_EVENT_FIELD.getState()
                + Separator.COLON_SEPARATOR + lesson.getLessonId());
        child.setIsSkip(false);
        childService.create(child);
        String response = translateService.getBySlug(EDIT_EVENT_FIELD) + " <i>\""
                + lesson.getTitle() + "\"</i> !";
        return new MessageSenderImpl().sendEditMessage(childId, messageId, response);
    }

    private Long parseId(String text) {
        return Long.parseLong(text.split(Separator.COLON_SEPARATOR)[1]);
    }
}
