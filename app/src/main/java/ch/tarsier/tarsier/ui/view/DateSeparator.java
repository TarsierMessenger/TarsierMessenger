package ch.tarsier.tarsier.ui.view;

/**
 * @author xawill
 */
public class DateSeparator implements BubbleListViewItem {
    private long mTimeStamp;

    public DateSeparator(long timeStamp) {
        mTimeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    @Override
    public long getId() {
        return getTimeStamp();
    }

    @Override
    public long getDateTime() {
        return getTimeStamp();
    }

    @Override
    public boolean isSeparator() {
        return true;
    }
}
