package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleChangeDuration implements InlineHandler<Update> {
    final ChildService childService;
    final TranslateService translateService;
    final String ENTER_LESSON_DURATION_AGAIN = "enter-lesson-duration-again";

    public InlineScheduleChangeDuration(ChildService childService,
                                        TranslateService translateService) {
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        Child child = childService.getById(childId).get();
        child.setState(State.SAVE_DURATION.getState());
        child.setIsSkip(false);
        childService.create(child);
        String content = translateService.getBySlug(ENTER_LESSON_DURATION_AGAIN);
        return new MessageSenderImpl().sendEditMessage(childId, messageId, content);
    }
}
