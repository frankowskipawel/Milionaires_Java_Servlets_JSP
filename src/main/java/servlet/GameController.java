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

        String help = httpServletRequest.getParameter("help");

        //help 5050
        if (help != null && help.equals("5050")) {
            getWheel5050();
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
        }
        //help phone
        if (help != null && help.equals("phone")) {
            getWheelPhone(httpServletRequest);
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
        }
        //help people
        if (help != null && help.equals("people")) {
            getWheelPeople(httpServletRequest);
            httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
        }

        String answer = httpServletRequest.getParameter("answer");
        if (currentNumber >= NUMBER_OF_GAME_QUESTIONS) {
            resetGame();
            httpServletRequest.getRequestDispatcher("win.jsp").forward(httpServletRequest, httpServletResponse);
        }

        String resume = httpServletRequest.getParameter("resume");
        if (resume == null) {
            if (answer.equals(currentQuestion.getCorrectAnswer())) {
                currentQuestion = getRandomQuestion();
                currentNumber++;
                session.removeAttribute("wheelPeople");
                session.removeAttribute("wheelPhone");
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

    private void getWheelPeople(HttpServletRequest httpServletRequest) {
        Random random = new Random();
        HttpSession session = httpServletRequest.getSession();
        int answer = random.nextInt(2);
        int correctAnswerInNumber = correctAnswerInNumber(currentQuestion);

        int incorrectAnswer = random.nextInt(5);
        while (incorrectAnswer == 0 || incorrectAnswer == correctAnswerInNumber) {
            incorrectAnswer = random.nextInt(5);
        }

        if (answer==0){session.setAttribute("wheelPeople", currentQuestion.getCorrectAnswer());}
        if (answer==1){session.setAttribute("wheelPeople", getLetterFromNumber(incorrectAnswer));}

    }

    private void getWheelPhone(HttpServletRequest httpServletRequest) {
        Random random = new Random();
        HttpSession session = httpServletRequest.getSession();
        int answer = random.nextInt(3);
        int correctAnswerInNumber = correctAnswerInNumber(currentQuestion);

        int incorrectAnswer = random.nextInt(5);
        while (incorrectAnswer == 0 || incorrectAnswer == correctAnswerInNumber) {
            incorrectAnswer = random.nextInt(5);
        }

        if (answer==0){session.setAttribute("wheelPhone", currentQuestion.getCorrectAnswer());}
        if (answer==1){session.setAttribute("wheelPhone", getLetterFromNumber(incorrectAnswer));}
        if (answer==2){session.setAttribute("wheelPhone", "Niestety nie znam odpowiedzi.");}

    }

    private void getWheel5050() {
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

    private String getLetterFromNumber(int number){
        if (number==1){return "A";}
        if (number==2){return "B";}
        if (number==3){return "C";}
        if (number==4){return "D";}
        return null;
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
