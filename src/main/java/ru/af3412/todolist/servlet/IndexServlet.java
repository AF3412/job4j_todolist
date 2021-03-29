package ru.af3412.todolist.servlet;

import ru.af3412.todolist.store.HbrnStore;
import ru.af3412.todolist.store.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    private static final Store STORE = HbrnStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", STORE.findAllCategories());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
