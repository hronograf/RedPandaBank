package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.keyboard.main.ReplyMainMenuButton;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@PackagePrivate
@Component
public class InlineScheduleFindLessonByDay implements InlineHandler<Update> {
    final LessonService lessonService;
    final ReplyMainMenuButton mainMenuButton;

    public InlineScheduleFindLessonByDay(LessonService lessonService,
                                         ReplyMainMenuButton mainMenuButton) {
        this.lessonService = lessonService;
        this.mainMenuButton = mainMenuButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String day = UpdateInfo.getData(update);
        Long childId = UpdateInfo.getUserId(update);
        lessonService.getLessonsByDayAndChildId(childId, day);
        ReplyKeyboardMarkup menuButton = mainMenuButton.getMainMenuButton();
        return new MessageSenderImpl().sendMessageWithReply(childId, "", menuButton);
    }
}
