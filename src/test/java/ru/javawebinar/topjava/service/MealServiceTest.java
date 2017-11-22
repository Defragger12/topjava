package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    @Qualifier("jdbcMealService")
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(100002, USER_ID);
        assertMatch(meals.get(0), meal);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(100002, USER_ID);
        service.get(100002, USER_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> mealList = service.getBetweenDates(LocalDate.of(2015, 6, 1),
                LocalDate.of(2015, 6, 2), ADMIN_ID);
        List<Meal> expected = Arrays.asList(
                meals.get(6),
                meals.get(7)
        );
        assertMatch(mealList, expected);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> mealList = service.getBetweenDateTimes(LocalDateTime.of(2015, 5, 30, 10, 0)
                , LocalDateTime.of(2015, 5, 31, 20, 0), USER_ID);
        List<Meal> expected = Arrays.asList(
                meals.get(0),
                meals.get(1),
                meals.get(2),
                meals.get(3),
                meals.get(4),
                meals.get(5)
        );
        assertMatch(mealList, expected);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> mealList = service.getAll(ADMIN_ID);
        assertMatch(mealList, meals.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()));
    }

    @Test
    public void update() throws Exception {
        Meal meal = new Meal(100002, LocalDateTime.now(), "kappa123", 10000);
        service.update(meal, ADMIN_ID);
        assertMatch(meal, service.get(100002, ADMIN_ID));
    }

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(LocalDateTime.now(), "kappasdf123", 2020202);
        service.update(meal, ADMIN_ID);
        assertMatch(meal, service.get(100010, ADMIN_ID));
    }

}