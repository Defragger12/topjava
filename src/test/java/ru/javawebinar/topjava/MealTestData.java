package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.util.DateTimeUtil.*;


public class MealTestData {

    public static final List<Meal> meals = Arrays.asList(
            new Meal(100002, parseLocalDateWithSS("2015-05-30 10:00:00"), "завтрак", 500),
            new Meal(100003, parseLocalDateWithSS("2015-05-30 13:00:00"), "Обед", 1000),
            new Meal(100004, parseLocalDateWithSS("2015-05-30 20:00:00"), "Ужин", 500),
            new Meal(100005, parseLocalDateWithSS("2015-05-31 10:00:00"), "Завтрак", 1000),
            new Meal(100006, parseLocalDateWithSS("2015-05-31 13:00:00"), "Обед", 500),
            new Meal(100007, parseLocalDateWithSS("2015-05-31 20:00:00"), "Ужин", 510),
            new Meal(100008, parseLocalDateWithSS("2015-06-01 14:00:00"), "Админ ланч", 510),
            new Meal(100009, parseLocalDateWithSS("2015-06-01 21:00:00"), "Админ ужин", 1500));

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user_id");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user_id").isEqualTo(expected);
    }


}
