package servlet;

import dao.QuestionDao;
import dao.UserDao;
import entity.Question;
import entity.AmountStep;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@WebServlet(name = "GameController", value = "/game")
public class GameController extends HttpServlet {

    private int NUMBER_OF_GAME_QUESTIONS = 12;

    private List<Question> questionList;
    private int currentNumber = 0;
    private Question currentQuestion;
    private HttpSession session;
    private boolean isAvailablePhoneAFriend;
    private boolean isAvailable5050;
    private boolean isAvailableAskTheAudience;
    private AmountStep amountToBeWon;

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String login = httpServletRequest.getSession().getAttribute("login").toString();
        String newGame = httpServletRequest.getParameter("new");
        if (newGame != null) {
            currentNumber = 0;
        }
        if (login == null || login.isEmpty()) {
            httpServletRequest.getRequestDispatcher("login.jsp").forward(httpServletRequest, httpServletResponse);
        }
        if (currentNumber == 0) {
            initializeGame(httpServletRequest);
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        }
        getParametersFromSession();
        String endGame = httpServletRequest.getParameter("endGame");
        if (endGame != null && endGame.equals("true")) {
            int finalWin;
            if (currentNumber == 1) {
                finalWin = 0;
            } else {
                finalWin = AmountStep.valueOf("STEP_" + (currentNumber - 1)).getValue();
            }
            session.setAttribute("amountToBeWon", finalWin);
            httpServletRequest.getRequestDispatcher("defeat.jsp").forward(httpServletRequest, httpServletResponse);
        }

        String lifeline = httpServletRequest.getParameter("lifeline");

