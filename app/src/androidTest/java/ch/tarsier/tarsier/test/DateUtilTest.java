package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import ch.tarsier.tarsier.util.DateUtil;

/**
 * Created by Marin on 16.11.2014.
 */
public class DateUtilTest extends AndroidTestCase {
    public DateUtilTest() {
        super();
    }
    public void testGetTimestamp(){
        //Set the time to now
        long timestamp = DateUtil.getNowTimestamp();
        String dateSeparator = DateUtil.computeDateSeparator(timestamp);
        assertEquals("Today", dateSeparator);

        // Set the time to yesterday
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        dateSeparator = DateUtil.computeDateSeparator(calendar.getTimeInMillis());
        assertEquals("Yesterday", dateSeparator);

        //Set time to the day before yesterday
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        SimpleDateFormat format = new SimpleDateFormat("E");
        dateSeparator = DateUtil.computeDateSeparator(calendar.getTimeInMillis());
        assertEquals(format.format(calendar.getTimeInMillis()), dateSeparator);

        // Set time to the a day more than one week ago
        calendar.add(Calendar.DAY_OF_MONTH,-9);
        dateSeparator = DateUtil.computeDateSeparator(calendar.getTimeInMillis());
        format = new SimpleDateFormat("dd.MM.yyyy");
        assertEquals(format.format(calendar.getTimeInMillis()), dateSeparator);
    }
}