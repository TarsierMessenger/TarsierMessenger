package ch.tarsier.tarsier.ui.view;

import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;

/**
 * BubbleListViewItem is the interface for the BubbleListView.
 *
 * @author xawill
 */
public interface BubbleListViewItem {
    long getId();
    long getDateTime();
    BubbleAdapter.EndlessListViewType getEndlessListViewType();
}
