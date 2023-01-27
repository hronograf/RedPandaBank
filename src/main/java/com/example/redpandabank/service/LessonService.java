package com.example.redpandabank.service;

import com.example.redpandabank.model.Lesson;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface LessonService {
    String COLON_SEPARATOR = ":";
    String QUOTE_SEPARATOR = "\"";
    Lesson create(Lesson category);

    void deleteById(Long id);

    Long getLessonsQuantity(Long id);


    List<Lesson> findLessonByChildIdAndWeekDay(Long userId, String day);

    HashSet<Lesson> findAllByChildId(Long childId);

    Optional<String> getLessonsByDayAndChildId(Long userId, String day);

    Lesson getById(Long id);

    Boolean findAllByTitle(String title, Long childId);

    Lesson findLessonByTitle(Long childId, String title);

    String getDuration(Integer duration);

    String getStartTime(Lesson lesson);

    String getFinishTime(Lesson lesson);

    String getInfoLessonbyIdAndSendByUrl(Long id);

    void deleteLessonByTitleAndChildId(String title, Long id);

    List<Lesson> findAllByChildIdWithoutLessonSchedule(Long childId);

}
