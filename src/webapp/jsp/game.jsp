<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>
<br>


<c:if test="${not empty errorName}"><h1>${errorName}</h1></c:if>
<div class="container">

    <div class="container" align="center">
        <a href="/game?help=phone"><img src="/images/phone.png" width="10%"
                                        style="opacity:0.${opacityPhone};filter:alpha(opacity=${opacityPhone}0);"
                                        class="img-rounded"></a>
        <a href="/game?help=5050"><img src="/images/5050.png" width="10%"
                                       style="opacity:0.${opacity5050};filter:alpha(opacity=${opacity5050}0);"
                                       class="img-rounded"></a>
        <a href="/game?help=people"><img src="/images/people.png" width="10%"
                                         style="opacity:0.${opacityPeople};filter:alpha(opacity=${opacityPeople}0);"
                                         class="img-rounded"></a>
    </div>


    <h3>Pytanie nr ${currentNumber}/${NUMBER_OF_GAME_QUESTIONS}</h3>
    <div class="progress">
        <c:if test="${currentNumber<5}">
        <div class="progress-bar progress-bar-success" role="progressbar" style="width:${currentNumber*8.3333}%"></c:if>
            <c:if test="${currentNumber>4}">
            <div class="progress-bar progress-bar-success" role="progressbar" style="width:33.3333%"></c:if>
                I ETAP
            </div>
            <c:if test="${currentNumber<5}">
            <div class="progress-bar progress-bar-warning" role="progressbar" style="width:0%"></c:if>
                <c:if test="${currentNumber>4 && currentNumber<9}">
                <div class="progress-bar progress-bar-warning" role="progressbar"
                     style="width:${currentNumber*8.3333-33.3333}%"></c:if>
                    <c:if test="${currentNumber>8}">
                    <div class="progress-bar progress-bar-warning" role="progressbar" style="width:33.3333%"></c:if>
                        II ETAP
                    </div>
                    <c:if test="${currentNumber<9}">
                    <div class="progress-bar progress-bar-danger" role="progressbar" style="width:0%"></c:if>
                        <c:if test="${currentNumber>8 && currentNumber<13}">
                        <div class="progress-bar progress-bar-danger" role="progressbar"
                             style="width:${currentNumber*8.3333-66.6666}%"></c:if>
                            <c:if test="${currentNumber>12}">
                            <div class="progress-bar progress-bar-danger" role="progressbar"
                                 style="width:33.3333%"></c:if>

                                III ETAP
                            </div>
                        </div>
                    </div>
                    <div class="container">
                        <h2>${currentQuestion.text}</h2>
                        <div class="btn-group btn-group-justified">
                            <a href="<c:if test="${currentQuestion.answerA != ''}">/game?answer=A</c:if>"
                               class="btn btn-primary"
                               <c:if test="${currentQuestion.answerA == ''}">disabled</c:if>>A: ${currentQuestion.answerA}</a>
                            <a href="<c:if test="${currentQuestion.answerB != ''}">/game?answer=B</c:if>"
                               class="btn btn-primary"
                               <c:if test="${currentQuestion.answerB == ''}">disabled</c:if>>B: ${currentQuestion.answerB}</a>
                        </div>
                        <div class="btn-group btn-group-justified">
                            <a href="<c:if test="${currentQuestion.answerC != ''}">/game?answer=C</c:if>"
                               class="btn btn-primary"
                               <c:if test="${currentQuestion.answerC == ''}">disabled</c:if>>C: ${currentQuestion.answerC}</a>
                            <a href="<c:if test="${currentQuestion.answerD != ''}">/game?answer=D</c:if>"
                               class="btn btn-primary"
                               <c:if test="${currentQuestion.answerD == ''}">disabled</c:if>>D: ${currentQuestion.answerD}</a>
                        </div>
                        <br>
                        <br>
                        <c:if test="${not empty wheelPhone}">
                            <h2>Twój przyjaciel wskazuje na następującą odpowiedź:<br>${wheelPhone}</h2>
                        </c:if>
                        <c:if test="${not empty wheelPeople}">
                            <h2>Publiczność wskazuje na odpowiedź:<br>${wheelPeople}</h2>
                        </c:if>
                        <c:if test="${not empty wheel5050}">
                            <h2>${wheel5050}</h2>
                        </c:if>
                    </div>
                    ${answer}
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>