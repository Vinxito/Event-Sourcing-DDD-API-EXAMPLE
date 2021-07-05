package com.dummy.shared.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public final class Utils {

    public static String dateToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static String dateToString(Timestamp timestamp) {
        return dateToString(timestamp.toLocalDateTime());
    }

    public static Timestamp stringToDate(String dateTime) throws ParseException {
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssssss");
        Date parsedDate = formattedDate.parse(dateTime);
        return new Timestamp(parsedDate.getTime());
    }

    public static String jsonEncode(HashMap<String, Serializable> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static HashMap<String, Serializable> jsonDecode(String body) {
        try {
            return new ObjectMapper().readValue(body, HashMap.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static String toSnake(String text) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, text);
    }

    public static String toCamel(String text) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, text);
    }

    public static String toCamelFirstUpper(String text) {
        return StringUtils.capitalize(text);
    }

    public static String toCamelFirstLower(String text) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, text);
    }
}
