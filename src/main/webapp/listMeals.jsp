<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h2>Meal List</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="meal" items="${requestScope.get('meals')}">
            <tr style="color:<c:out value="${meal.exceed ? 'red' : 'green'}"/>">
                <td><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH-mm"/></td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="listMeals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="listMeals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
        <p><a href="listMeals?action=insert">Add User</a></p>
        </tbody>
    </table>
</div>

</body>
</html>
