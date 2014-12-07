package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ch.tarsier.tarsier.util.DateUtil;

/**
 * Created by Marin on 16.11.2014.
 */
public class DateUtilTest extends AndroidTestCase {

    public DateUtilTest() {
        super();
    }

    public void testComputeDateSeparator() {
        String dateSeparator = "";

        // Set the time to yesterday
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        dateSeparator = DateUtil.computeDateSeparator(calendar.getTimeInMillis());
        assertEquals("Yesterday", dateSeparator);

        //Set time to the day before yesterday
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Format format = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        dateSeparator = DateUtil.computeDateSeparator(calendar.getTimeInMillis());
        assertEquals(format.format(calendar.getTimeInMillis()), dateSeparator);

        // Set time to the a day more than one week ago
        calendar.add(Calendar.DAY_OF_MONTH, -9);
        dateSeparator = DateUtil.computeDateSeparator(calendar.getTimeInMillis());
        format = new SimpleDateFormat("dd.MM.yyyy");
        assertEquals(format.format(calendar.getTimeInMillis()), dateSeparator);
    }

    public void testComputeHour() {
        // Set the time to yesterday
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 55);
        String sentHour = DateUtil.computeHour(calendar.getTimeInMillis());
        Format format = new SimpleDateFormat("HH:mm");
        assertEquals("03:55" , sentHour);

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 3);
        sentHour=DateUtil.computeHour(calendar.getTimeInMillis());
        assertEquals("15:03", sentHour);
    }
}