package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 11.06.2020.
 * @link https://github.com/vladmeh/calories-management
 */

public class MealServlet extends HttpServlet {
    public static final String FORM = "/mealForm.jsp";
    public static final String LIST = "/meals.jsp";
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new MemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal((id.isEmpty()) ? null : Long.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        log.info((id.isEmpty()) ? "create new meal" : "update: {}", id);
        repository.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        switch (action == null ? "all" : action) {
            case "delete":
                log.info("delete: {}", id);
                repository.delete(Long.valueOf(id));
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                Meal meal = (id == null)
                        ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS), "", 1000)
                        : repository.find(Long.valueOf(id));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(FORM).forward(request, response);
                break;
            case "all":
            default:
                log.info("findAll");
                List<MealTo> meals = MealsUtil.filteredByStreams(repository.all(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
                request.setAttribute("meals", meals);
                request.getRequestDispatcher(LIST).forward(request, response);
                break;
        }
    }
}
