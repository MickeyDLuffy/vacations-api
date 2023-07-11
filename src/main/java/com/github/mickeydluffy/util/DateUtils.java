package com.github.mickeydluffy.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.LongStream;

public class DateUtils {

    private DateUtils() {}

    public static long countWeekdaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        return LongStream.rangeClosed(0, ChronoUnit.DAYS.between(startDate, endDate))
            .mapToObj(startDate::plusDays)
            .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
            .count();
    }
}
