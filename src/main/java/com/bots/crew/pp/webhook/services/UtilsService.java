package com.bots.crew.pp.webhook.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public static LocalTime convertToLocalTime(Date date) {
        return LocalTime.parse(date.toString(), timeFormatter);
    }

    public static String convertToString(Date date){
        return formatter.format(date);
    }
}
