package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MealDao {
    private Connection connection;

    public MealDao() {
        connection = DbUtil.getConnection();
    }

    public void addMeal(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO test.meals(dateTime, description, calories) VALUES (?, ?, ?)");
            preparedStatement.setString(1, meal.getDateTime().toString());
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3, meal.getCalories());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMeal(int mealId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM test.meals WHERE id=?");
            preparedStatement.setInt(1, mealId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMeal(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE test.meals SET dateTime=?, description=?, calories=? " +
                            "WHERE id=?");
            preparedStatement.setString(1, meal.getDateTime().format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH-mm")));
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3, meal.getCalories());
            preparedStatement.setInt(4, meal.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Meal> getAllMeals() {
        List<Meal> meals = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM test.meals");
            while (rs.next()) {
                Meal meal = new Meal();
                meal.setId(rs.getInt("id"));
                meal.setDateTime(LocalDateTime.parse(rs.getString("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")));
                meal.setDescription(rs.getString("description"));
                meal.setCalories(rs.getInt("calories"));
                meals.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meals;
    }

    public Meal getMealById(int mealId) {
        Meal meal = new Meal();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM test.meals WHERE id=?");
            preparedStatement.setInt(1, mealId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                meal.setId(rs.getInt("id"));
                meal.setDateTime(LocalDateTime.parse(rs.getString("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")));
                meal.setDescription(rs.getString("description"));
                meal.setCalories(rs.getInt("calories"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meal;
    }
}
