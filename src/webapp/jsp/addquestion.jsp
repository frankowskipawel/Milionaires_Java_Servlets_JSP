<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<div class="container">
        <c:if test="${not empty message}"><h5 style="color:red">${message}</h5></c:if>

    <form action="/addquestion" method="post">
        <div class="form-group">
            <label for="text">Pytanie:</label>
            <input type="text" name="text" class="form-control" id="text" value="${text_temp}">
        </div>
        <div class="form-group">
            <label for="answerA">Odpowiedz A</label>
            <input type="answerA" name="answerA" class="form-control" id="answerA" value="${answerA_temp}">
        </div>
        <div class="form-group">
            <label for="answerB">Odpowiedz B</label>
            <input type="answerB" name="answerB" class="form-control" id="answerB" value="${answerB_temp}">
        </div>
        <div class="form-group">
            <label for="answerC">Odpowiedz C</label>
            <input type="answerC" name="answerC" class="form-control" id="answerC" value="${answerC_temp}">
        </div>
        <div class="form-group">
            <label for="answerD">Odpowiedz D</label>
            <input type="answerD" name="answerD" class="form-control" id="answerD" value="${answerD_temp}">
        </div>
        <div class="form-group">
            <label for="correctAnswer">Ustaw poprawną odpowiedź</label>
            <select class="form-control" id="correctAnswer" name="correctAnswer">
                <option>A</option>
                <option>B</option>
                <option>C</option>
                <option>D</option>
            </select>
        </div>
        <button type="submit" class="btn btn-default">Dodaj</button>
    </form>
</div>
</body>
</html>