<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>

<form method="post" action='listMeals' name="frmAddMeal">
    <label>Meal ID</label>
    <input type="text" readonly="readonly" name="mealId" value="<c:out value="${meal.id}" />"/> <br/>
    <label>dateTime</label>
    <input type="text" name="dateTime" value="<javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd hh-mm"/>"/> <br/>
    <label>Description</label>
    <input type="text" name="description" value="<c:out value="${meal.description}" />"/> <br/>
    <label>Calories</label>
    <input type="text" name="calories" value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>