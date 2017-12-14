package com.bots.crew.pp.webhook.services;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilsService {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault());
    private final static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    public static LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    public static Date convertToDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertToDate(LocalTime time, LocalDate date) {
        return Date.from(time.atDate(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth())).
                atZone(ZoneId.systemDefault()).toInstant());
    }




    public static LocalTime convertToLocalTime(Date time) {
        Instant instant = Instant.ofEpochMilli(time.getTime());
        return  LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
    }

    public static String convertToString(Date date) {
        return formatter.format(date);
    }

    public static Date addDays(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }


}
