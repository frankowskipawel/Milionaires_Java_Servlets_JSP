<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp"/>
<br>

<c:if test="${not empty errorName}"><h1>${errorName}</h1></c:if>
<div class="container" align="right">
    <h4>Twoja wygrana : ${yourWin}</h4>
</div>
<div class="container">

    <div class="container" align="center">
        <a href="/game?lifeline=phoneAFriend">
            <img src="/images/phone.png" width="10%"
                 style="opacity:0.${greyedOutAskAFriend};filter:alpha(opacity=${greyedOutAskAFriend}0);"
                 class="img-rounded"></a>
        <a href="/game?lifeline=5050">
            <img src="/images/5050.png" width="10%"
                 style="opacity:0.${greyedOut5050};filter:alpha(opacity=${greyedOut5050}0);" class="img-rounded"></a>
        <a href="/game?lifeline=askThePeople"><img src="/images/people.png" width="10%"
                                                   style="opacity:0.${greydOutAskTheAudience};filter:alpha(opacity=${greydOutAskTheAudience}0);"
                                                   class="img-rounded"></a>
    </div>

    <h3>Pytanie nr ${currentNumber}/${NUMBER_OF_GAME_QUESTIONS} za ${amountToBeWon}zł</h3>

    <div class="progress">
        <c:if test="${currentNumber<3}">
        <div class="progress-bar progress-bar-success" role="progressbar" style="width:${currentNumber*8.3333}%"></c:if>
            <c:if test="${currentNumber>2}">
            <div class="progress-bar progress-bar-success" role="progressbar" style="width:16.6666%"></c:if>
                I ETAP
            </div>

            <c:if test="${currentNumber<3}">
            <div class="progress-bar progress-bar-warning" role="progressbar" style="width:0%"></c:if>
                <c:if test="${currentNumber>2 && currentNumber<8}">
                <div class="progress-bar progress-bar-warning" role="progressbar"
                     style="width:${(currentNumber*8.3333)-(16.6666)}%"></c:if>
                    <c:if test="${currentNumber>7}">
                    <div class="progress-bar progress-bar-warning" role="progressbar" style="width:41.6666%"></c:if>
                        II ETAP
                    </div>

                    <c:if test="${currentNumber<8}">
                    <div class="progress-bar progress-bar-danger" role="progressbar" style="width:0%"></c:if>
                        <c:if test="${currentNumber>7 && currentNumber<NUMBER_OF_GAME_QUESTIONS+1}">
                        <div class="progress-bar progress-bar-danger" role="progressbar"
                             style="width:${(currentNumber*8.3333)-58.3333}%"></c:if>
                            <c:if test="${currentNumber>NUMBER_OF_GAME_QUESTIONS}">
                            <div class="progress-bar progress-bar-danger" role="progressbar"
                                 style="width:41.6666%"></c:if>
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
                        <a href="/game?endGame=true" class="btn btn-info" role="button">Rezygnuje</a>
                        <br>
                        <c:if test="${not empty answerFromPhoneAFriend}">
                            <h2>Twój przyjaciel wskazuje na następującą odpowiedź:<br>${answerFromPhoneAFriend}</h2>
                        </c:if>
                        <c:if test="${not empty answerFromAskTheAudience}">
                            <h2>Publiczność wskazuje na odpowiedź:<br>${answerFromAskTheAudience}</h2>
                        </c:if>
                        <c:if test="${not empty answerFrom5050}">
                            <h2>${answerFrom5050}</h2>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>