<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<div class="container">
    <c:if test="${not empty errorName}"> <div class="alert alert-danger">${errorName}</div></c:if>
        <c:if test="${not empty errorLogin}"> <div class="alert alert-danger">${errorLogin}</div></c:if>
        <c:if test="${not empty errorPassword}"> <div class="alert alert-danger">${errorPassword}</div></c:if>
    <c:if test="${not empty errorExistLogin}"> <div class="alert alert-danger">${errorExistLogin}</div></c:if>
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