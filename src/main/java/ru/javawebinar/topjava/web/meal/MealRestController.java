package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(AuthorizedUser.id());
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id) throws NotFoundException {
        log.info("delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) throws NotFoundException {
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id()), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    public List<MealWithExceed> getFilteredByTime(LocalTime startTime, LocalTime endTime) {
        log.info("getFilteredByTimeMeals");
        return MealsUtil.getFilteredByTime(this.getAll(), startTime, endTime);
    }

    public List<MealWithExceed> getFilteredByDate(LocalDate startDate, LocalDate endDate) {
        log.info("getFilteredByDateMeals");
        return MealsUtil.getFilteredByDate(this.getAll(), startDate, endDate);
    }
}