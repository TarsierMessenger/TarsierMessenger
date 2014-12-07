package ch.tarsier.tarsier.ui.view;

import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;

/**
 * @author xawill
 */
public interface BubbleListViewItem {
    long getId();
    long getDateTime();
    BubbleAdapter.EndlessListViewType getEndlessListViewType();
}
