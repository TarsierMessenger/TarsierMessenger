package ch.tarsier.tarsier.ui.view;

import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;

/**
 * @author xawill
 */
public interface BubbleListViewItem {
    public long getId();
    public long getDateTime();
    public BubbleAdapter.EndlessListViewType getEndlessListViewType();
}
