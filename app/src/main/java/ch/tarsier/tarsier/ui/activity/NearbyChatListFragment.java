package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.NearbyChatroomSummary;
import ch.tarsier.tarsier.ui.adapter.NearbyChatroomAdapter;

/**
 * Created by benjamin on 25/11/14.
 */
public class NearbyChatListFragment extends Fragment {

    Activity mActivity;
    NearbyChatroomAdapter mNearbyChatroomAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle bundle = getArguments();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        updateChatroomAdapter();
    }

    private void updateChatroomAdapter() {
        mNearbyChatroomAdapter = new NearbyChatroomAdapter(mActivity,R.layout.row_nearby_chat_list,getChatroomNearby());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rowView = inflater.inflate(R.layout.fragment_nearby_chat_list, container, false);
        ListView listView = (ListView) rowView.findViewById(R.id.nearby_chat_list);
        listView.setAdapter(mNearbyChatroomAdapter);
        return rowView;
    }


    public NearbyChatroomSummary[] getChatroomNearby() {
        NearbyChatroomSummary[] chatroomNearby = {
                new NearbyChatroomSummary(7,"SwEng","23:02",55),
                new NearbyChatroomSummary(6,"Sat Rocks","Yesterday",163),
                new NearbyChatroomSummary(5,"CO2","Friday",158),
                new NearbyChatroomSummary(4,"Grillades Préverenges","Wednesday",18),
                new NearbyChatroomSummary(3,"Hong Kong's umbrella movement","Tuesday",88954),
                new NearbyChatroomSummary(2,"M2 EPFL","16.10.14",32),
                new NearbyChatroomSummary(1,"TA meeting 1","14.10.14",8)
        };

        return chatroomNearby;
    }
}
