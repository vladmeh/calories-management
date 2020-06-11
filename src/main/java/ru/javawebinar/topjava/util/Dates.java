package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Vladimir Mikhaylov <vladmeh@gmail.com> on 11.06.2020.
 * @link https://github.com/vladmeh/calories-management
 */

public class Dates {
    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
