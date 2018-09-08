<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Orders</title>
</head>

<body>

<div class="container">

    <h1>All Orders</h1>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Number</th>
            <th>Description</th>
            <th>Amount</th>
            <th>Currency</th>
        </tr>
        </thead>

        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.number}</td>
                <td>${order.description}</td>
                <td>${order.amount}</td>
                <td>${order.currency}</td>
            </tr>
        </c:forEach>
    </table>

</div>

</body>
</html>