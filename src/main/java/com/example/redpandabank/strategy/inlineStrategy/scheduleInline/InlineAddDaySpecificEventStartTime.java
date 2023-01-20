package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.keyboard.schedule.InlineAddDaySpecificEventStartTimeButton;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.model.LessonSchedule;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonScheduleService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Comparator;
import java.util.List;

@PackagePrivate
@Component
public class InlineAddDaySpecificEventStartTime implements InlineHandler<Update> {
    final LessonScheduleService lessonScheduleService;
    final LessonService lessonService;
    final ChildService childService;
    final InlineAddDaySpecificEventStartTimeButton inlineAddDaySpecificEventStartTimeButton;

    public InlineAddDaySpecificEventStartTime(LessonScheduleService lessonScheduleService,
                                              LessonService lessonService, ChildService childService, InlineAddDaySpecificEventStartTimeButton inlineAddDaySpecificEventStartTimeButton) {
        this.lessonScheduleService = lessonScheduleService;
        this.lessonService = lessonService;
        this.childService = childService;
        this.inlineAddDaySpecificEventStartTimeButton = inlineAddDaySpecificEventStartTimeButton;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String day = parseData(update.getCallbackQuery().getData());
        String title = parseTitle(update.getCallbackQuery().getMessage().getText());
        Lesson lesson = lessonService.findLessonByTitle(childId, title);
        List<LessonSchedule> lessonSchedules = lesson.getLessonSchedules();
        lessonSchedules.sort((lessonSchedule1, lessonSchedule2) -> comparator.compare(lessonSchedule1, lessonSchedule2));
        LessonSchedule lessonSchedule = lessonSchedules.get(lessonSchedules.size() - 1);
        lessonSchedule.setDay(day);
        lessonScheduleService.create(lessonSchedule);
        Child child = childService.findByUserId(childId);
        child.setState(State.NO_STATE.getState());
        child.setIsSkip(false);
        childService.create(child);
        InlineKeyboardMarkup inline = inlineAddDaySpecificEventStartTimeButton.getInline(lesson);
        String response = "День добавили для урока <i>\"" + lesson.getTitle() + "\"</i> , может чтото еще интересно?";
        String infoLesson = lessonService.getInfoLessonbyId(lesson.getLessonId());
        new MessageSenderImpl().sendMessageViaURL(childId, infoLesson);
        return new MessageSenderImpl().sendEditMessageWithInline(childId, messageId, inline, response);
    }

    Comparator<LessonSchedule> comparator = new Comparator<LessonSchedule>() {
        @Override
        public int compare(LessonSchedule lessonSchedule1, LessonSchedule lessonSchedule2) {
            return lessonSchedule1.getLessonScheduleId().compareTo(lessonSchedule2.getLessonScheduleId());
        }
    };

    private String parseData(String data) {
        return data.split(LessonService.COLON_SEPARATOR)[1];
    }

    private String parseTitle(String text) {
        return text.split(LessonService.QUOTE_SEPARATOR)[1];
    }

}
