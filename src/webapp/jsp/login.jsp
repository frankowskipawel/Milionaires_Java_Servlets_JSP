<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<div class="container">
    <c:if test="${not empty errorLogin}">
        <div class="alert alert-danger">${errorLogin}
        </div>
    </c:if>
    <form action="/login" method="post">
        <div class="form-group">
            <label for="login">Login</label>
            <input type="login" name="login" class="form-control" id="login" value="${login_temp}">
        </div>
        <div class="form-group">
            <label for="password">Hasło:</label>
            <input type="password" name="password" class="form-control" id="password">
        </div>
        <button type="submit" class="btn btn-default">Zaloguj</button>
    </form>
</div>
</body>
</html>