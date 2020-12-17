package com.covid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author mukong
 */
public class DateUtil {

    public static Integer getTodayMinSecond() {

        return (int)LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toEpochSecond(ZoneOffset.of("+8"));
    }

    public static Integer getTodayMaxSecond() {

        return (int)LocalDateTime.of(LocalDate.now(), LocalTime.MAX).toEpochSecond(ZoneOffset.of("+8"));
    }

    public static Integer getDayMinSecond(int daysToSubtract) {

        return (int)LocalDateTime.of(LocalDate.now().minusDays(daysToSubtract), LocalTime.MIN).toEpochSecond(ZoneOffset.of("+8"));
    }

    public static Integer getCurrentTime() {

        return (int)LocalDateTime.of(LocalDate.now(), LocalTime.now()).toEpochSecond(ZoneOffset.of("+8"));
    }

    public static Integer getTimestamp(int daysToAdd) {

        return (int) LocalDateTime.of(LocalDate.now().plusDays(daysToAdd), LocalTime.now())
            .toEpochSecond(ZoneOffset.of("+8"));
    }

    public static int dateSecond(int timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDate date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return (int) LocalDateTime.of(date, LocalTime.MIN).toEpochSecond(ZoneOffset.of("+8"));
    }

    public static String getDateTimeAsStr(int timestamp, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(formatter);
    }

    public static String stringYMD(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(localDate);
    }

    public static String stringMD(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        return formatter.format(localDate);
    }

    public static Integer integerYMD(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.valueOf(formatter.format(localDate));
    }

    public static String strToDateFormat(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        Date newDate= null;
        try {
            newDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(newDate);
    }
}
