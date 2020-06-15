package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    private static int getUserId() {
        return SecurityUtil.authUserId();
    }

    public List<MealTo> getAll() {
        log.info("getAll for user {}", getUserId());
        return MealsUtil.getTos(service.getAll(getUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int id) {
        log.info("get {} for user {}", id, getUserId());
        return service.get(getUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create {} for user {}", meal, getUserId());
        checkNew(meal);
        return service.create(getUserId(), meal);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={} for user {}", meal, id, getUserId());
        assureIdConsistent(meal, id);
        service.update(getUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {} for user {}", id, getUserId());
        service.delete(getUserId(), id);
    }

}