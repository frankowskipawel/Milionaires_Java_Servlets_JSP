package servlet;

import dao.QuestionDao;
import dao.UserDao;
import entity.Question;
import entity.StepAmount;
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
    private boolean wheelPhone;
    private boolean wheel5050;
    private boolean wheelPeople;
    private StepAmount amount;


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
        if (endGame != null && endGame.equals("true")){
            session.setAttribute("amount", StepAmount.valueOf("STEP_"+(currentNumber-1)).getValue());
            httpServletRequest.getRequestDispatcher("defeat.jsp").forward(httpServletRequest, httpServletResponse);
        }

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


        String resume = httpServletRequest.getParameter("resume");
        if (resume == null) {
            if (answer.equals(currentQuestion.getCorrectAnswer())) {
                currentQuestion = getRandomQuestion();
                currentNumber++;
                session.removeAttribute("wheelPeople");
                session.removeAttribute("wheelPhone");
                session.removeAttribute("wheel5050");
                UserDao userDao = new UserDao();
                User user = userDao.findById(login);
                user.setSumOfCorrectAnswers(user.getSumOfCorrectAnswers() + 1);
                userDao.update(user);
                setParametersToSession();
                if (currentNumber <= NUMBER_OF_GAME_QUESTIONS) {
                    amount = StepAmount.valueOf("STEP_" + currentNumber);
                }
                session.setAttribute("amount", amount.getValue());

                if (currentNumber > NUMBER_OF_GAME_QUESTIONS) {
                    resetGame();
                    httpServletRequest.getRequestDispatcher("win.jsp").forward(httpServletRequest, httpServletResponse);
                }
                httpServletRequest.getRequestDispatcher("game.jsp").forward(httpServletRequest, httpServletResponse);
                return;
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
        if (amount.getValue() < 20000) {
            session.setAttribute("amount", 0);
        }
        if (amount.getValue() > 10000 && amount.getValue() <= 125000) {
            session.setAttribute("amount", 10000);
        }
        if (amount.getValue() > 125000 && amount.getValue() <= 1000000) {
            session.setAttribute("amount", 125000);
        }
    }

    private void getWheelPeople(HttpServletRequest httpServletRequest) {
        if (wheelPeople == true) {

            Random random = new Random();
            session = httpServletRequest.getSession();
            session.setAttribute("opacityPeople", 3);
            int answer = random.nextInt(2);
            int correctAnswerInNumber = correctAnswerInNumber(currentQuestion);

            int incorrectAnswer = random.nextInt(5);
            while (incorrectAnswer == 0 || incorrectAnswer == correctAnswerInNumber) {
                incorrectAnswer = random.nextInt(5);
            }

            if (answer == 0) {
                session.setAttribute("wheelPeople", currentQuestion.getCorrectAnswer());
            }
            if (answer == 1) {
                session.setAttribute("wheelPeople", getLetterFromNumber(incorrectAnswer));
            }

            wheelPeople = false;
        }
    }

    private void getWheelPhone(HttpServletRequest httpServletRequest) {
        if (wheelPhone == true) {
            Random random = new Random();
            session = httpServletRequest.getSession();
            session.setAttribute("opacityPhone", 3);
            int answer = random.nextInt(3);
            int correctAnswerInNumber = correctAnswerInNumber(currentQuestion);

            int incorrectAnswer = random.nextInt(5);
            while (incorrectAnswer == 0 || incorrectAnswer == correctAnswerInNumber) {
                incorrectAnswer = random.nextInt(5);
            }

            if (answer == 0) {
                session.setAttribute("wheelPhone", currentQuestion.getCorrectAnswer());
            }
            if (answer == 1) {
                session.setAttribute("wheelPhone", getLetterFromNumber(incorrectAnswer));
            }
            if (answer == 2) {
                session.setAttribute("wheelPhone", "Niestety nie znam odpowiedzi.");
            }
            wheelPhone = false;
        }
    }

    private void getWheel5050() {
        if (wheel5050 == true) {
            session.setAttribute("opacity5050", 3);
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
            session.setAttribute("wheel5050", "Odrzucono 2 błędne odpowiedzi.");
            wheel5050 = false;
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
        wheelPhone = true;
        wheel5050 = true;
        wheelPeople = true;
        session.removeAttribute("opacityPeople");
        session.removeAttribute("opacityPhone");
        session.removeAttribute("opacity5050");
        session.removeAttribute("wheel5050");
        session.removeAttribute("wheelPeople");
        session.removeAttribute("wheelPhone");

        amount = StepAmount.valueOf("STEP_" + currentNumber);
        session.setAttribute("amount", amount.getValue());
        session.setAttribute("yourWin", "0zł");
    }

    public void resetGame() {
        currentNumber = 0;
        currentQuestion = null;
        session.removeAttribute("currentNumber");
        session.removeAttribute("currentQuestion");
        wheelPhone = false;
        wheel5050 = false;
        wheelPeople = false;
        session.removeAttribute("opacityPeople");
        session.removeAttribute("opacityPhone");
        session.removeAttribute("opacity5050");
    }

    public void getParametersFromSession() {
        currentNumber = Integer.parseInt(session.getAttribute("currentNumber").toString());
        currentQuestion = (Question) session.getAttribute("currentQuestion");
    }

    public void setParametersToSession() {
        session.setAttribute("currentNumber", currentNumber);
        session.setAttribute("currentQuestion", currentQuestion);
        session.setAttribute("yourWin", StepAmount.valueOf("STEP_"+(currentNumber-1)).getValue()+"zł");
    }
}
