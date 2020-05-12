<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>
<br>
<div class="container">
    <h2>Ranking</h2>
    <p>wegług ilości poprawnych odpowiedzi.</p>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Login</th>
            <th>Nazwa</th>
            <th>Suma</th>
        </tr>
        </thead>
        <tbody>


        <c:forEach items="${users}" var="user">
<%--            <tr>--%>
<%--                <th>${car.id}</th>--%>
<%--                <td>${car.name}</td>--%>

            <tr>
                <td>${user.login}</td>
                <td>${user.name}</td>
                <td>${user.sumOfCorrectAnswers}</td>
            </tr>



<%--            </tr>--%>
        </c:forEach>
<%--        <tr>--%>
<%--            <td>John</td>--%>
<%--            <td>Doe</td>--%>
<%--            <td>john@example.com</td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>Mary</td>--%>
<%--            <td>Moe</td>--%>
<%--            <td>mary@example.com</td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>July</td>--%>
<%--            <td>Dooley</td>--%>
<%--            <td>july@example.com</td>--%>
<%--        </tr>--%>
<%--        --%>


        </tbody>
    </table>
</div>
</body>
</html>