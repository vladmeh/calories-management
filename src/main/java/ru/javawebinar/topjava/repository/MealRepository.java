package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 11.06.2020.
 * @link https://github.com/vladmeh/calories-management
 */

public interface MealRepository {
    Meal save(Meal entity);

    Meal find(Long id);

    void delete(Long id);

    Collection<Meal> all();
}
