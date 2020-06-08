package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    static Map<LocalDate, Integer> mapDay = new HashMap<>();

    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByStreamInOneReturn(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            caloriesSumPerDate.put(mealDate, caloriesSumPerDate.getOrDefault(mealDate, 0) + meal.getCalories());
        }

        List<UserMealWithExcess> mealExceeded = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
                mealExceeded.add(new UserMealWithExcess(
                        dateTime,
                        meal.getDescription(),
                        meal.getCalories(),
                        meal.getCalories() < caloriesSumPerDate.get(dateTime.toLocalDate())
                ));
            }
        }

        return mealExceeded;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDay = meals.stream().collect(Collectors.groupingBy(
                um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)
        ));

        return meals.stream()
                .filter(um -> TimeUtil.isBetweenHalfOpen(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> new UserMealWithExcess(
                        um.getDateTime(),
                        um.getDescription(),
                        um.getCalories(),
                        um.getCalories() < caloriesSumByDay.get(um.getDateTime().toLocalDate())
                ))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredWithExceeded(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();

        mapDay.merge(meals.get(0).getDateTime().toLocalDate(), meals.get(0).getCalories(), Integer::sum);
        if (meals.size() > 1) {
            list.addAll(filteredWithExceeded(
                    meals.subList(1, meals.size()),
                    startTime,
                    endTime,
                    caloriesPerDay)
            );
        }

        LocalTime time = meals.get(0).getDateTime().toLocalTime();
        if (TimeUtil.isBetweenHalfOpen(time, startTime, endTime)) {
            list.add(new UserMealWithExcess(
                    meals.get(0).getDateTime(),
                    meals.get(0).getDescription(),
                    meals.get(0).getCalories(),
                    mapDay.get(meals.get(0).getDateTime().toLocalDate()) < caloriesPerDay
            ));
        }

        return list;
    }

    public static List<UserMealWithExcess> filteredByStreamInOneReturn(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals
                .stream()
                .collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate()))
                .values()
                .stream()
                .flatMap(dayMeals -> {
                    boolean exceed = dayMeals.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay;
                    return dayMeals.stream().filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> createWithExceed(meal, exceed));
                })
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess createWithExceed(UserMeal meal, boolean exceed) {
        return new UserMealWithExcess(
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories(),
                exceed
        );
    }
}
