package com.example.redpandabank.model;

import lombok.Getter;

@Getter
public enum Command {
    START("/start"),
    SCHEDULE("Расписание"),
    EDIT_SCHEDULE("Редактировать"),
    ADD_SCHEDULE_EVENT("Новый урок"),
    ADD_EVENT_NAME("Название урока"),
    ALL_LESSONS("Покажи все уроки"),
    SAVE_EVENT_NAME("/saveName"),
    SAVE_EVENT_DESCRIPTION("/saveDesc")
    ;
    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
