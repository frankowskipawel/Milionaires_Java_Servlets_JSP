package servlet;

import dao.QuestionDao;
import entity.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddQuestionController", value = "/addquestion")
public class AddQuestionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
       String login = httpServletRequest.getSession().getAttribute("login").toString();
        if (login.isEmpty() || !login.equals("admin")){
           httpServletRequest.getRequestDispatcher("addquestion.jsp").forward(httpServletRequest, httpServletResponse);
       }
        httpServletRequest.getRequestDispatcher("addquestion.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String text = httpServletRequest.getParameter("text");
        String answerA = httpServletRequest.getParameter("answerA");
        String answerB = httpServletRequest.getParameter("answerB");
        String answerC = httpServletRequest.getParameter("answerC");
        String answerD = httpServletRequest.getParameter("answerD");
        String correctAnswer = httpServletRequest.getParameter("correctAnswer");

        if (text.isEmpty() || answerA.isEmpty() || answerB.isEmpty() || answerC.isEmpty() || answerD.isEmpty() || correctAnswer.isEmpty()){
            httpServletRequest.setAttribute("message", "Uzupełnij wszystkie pola");
            httpServletRequest.getRequestDispatcher("addquestion.jsp").forward(httpServletRequest, httpServletResponse);
        }

        Question question = Question.builder()
                .text(text)
                .answerA(answerA)
                .answerB(answerB)
                .answerC(answerC)
                .answerD(answerD)
                .correctAnswer(correctAnswer).build();
        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(question);
        httpServletRequest.setAttribute("message", "Dodano pytanie.");
        httpServletRequest.getRequestDispatcher("addquestion.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
