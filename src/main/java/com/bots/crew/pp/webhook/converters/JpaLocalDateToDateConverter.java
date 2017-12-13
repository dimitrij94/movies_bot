package com.bots.crew.pp.webhook.converters;

import com.bots.crew.pp.webhook.services.UtilsService;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.util.Date;

@Converter(autoApply = true)
public class JpaLocalDateToDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
        return (locDate == null ? null : UtilsService.convertToDate(locDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return (dbData == null ? null : UtilsService.convertToLocalDate(dbData));
    }

}
