package ru.af3412.todolist.servlet;

import com.google.gson.Gson;
import ru.af3412.todolist.model.Task;
import ru.af3412.todolist.store.HbrnStore;
import ru.af3412.todolist.store.Store;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@WebServlet("/tasks")
public class TasksServlet extends HttpServlet {

    private final static Gson GSON = new Gson();
    private static final Store STORE = HbrnStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        sendReply(resp, STORE.findAllTask());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task newTask = getRequestBody(req);
        sendReply(resp, STORE.save(newTask));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task updatedTask = getRequestBody(req);
        sendReply(resp, STORE.update(updatedTask));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task deletedTask = getRequestBody(req);
        boolean stat = STORE.delete(deletedTask);
        sendReply(resp, stat);
    }

    private Task getRequestBody(HttpServletRequest req) throws IOException {
        String reqBody;
        try (InputStream inputStream = req.getInputStream();
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            reqBody = reader.lines().collect(Collectors.joining());
        }
        return GSON.fromJson(reqBody, Task.class);
    }

    private void sendReply(HttpServletResponse resp, Object obj) throws IOException {
        String json = GSON.toJson(obj);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

}
