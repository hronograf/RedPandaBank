package com.example.redpandabank.model;

import lombok.Getter;

@Getter
public enum Command {
    START("/start"),

    SCHEDULE("Расписание"),
    EDIT_SCHEDULE("Редактировать"),
    ALL_LESSONS("Покажи все уроки"),
    ADD_SCHEDULE_EVENT("Новый урок"),
    SAVE_EVENT_NAME("/saveName"),
    SAVE_EVENT_DESCRIPTION("/saveDesc"),
    SAVE_EVENT_DURATION("/saveDuration"),
    BACK_TO_MAIN_MENU("Главное меню");
    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
