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
            <tr>
                <td>${user.login}</td>
                <td>${user.name}</td>
                <td>${user.sumOfCorrectAnswers}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>