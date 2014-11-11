package ch.tarsier.tarsier;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xawill
 */
public class DateUtil {
    private static Calendar calendar = Calendar.getInstance();

    public static String computeDateSeparator(long timestamp) {
        //Set calendar to Now
        calendar.setTime(new Date());

        //Today at 00:00
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long todayTimestamp = getNowTimestamp();

        calendar.add(Calendar.DAY_OF_MONTH, -1); //Yesterday at 00:00
        long yesterdayTimestamp = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, -5); //One week ago at 00:00
        long weekTimestamp = calendar.getTimeInMillis();

        Format format;
        if (timestamp >= todayTimestamp) { // today
            return "Today";
        } else if (timestamp >= yesterdayTimestamp) { // yesterday
            return "Yesterday";
        } else if (timestamp >= weekTimestamp) { // one week ago
            format = new SimpleDateFormat("E");
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

    public static String computeHour(long timestamp) {
        Date sentHour = new Date(timestamp);

        Format format = new SimpleDateFormat("HH:mm");
        return format.format(sentHour);
    }
}
