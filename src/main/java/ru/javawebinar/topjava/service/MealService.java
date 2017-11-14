package ru.javawebinar.topjava.service;

import org.springframework.cglib.core.Local;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {
    Meal create(Meal meal);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal, int userId);

    Collection<Meal> getAll(int userId);

    List<Meal> getFilteredByTime(int userId, LocalTime startTime, LocalTime endTime);

    List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate);
}