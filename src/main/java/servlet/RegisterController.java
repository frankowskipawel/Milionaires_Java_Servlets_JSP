package servlet;

import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("register.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String name = httpServletRequest.getParameter("name");
        String login = httpServletRequest.getParameter("login");
        String password = httpServletRequest.getParameter("password");

        UserDao userDao = new UserDao();

        boolean isValidation = true;
        if (name==null || name.isEmpty()){
            isValidation=false;
            httpServletRequest.setAttribute("errorName", "Musisz podać swoje imie");
        }
        if (login==null || login.isEmpty()){
            isValidation=false;
            httpServletRequest.setAttribute("errorLogin", "Musisz podać login.");
        }
        if (password==null || password.isEmpty()){
            isValidation=false;
            httpServletRequest.setAttribute("errorPassword", "Musisz podać hasło.");
        }
        if (userDao.findById(login)!=null){
            isValidation=false;
            httpServletRequest.setAttribute("errorExistLogin", "Login jest już zajęty!");
        }
        if (!isValidation){
            httpServletRequest.setAttribute("name_temp",name);
            httpServletRequest.setAttribute("login_temp",login);
            httpServletRequest.setAttribute("password_temp",password);
            httpServletRequest.getRequestDispatcher("register.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        }
        User user = User.builder().name(name).login(login).password(password).build();
        userDao.insert(user);
        HttpSession session =  httpServletRequest.getSession();
        session.setAttribute("login",login);
        httpServletRequest.getRequestDispatcher("home.jsp").forward(httpServletRequest, httpServletResponse);
    }


}
