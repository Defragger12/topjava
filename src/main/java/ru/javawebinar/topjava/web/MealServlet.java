package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealDao dao;

    public MealServlet() {
        super();
        dao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.deleteMeal(mealId);
            response.sendRedirect("listMeals?action=listMeals");
        } else if (action.equalsIgnoreCase("edit")) {
            forward = "meal.jsp";
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getMealById(mealId);
            request.setAttribute("meal", meal);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("listMeals")) {
            forward = "listMeals.jsp";
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), LocalTime.of(0, 0), java.time.LocalTime.of(23, 0), 2000);
            request.setAttribute("meals", mealWithExceeds);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else {
            forward = "meal.jsp";
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }

//        RequestDispatcher view = request.getRequestDispatcher(forward);
//        view.forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            dao.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            dao.updateMeal(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher("/listMeals.jsp");
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), LocalTime.of(0, 0), java.time.LocalTime.of(23, 0), 2000);
        request.setAttribute("meals", mealWithExceeds);
        view.forward(request, response);
    }
}
