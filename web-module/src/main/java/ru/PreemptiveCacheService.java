package ru;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class PreemptiveCacheService extends HttpServlet {
    int maxSize = 100;
    PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String jsonValue = mapper.writeValueAsString(cache.getAll());
            resp.getWriter().write(jsonValue);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("application/json");
        String value = req.getParameter("value");
        int key = Integer.parseInt(req.getParameter("key"));

        cache.put(key, value);
        String jsonValue = mapper.writeValueAsString(cache.get(key));
        resp.getWriter().write(jsonValue);
    }
}