        //used 5050
        if (lifeline != null && lifeline.equals("5050")) {
            use5050();
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
        }
        //used phone a friend
        if (lifeline != null && lifeline.equals("phoneAFriend")) {
            usePhoneAFriend(httpServletRequest);
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
        }
        //used ask the audience
        if (lifeline != null && lifeline.equals("askThePeople")) {
            useAskTheAudience(httpServletRequest);
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
        }
        String answer = httpServletRequest.getParameter("answer");
        String resume = httpServletRequest.getParameter("resume");
        if (resume == null) {
            if (answer.equals(currentQuestion.getCorrectAnswer())) {
                currentQuestion = getRandomQuestion();
                currentNumber++;
                session.removeAttribute("answerFromAskTheAudience");
                session.removeAttribute("answerFromPhoneAFriend");
                session.removeAttribute("answerFrom5050");
                UserDao userDao = new UserDao();
                User user = userDao.findById(login);
                user.setSumOfCorrectAnswers(user.getSumOfCorrectAnswers() + 1);
                userDao.update(user);
                setParametersToSession();
                if (currentNumber <= NUMBER_OF_GAME_QUESTIONS) {
                    amountToBeWon = AmountStep.valueOf("STEP_" + currentNumber);
                }
                session.setAttribute("amountToBeWon", amountToBeWon.getValue());

                if (currentNumber > NUMBER_OF_GAME_QUESTIONS) {
                    resetGame();
                    httpServletRequest.getRequestDispatcher("win.jsp").forward(httpServletRequest, httpServletResponse);
                }
                httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
            } else {
                resetGame();
                calculateFinalWin();
                httpServletRequest.getRequestDispatcher("defeat.jsp").forward(httpServletRequest, httpServletResponse);
            }
        } else {
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    private void calculateFinalWin() {
        if (amountToBeWon.getValue() < 20000) {
            session.setAttribute("amountToBeWon", 0);
        }
        if (amountToBeWon.getValue() > 1000 && amountToBeWon.getValue() <= 75000) {
            session.setAttribute("amountToBeWon", 1000);
        }
        if (amountToBeWon.getValue() > 40000 && amountToBeWon.getValue() <= 1000000) {
            session.setAttribute("amountToBeWon", 40000);
        }
    }

    private void useAskTheAudience(HttpServletRequest httpServletRequest) {
        if (isAvailableAskTheAudience == true) {

            Random random = new Random();
            session = httpServletRequest.getSession();
            session.setAttribute("greydOutAskTheAudience", 3);
            int answer = random.nextInt(2);
            int correctAnswerInNumber = correctAnswerInNumber(currentQuestion);

            int incorrectAnswer = random.nextInt(5);
            while (incorrectAnswer == 0 || incorrectAnswer == correctAnswerInNumber) {
                incorrectAnswer = random.nextInt(5);
            }
            if (answer == 0) {
                session.setAttribute("answerFromAskTheAudience", currentQuestion.getCorrectAnswer());
            }
            if (answer == 1) {
                session.setAttribute("answerFromAskTheAudience", getLetterFromNumber(incorrectAnswer));
            }
            isAvailableAskTheAudience = false;
        }
    }

    private void usePhoneAFriend(HttpServletRequest httpServletRequest) {
        if (isAvailablePhoneAFriend == true) {
            Random random = new Random();
            session = httpServletRequest.getSession();
            session.setAttribute("greyedOutAskAFriend", 3);
            int answer = random.nextInt(3);
            int correctAnswerInNumber = correctAnswerInNumber(currentQuestion);

            int incorrectAnswer = random.nextInt(5);
            while (incorrectAnswer == 0 || incorrectAnswer == correctAnswerInNumber) {
                incorrectAnswer = random.nextInt(5);
            }
            if (answer == 0) {
                session.setAttribute("answerFromPhoneAFriend", currentQuestion.getCorrectAnswer());
            }
            if (answer == 1) {
                session.setAttribute("answerFromPhoneAFriend", getLetterFromNumber(incorrectAnswer));
            }
            if (answer == 2) {
                session.setAttribute("answerFromPhoneAFriend", "Niestety nie znam odpowiedzi.");
            }
            isAvailablePhoneAFriend = false;
        }
    }

    private void use5050() {
        if (isAvailable5050 == true) {
            session.setAttribute("greyedOut5050", 3);
            int randomIncorrectAnswers;
            Random random = new Random();
            randomIncorrectAnswers = random.nextInt(5);
            int correctAnswerInNumber = correctAnswerInNumber(currentQuestion);
            while (randomIncorrectAnswers == 0 || randomIncorrectAnswers == correctAnswerInNumber) {
                randomIncorrectAnswers = random.nextInt(5);
            }
            if (randomIncorrectAnswers != 1 && correctAnswerInNumber != 1) {
                currentQuestion.setAnswerA("");
            }
            if (randomIncorrectAnswers != 2 && correctAnswerInNumber != 2) {
                currentQuestion.setAnswerB("");
            }
            if (randomIncorrectAnswers != 3 && correctAnswerInNumber != 3) {
                currentQuestion.setAnswerC("");
            }
            if (randomIncorrectAnswers != 4 && correctAnswerInNumber != 4) {
                currentQuestion.setAnswerD("");
            }
            session.setAttribute("answerFrom5050", "Odrzucono 2 błędne odpowiedzi.");
            isAvailable5050 = false;
        }
    }

    public int correctAnswerInNumber(Question question) {
        if (currentQuestion.getCorrectAnswer().equals("A")) {
            return 1;
        }
        if (currentQuestion.getCorrectAnswer().equals("B")) {
            return 2;
        }
        if (currentQuestion.getCorrectAnswer().equals("C")) {
            return 3;
        }
        if (currentQuestion.getCorrectAnswer().equals("D")) {
            return 4;
        }
        return 0;
    }

    private String getLetterFromNumber(int number) {
        if (number == 1) {
            return "A";
        }
        if (number == 2) {
            return "B";
        }
        if (number == 3) {
            return "C";
        }
        if (number == 4) {
            return "D";
        }
        return null;
    }

    public Question getRandomQuestion() {
        Random generator = new Random();
        Question question = questionList.get(generator.nextInt(questionList.size()));
        Question returnQuestion = new Question();
        returnQuestion.setAnswerA(question.getAnswerA());
        returnQuestion.setAnswerB(question.getAnswerB());
        returnQuestion.setAnswerC(question.getAnswerC());
        returnQuestion.setAnswerD(question.getAnswerD());
        returnQuestion.setText(question.getText());
        returnQuestion.setCorrectAnswer(question.getCorrectAnswer());
        return returnQuestion;
    }

    public void initializeGame(HttpServletRequest httpServletRequest) {
        QuestionDao questionDao = new QuestionDao();
        questionList = questionDao.getAllQuestions();
        currentNumber = 1;
        currentQuestion = getRandomQuestion();
        session = httpServletRequest.getSession();
        session.setAttribute("NUMBER_OF_GAME_QUESTIONS", NUMBER_OF_GAME_QUESTIONS);
        session.setAttribute("currentNumber", currentNumber);
        session.setAttribute("currentQuestion", currentQuestion);
        isAvailablePhoneAFriend = true;
        isAvailable5050 = true;
        isAvailableAskTheAudience = true;
        session.removeAttribute("greydOutAskTheAudience");
        session.removeAttribute("greyedOutAskAFriend");
        session.removeAttribute("greyedOut5050");
        session.removeAttribute("answerFrom5050");
        session.removeAttribute("answerFromAskTheAudience");
        session.removeAttribute("answerFromPhoneAFriend");
        amountToBeWon = AmountStep.valueOf("STEP_" + currentNumber);
        session.setAttribute("amountToBeWon", amountToBeWon.getValue());
        session.setAttribute("yourWin", "0zł");
    }

    public void resetGame() {
        currentNumber = 0;
        currentQuestion = null;
    }

    public void getParametersFromSession() {
        currentNumber = Integer.parseInt(session.getAttribute("currentNumber").toString());
        currentQuestion = (Question) session.getAttribute("currentQuestion");
    }

    public void setParametersToSession() {
        session.setAttribute("currentNumber", currentNumber);
        session.setAttribute("currentQuestion", currentQuestion);
        session.setAttribute("yourWin", AmountStep.valueOf("STEP_" + (currentNumber - 1)).getValue() + "zł");
    }
}
