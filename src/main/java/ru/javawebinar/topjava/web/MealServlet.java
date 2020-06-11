package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 11.06.2020.
 * @link https://github.com/vladmeh/calories-management
 */

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
