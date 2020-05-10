<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<div class="container">
    <c:if test="${not empty errorName}"><h5 style="color:red">${errorName}</h5></c:if>
    <c:if test="${not empty errorLogin}"><h5 style="color:red">${errorLogin}</h5></c:if>
    <c:if test="${not empty errorPassword}"><h5 style="color:red">${errorPassword}</h5></c:if>
    <c:if test="${not empty errorExistLogin}"><h5 style="color:red">${errorExistLogin}</h5></c:if>
    <form action="/register" method="post">
        <div class="form-group">
            <label for="name">Imię:</label>
            <input type="name" name="name" class="form-control" id="name" value="${name_temp}">
        </div>
        <div class="form-group">
            <label for="login">Login</label>
            <input type="login" name="login" class="form-control" id="login" value="${login_temp}">
        </div>
        <div class="form-group">
            <label for="password">Hasło:</label>
            <input type="password" name="password" class="form-control" id="password" value="${password_temp}">
        </div>
        <button type="submit" class="btn btn-default">Utwórz</button>
    </form>
</div>
</body>
</html>