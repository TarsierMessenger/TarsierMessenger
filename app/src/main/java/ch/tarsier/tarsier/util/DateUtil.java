package ch.tarsier.tarsier.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * DateUtil is the class that provides date/time utilities.
 *
 * @author xawill and marinnicolini
 */
public class DateUtil {
    private static Calendar calendar = Calendar.getInstance();
    private static final int NEGATIVE_FIVE = -5;
    private static final int YEAR_2014 = 2014;

    public static String computeDateSeparator(long timestamp) {
        //Set calendar to Now
        calendar.setTime(new Date());

        //Today at 00:00
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long todayTimestamp = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, -1); //Yesterday at 00:00
        long yesterdayTimestamp = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, NEGATIVE_FIVE); //One week ago at 00:00
        long weekTimestamp = calendar.getTimeInMillis();

        Format format;
        if (timestamp >= todayTimestamp) { // today
            return "Today";
        } else if (timestamp >= yesterdayTimestamp) { // yesterday
            return "Yesterday";
        } else if (timestamp >= weekTimestamp) { // one week ago
            format = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            return format.format(timestamp);
        } else { // further
            format = new SimpleDateFormat("dd.MM.yyyy");
            return format.format(timestamp);
        }
    }

    public static long getNowTimestamp() {
        //Set calendar to Now
        calendar.setTime(new Date());

        return calendar.getTimeInMillis();
    }

    public static long getFirstDecemberTimestamp() {
        //Set calendar to 1st december
        calendar.set(YEAR_2014, Calendar.DECEMBER, 1, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    public static String computeHour(long timestamp) {
        Date sentHour = new Date(timestamp);

        Format format = new SimpleDateFormat("HH:mm");
        return format.format(sentHour);
    }
}
