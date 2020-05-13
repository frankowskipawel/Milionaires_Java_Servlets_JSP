package servlet;

import dao.QuestionDao;
import dao.UserDao;
import entity.Question;
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

    private int NUMBER_OF_GAME_QUESTIONS = 15;

    private List<Question> questionList;
    private int currentNumber = 0;
    private Question currentQuestion;
    private HttpSession session;


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
        String answer = httpServletRequest.getParameter("answer");
        if (currentNumber >= NUMBER_OF_GAME_QUESTIONS) {
            resetGame();
            httpServletRequest.getRequestDispatcher("win.jsp").forward(httpServletRequest, httpServletResponse);
        }
        String resume = httpServletRequest.getParameter("resume");
        if (resume==null) {
            if (answer.equals(currentQuestion.getCorrectAnswer())) {
                currentQuestion = getRandomQuestion();
                currentNumber++;
                UserDao userDao = new UserDao();
                User user = userDao.findById(login);
                user.setSumOfCorrectAnswers(user.getSumOfCorrectAnswers() + 1);
                userDao.update(user);
                setParametersToSession();
                httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
                return;
            } else {
                resetGame();
                httpServletRequest.getRequestDispatcher("defeat.jsp").forward(httpServletRequest, httpServletResponse);
            }
        } else {
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    public Question getRandomQuestion() {
        Random generator = new Random();
        return questionList.get(generator.nextInt(questionList.size()));
    }

    public void initializeGame(HttpServletRequest httpServletRequest) {
        QuestionDao questionDao = new QuestionDao();
        questionList = questionDao.getAllQuestions();
        currentNumber = 1;
        currentQuestion = getRandomQuestion();
        session = httpServletRequest.getSession();
        session.setAttribute("currentNumber", currentNumber);
        session.setAttribute("currentQuestion", currentQuestion);
    }

    public void resetGame() {
        currentNumber = 0;
        currentQuestion = null;
        session.removeAttribute("currentNumber");
        session.removeAttribute("currentQuestion");
    }

    public void getParametersFromSession() {
        currentNumber = Integer.parseInt(session.getAttribute("currentNumber").toString());
        currentQuestion = (Question) session.getAttribute("currentQuestion");
    }

    public void setParametersToSession() {
        session.setAttribute("currentNumber", currentNumber);
        session.setAttribute("currentQuestion", currentQuestion);
    }
}
