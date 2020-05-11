package servlet;

import dao.QuestionDao;
import entity.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "GameController", value = "/game")
public class GameController extends HttpServlet {

    private int NUMBER_OF_GAME_QUESTIONS = 15;

    private int currentNumber = 0;
    private Question currentQuestion;
    private HttpSession session;


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

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
        if (answer.equals(currentQuestion.getCorrectAnswer())) {
            currentQuestion = getRandomQuestion();
            currentNumber++;
            setParametersToSession();
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        } else {
            resetGame();
            httpServletRequest.getRequestDispatcher("defeat.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    public Question getRandomQuestion() {
        QuestionDao questionDao = new QuestionDao();
        Random generator = new Random();
        return questionDao.findById(generator.nextInt(3) + 1);
    }

    public void initializeGame(HttpServletRequest httpServletRequest) {
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
