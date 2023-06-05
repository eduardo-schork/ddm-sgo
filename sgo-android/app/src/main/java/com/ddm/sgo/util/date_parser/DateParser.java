package com.ddm.sgo.util.date_parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateParser {
    public static long getCurrentMilliseconds() {
        return System.currentTimeMillis();
    }

    public static String formatMillisecondsToDate(long milliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }
}
