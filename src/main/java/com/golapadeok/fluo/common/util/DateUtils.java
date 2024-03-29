package com.golapadeok.fluo.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
    public static String dateFormatter(LocalDateTime date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(date, ZoneId.of("GMT"));

        // 출력 형식 설정
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

        // 포맷 적용 및 출력
        return zonedDateTime.format(outputFormatter);
    }
}
