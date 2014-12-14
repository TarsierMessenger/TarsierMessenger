package ch.tarsier.tarsier.test.util;

import android.test.AndroidTestCase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ch.tarsier.tarsier.util.DateUtil;

/**
 * DateUtilTest tests the DateUtil class.
 *
 * @see ch.tarsier.tarsier.util.DateUtil
 * @author marinnicolini on 16.11.2014.
 */
public class DateUtilTest extends AndroidTestCase {

    private static final int MORE_THAN_ONE_WEEK = 9;
    private static final int THREE = 3;
    private static final int FIFTEEN = 15;
    private static final int FIFTY_FIVE = 55;

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
        calendar.add(Calendar.DAY_OF_MONTH, -MORE_THAN_ONE_WEEK);
        dateSeparator = DateUtil.computeDateSeparator(calendar.getTimeInMillis());
        format = new SimpleDateFormat("dd.MM.yyyy");
        assertEquals(format.format(calendar.getTimeInMillis()), dateSeparator);
    }

    public void testComputeHour() {
        // Set the time to yesterday
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, THREE);
        calendar.set(Calendar.MINUTE, FIFTY_FIVE);
        String sentHour = DateUtil.computeHour(calendar.getTimeInMillis());
        Format format = new SimpleDateFormat("HH:mm");
        assertEquals("03:55" , sentHour);

        calendar.set(Calendar.HOUR_OF_DAY, FIFTEEN);
        calendar.set(Calendar.MINUTE, THREE);
        sentHour=DateUtil.computeHour(calendar.getTimeInMillis());
        assertEquals("15:03", sentHour);
    }
}