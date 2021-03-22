package ru.af3412.todolist.servlet;


import ru.af3412.todolist.model.User;
import ru.af3412.todolist.store.HbrnStore;
import ru.af3412.todolist.store.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Store STORE = HbrnStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        Optional<User> user = STORE.findUserByName(name);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                HttpSession sc = req.getSession();
                sc.setAttribute("user", user.get());
                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                invalidAuthentication(req, resp);
            }
        } else {
            invalidAuthentication(req, resp);
        }

    }

    private void invalidAuthentication(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("error", "Не верный email или пароль");
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }
}
