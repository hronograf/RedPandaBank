package com.example.redpandabank.service;

import com.example.redpandabank.model.Lesson;

import java.util.List;

public interface LessonService {
    Lesson create(Lesson category);

    void deleteById(Long id);

    Long getLessonsQuantity(Long id);


    List<Lesson> findLessonByChildIdAndWeekDay(Long userId, String day);

    List<Lesson> findAllByChildId(Long childId);

    String getLessonsByDayAndChildId(Long userId, String day);

    Lesson getById(Long id);

    Boolean findAllByTitle(String title, Long childId);

    Lesson findLessonByTitleAndChildId(Long childId, String title);

    String getDuration(Integer duration);

    String getStartTime(Lesson lesson);

    String getFinishTime(Lesson lesson);

    String getInfoLessonbyId(Long id);
}
