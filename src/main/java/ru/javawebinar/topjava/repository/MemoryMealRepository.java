package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 11.06.2020.
 * @link https://github.com/vladmeh/calories-management
 */

public class MemoryMealRepository implements MealRepository{
    private final Map<Long, Meal> repository = new ConcurrentHashMap<>();

    private final AtomicLong counter = new AtomicLong(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal entity) {
        if (entity.getId() == null) {
            entity.setId(counter.incrementAndGet());
        }

        repository.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public Meal find(Long id) {
        return repository.get(id);
    }

    @Override
    public void delete(Long id) {
        repository.remove(id);
    }

    @Override
    public Collection<Meal> all() {
        return repository.values();
    }
}
