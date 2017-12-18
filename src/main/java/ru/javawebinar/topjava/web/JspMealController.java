package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public String getMeals(Model model, @RequestParam(value = "action", required = false) String action, @RequestParam(value = "id", required = false) Integer id) {
        if (action != null) {
            switch (action) {
                case "delete":
                    mealService.delete(Integer.valueOf(id), AuthorizedUser.id());
                    return "redirect:meals";
                case "update":
                    model.addAttribute("meal", mealService.get(Integer.valueOf(id), AuthorizedUser.id()));
                    return "mealForm";
                case "create":
                    model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
                    return "mealForm";
            }
        }
        model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @PostMapping("/meals")
    public String postMeals(Model model, HttpServletRequest request, @RequestParam(value = "action", required = false) String action) {
        if (action != null && action.equals("filter")) {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getBetweenDateTimes(LocalDateTime.of(startDate, startTime),
                    LocalDateTime.of(endDate, endTime), AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
            return "meals";
        } else {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                mealService.create(meal, AuthorizedUser.id());
            } else {
                meal.setId(Integer.valueOf(request.getParameter("id")));
                mealService.update(meal, AuthorizedUser.id());
            }
        }
        return "redirect:meals";
    }
}
