package com.bots.crew.pp.webhook.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UtilsService {
    public static LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    public static Date convertToDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
