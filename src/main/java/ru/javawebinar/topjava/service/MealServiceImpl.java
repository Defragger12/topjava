package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (repository.get(id) != null && repository.get(id).getUserId() != userId) {
            throw new NotFoundException("this meal is not yours");
        }
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        if (repository.get(id) != null && repository.get(id).getUserId() != userId) {
            throw new NotFoundException("this meal is not yours");
        }
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public void update(Meal meal, int userId) {
        if (meal.getUserId() != userId) {
            throw new NotFoundException("this meal is not yours");
        }
        repository.save(meal);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll().stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredByTime(int userId, LocalTime startTime, LocalTime endTime) {
        return this.getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return this.getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}