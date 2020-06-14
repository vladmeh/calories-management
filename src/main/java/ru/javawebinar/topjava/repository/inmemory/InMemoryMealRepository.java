package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(1, meal));

        this.save(2, new Meal(LocalDateTime.of(2020, 6, 13, 8, 0), "Завтрак", 1000));
        this.save(2, new Meal(LocalDateTime.of(2020, 6, 13, 14, 0), "Обед", 700));
        this.save(2, new Meal(LocalDateTime.of(2020, 6, 13, 20, 0), "Ужин", 500));
    }

    @Override
    public Meal save(int userId, Meal meal) {

        Map<Integer, Meal> mealsOfUser = repository.computeIfAbsent(userId, meals -> new ConcurrentHashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsOfUser.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return mealsOfUser.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        return repository.get(userId) != null && repository.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        return repository.get(userId) != null ? repository.get(userId).get(mealId) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> mealsOfUser = repository.get(userId);

        if (mealsOfUser == null) {
            return Collections.emptyList();
        }

        return mealsOfUser.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

