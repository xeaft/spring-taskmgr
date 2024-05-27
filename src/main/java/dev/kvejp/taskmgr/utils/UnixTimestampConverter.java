package dev.kvejp.taskmgr.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UnixTimestampConverter {
    public String convert(int unixTimestamp, String timezone) {
        Instant instant = Instant.ofEpochSecond(unixTimestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yy, HH:mm")
                .withZone(ZoneId.of(timezone.toUpperCase()));
        return formatter.format(instant);
    }
}
