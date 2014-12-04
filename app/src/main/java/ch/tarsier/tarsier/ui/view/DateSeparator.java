package ch.tarsier.tarsier.ui.view;

import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;

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
    public BubbleAdapter.EndlessListViewType getEndlessListViewType() {
        return BubbleAdapter.EndlessListViewType.DATE_SEPARATOR;
    }
}
