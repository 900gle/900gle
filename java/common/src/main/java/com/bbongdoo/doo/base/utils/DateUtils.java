package com.bbongdoo.doo.base.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static String getNowDateTime() {
        return getNowDateTime("yyyy-MM-dd'T'HH:mm:ss");
    }

    public static String getMinusDateTime(int minusMinutes) {
        return getMinusDateTime("yyyy-MM-dd'T'HH:mm:ss", minusMinutes);
    }

    public static String getNowDateTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.now();
        String endDateTime = formatter.format(localDateTime);
        return endDateTime;
    }

    public static String getMinusDateTime(String format, int minusMinutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(minusMinutes);
        String startDateTime = formatter.format(localDateTime);
        return startDateTime;
    }

    public static long getDday(String targetDate) {
        LocalDate today = LocalDate.now();
        LocalDate startDt = LocalDate.parse(targetDate, DateTimeFormatter.ISO_DATE);
        return ChronoUnit.DAYS.between(startDt, today);
    }
}
