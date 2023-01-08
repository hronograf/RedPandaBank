package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.stream.Collectors;

@PackagePrivate
@Component
public class InlineEditScheduleEventFieldButton {
    final static String SEPARATOR = ":";

    public InlineKeyboardMarkup getInline(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Название урока: " + lesson.getTitle(),
                        Command.EDIT_SCHEDULE_EVENT_FIELD.getName() + SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button("Имя учителя: " + lesson.getTeacher(),
                        Command.EDIT_SCHEDULE_EVENT_TEACHER.getName() + SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button("Время начала урока: " + getStartTime(lesson),
                        Command.EDIT_SCHEDULE_EVENT_START_TIME.getName() + SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button("Длительность урока: " + lesson.getDuration(),
                        Command.EDIT_SCHEDULE_EVENT_DURATION.getName() + SEPARATOR + lesson.getTitle())
                .endRow()
                .build();
    }

    public String getStartTime(Lesson lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = lesson.getLessonSchedules().stream()
                .map(lessonSchedule -> lessonSchedule.getLessonStartTime() + " ")
                .collect(Collectors.toList());
        String string = stringBuilder.append(stringList).toString();
        return string.substring(1, string.length() - 1);
    }
}
