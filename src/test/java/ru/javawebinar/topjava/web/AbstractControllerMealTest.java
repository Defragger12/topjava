package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.MealService;

public abstract class AbstractControllerMealTest extends AbstractControllerTest {

    @Autowired
    protected MealService mealService;

}
